package Laboratory2;

import java.time.LocalTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * The class that is used to solve the exercise.
 */
public class Problem
{

    /**
     * The collection of depots.
     */
    private ArrayList<Depot> m_depots = new ArrayList<>();
    /**
     * The collection of clients.
     */
    private ArrayList<Client> m_clients = new ArrayList<>();

    /**
     * @return All existing depots.
     */
    public ArrayList<Depot> getDepots() {
        return m_depots;
    }

    /**
     * Adds a depot to the collection of depots.
     * @param depot The depot.
     */
    public void addDepot(Depot depot) {
            for (var d : m_depots) {
                if (d.equals(depot)) {
                    return;
                }
            }
        m_depots.add(depot);
    }

    public void addClient(Client client)
    {
        for(var c : m_clients)
        {
            if(c.equals(client))
            {
                return;
            }
        }
        m_clients.add(client);
    }

    /**
     * @return All the vehicles from all the depots that exist.
     */
    public ArrayList<Vehicle> getAllVehicles()
    {
        ArrayList<Vehicle> vehicles = new ArrayList<>();
        for(var d : m_depots)
        {
            vehicles.addAll(Arrays.asList(d.getVehicles()));
        }
        return vehicles;
    }

    // Assign clients to vehicles

    public String assign()
    {
        StringBuilder stringResult = new StringBuilder();
        Random random = new Random();

        ArrayList<ArrayList<Client>> compatibleClients;
        for(var depot : m_depots)
        {
            Client closestClient = null;
            float minDistanceToClient = Float.MAX_VALUE;
            for(var client : m_clients)
            {
                float distance = (float)Math.sqrt((depot.positionX - client.positionX) * (depot.positionX - client.positionX) + (depot.positionY - client.positionY) * (depot.positionY - client.positionY));
                if(distance < minDistanceToClient && !client.isSatifised)
                {
                    minDistanceToClient = distance;
                    closestClient = client;
                }
            }
            if(closestClient == null)
            {
                System.out.println("No more clients.");
                return "Null";
            }

            AtomicBoolean startNewTour = new AtomicBoolean(false);
            startNewTour.set(false);
            closestClient.isSatifised = true;
            Client finalClosestClient = closestClient;
            float finalMinDistanceToClient = minDistanceToClient;
            Thread beginTour = new Thread(() -> {
                // depot has cars?
                boolean hasFreeCars = false;
                Vehicle freeVehicle = null;
                for(var v : depot.getVehicles())
                {
                    if(!v.isUsed())
                    {
                        hasFreeCars = true;
                        freeVehicle = v;
                        break;
                    }
                }

                if(!hasFreeCars)
                {
                    System.out.printf("Depot doesn't have free cars!%n");
                    return;
                }

                System.out.printf("Began tour from depot: %s to client: %s with vehicle: %s (distance: %f).%n", depot.getName(), finalClosestClient.getName(), freeVehicle.getName(), finalMinDistanceToClient);

                freeVehicle.setUsed(true);

                LocalTime concretLocalTimeStart = finalClosestClient.getStartVisitingInterval();
                LocalTime concretLocalTimeEnd = finalClosestClient.getEndVisitingInterval();

                //search for closest compatible client
                while(true) {
                    float minDistance =  Float.MAX_VALUE;
                    Client closestContextClient = null;
                    for (var client : m_clients) {

                        //check if time interval is not compatible and client hasn't been visited
                        if(client.isSatifised)
                        {
                            continue;
                        }

                        if((client.getEndVisitingInterval().isBefore(concretLocalTimeStart) || client.getStartVisitingInterval().isAfter(concretLocalTimeEnd)))
                        {
                            //System.out.printf("Depot %s can't reach client: %s.%n", depot.getName(), client.getName());
                            startNewTour.set(true);
                            continue;
                        }

                        var distance = (float) Math.sqrt((finalClosestClient.positionX - client.positionX) * (finalClosestClient.positionX - client.positionX) + (finalClosestClient.positionY - client.positionY) * (finalClosestClient.positionY - client.positionY));
                        if(distance < minDistance)
                        {
                            minDistance = distance;
                            closestContextClient = client;
                        }
                    }

                    if(closestContextClient == null)
                    {
                        System.out.printf("Depot %s goes back to the start. No more clients!%n", depot.getName());
                        return;
                    }

                    freeVehicle = null;
                    for(var v : depot.getVehicles())
                    {
                        if(!v.isUsed())
                        {
                            freeVehicle = v;
                            break;
                        }
                    }

                    if(freeVehicle == null)
                    {
                        System.out.printf("No more free vehicles for depot: %s%n.", depot.getName());
                        return;
                    }
                    closestContextClient.isSatifised = true;
                    System.out.printf("Depot %s goes to client: %s with vehicle: %s. %n", depot.getName(), closestContextClient.getName(), freeVehicle.getName());
                }

            });
            beginTour.start();

            while(!startNewTour.get() && beginTour.isAlive()) {}
        }

        return "Finished all tours.";
    }


    /**
     * The bonus part of the laboratory.
     */
    class Edge
    {
        public Node directNeighbour;
        public int cost;

        public Edge(Node value, int cost)
        {
            this.cost = cost;
            this.directNeighbour = value;
        }
    }
    class Node<T> {
        public int nodeType = -1; // 0-transport node 1-client, 2-depot
        public T storedObject = null;
        public int posX, posY;
        public Set<Edge> neighbours = new HashSet<>();
        public int distance = Integer.MAX_VALUE;

        public List<Node> shortestPath = new LinkedList<>();

        public Node(int value) {
            this.nodeType = value;
        }

        public Node(int value, T obj, int x, int y) {
            this.nodeType = value;
            this.storedObject = obj;
            this.posX = x; this.posY = y;
        }

        public Node(int value, int x, int y) {
            this.nodeType = value;
            this.storedObject = null;
            this.posX = x; this.posY = y;
        }


        public boolean addEdge(Edge value) {
            return neighbours.add(value);
        }
    }


    class Graph {
        public Set<Node> nodes;

        public Graph() {
            nodes = new HashSet<>();
        }

        boolean addNode(Node value) {
            return nodes.add(value);
        }
    }

    public void bonus() {
        //random
        Random random = new Random();

        //get the furthest node from top left corner
        int maxPositionX = -1;
        int maxPositionY = -1;

        for (var c : m_clients) {
            if (c.positionX > maxPositionX) {
                maxPositionX = (int) c.positionX;
            }

            if (c.positionY > maxPositionY) {
                maxPositionY = (int) c.positionY;
            }
        }

        for (var d : m_depots) {
            if (d.positionX > maxPositionX) {
                maxPositionX = (int) d.positionX;
            }

            if (d.positionY > maxPositionY) {
                maxPositionY = (int) d.positionY;
            }
        }

        System.out.printf("X: %d, Y: %d%n", maxPositionX, maxPositionY);
        Graph lineGraph = new Graph();
        Node[][] constructedNodes = new Node[maxPositionY + 1][maxPositionX + 1];


        //constructing the line graph
        for (var c : m_clients) {
            constructedNodes[(int) c.positionY][(int) c.positionX] = new Node<Client>(1, c, (int)c.positionX, (int)c.positionY);
        }

        for (var d : m_depots) {
            constructedNodes[(int) d.positionY][(int) d.positionX] = new Node<Depot>(2, d, (int)d.positionX, (int)d.positionY);
        }

        for (int i = 0; i < maxPositionY; i++) {
            for (int j = 0; j < maxPositionX; j++) {
                if (constructedNodes[i][j] == null) {
                    constructedNodes[i][j] = new Node<String>(0, i, j);
                }
            }
        }

        //costructing the weighted edges
        int randomCost = -1;
        for (int i = 0; i < maxPositionY; i++) {
            for (int j = 0; j < maxPositionX; j++) {
                // center of the linegraph

                if (i > 0 && i < maxPositionY - 1 && j > 0 && j < maxPositionX - 1) {
                    randomCost = random.nextInt(100);
                    constructedNodes[i][j].addEdge(new Edge(constructedNodes[i - 1][j], randomCost));
                    constructedNodes[i - 1][j].addEdge(new Edge(constructedNodes[i][j], randomCost));

                    randomCost = random.nextInt(100);
                    constructedNodes[i][j].addEdge(new Edge(constructedNodes[i + 1][j], randomCost));
                    constructedNodes[i + 1][j].addEdge(new Edge(constructedNodes[i][j], randomCost));

                    randomCost = random.nextInt(100);
                    constructedNodes[i][j - 1].addEdge(new Edge(constructedNodes[i][j], randomCost));
                    constructedNodes[i][j].addEdge(new Edge(constructedNodes[i][j - 1], randomCost));

                    randomCost = random.nextInt(100);
                    constructedNodes[i][j + 1].addEdge(new Edge(constructedNodes[i][j], randomCost));
                    constructedNodes[i][j].addEdge(new Edge(constructedNodes[i][j + 1], randomCost));
                }
            }
        }
        for (int i = 1; i < maxPositionY - 1; i++) {
            randomCost = random.nextInt(100);
            constructedNodes[i][0].addEdge(new Edge(constructedNodes[i - 1][1], randomCost));
            constructedNodes[i - 1][0].addEdge(new Edge(constructedNodes[i][0], randomCost));

            randomCost = random.nextInt(100);
            constructedNodes[i][0].addEdge(new Edge(constructedNodes[i + 1][0], randomCost));
            constructedNodes[i + 1][0].addEdge(new Edge(constructedNodes[i][0], randomCost));

            randomCost = random.nextInt(100);
            constructedNodes[i][0].addEdge(new Edge(constructedNodes[i][1], randomCost));
            constructedNodes[i][1].addEdge(new Edge(constructedNodes[i][0], randomCost));
        }

        for (int i = 1; i < maxPositionY - 1; i++) {
            randomCost = random.nextInt(100);
            constructedNodes[i][maxPositionX - 1].addEdge(new Edge(constructedNodes[i - 1][maxPositionX - 1], randomCost));
            constructedNodes[i - 1][maxPositionX - 1].addEdge(new Edge(constructedNodes[i][maxPositionX - 1], randomCost));

            randomCost = random.nextInt(100);
            constructedNodes[i][maxPositionX - 1].addEdge(new Edge(constructedNodes[i + 1][maxPositionX - 1], randomCost));
            constructedNodes[i + 1][maxPositionX - 1].addEdge(new Edge(constructedNodes[i][maxPositionX - 1], randomCost));

            randomCost = random.nextInt(100);
            constructedNodes[i][0].addEdge(new Edge(constructedNodes[i][maxPositionX - 2], randomCost));
            constructedNodes[i][maxPositionX - 2].addEdge(new Edge(constructedNodes[i][0], randomCost));
        }

        for (int i = 1; i < maxPositionX - 1; i++) {
            randomCost = random.nextInt(100);
            constructedNodes[0][i].addEdge(new Edge(constructedNodes[0][i - 1], randomCost));
            constructedNodes[0][i - 1].addEdge(new Edge(constructedNodes[0][i], randomCost));

            randomCost = random.nextInt(100);
            constructedNodes[0][i].addEdge(new Edge(constructedNodes[0][i + 1], randomCost));
            constructedNodes[0][i + 1].addEdge(new Edge(constructedNodes[0][i], randomCost));

            randomCost = random.nextInt(100);
            constructedNodes[0][i].addEdge(new Edge(constructedNodes[1][i], randomCost));
            constructedNodes[1][i].addEdge(new Edge(constructedNodes[0][i], randomCost));
        }

        for (int i = 1; i < maxPositionX - 1; i++) {
            randomCost = random.nextInt(100);
            constructedNodes[maxPositionY - 1][i].addEdge(new Edge(constructedNodes[maxPositionY - 1][i - 1], randomCost));
            constructedNodes[maxPositionY - 1][i - 1].addEdge(new Edge(constructedNodes[maxPositionY - 1][i], randomCost));

            randomCost = random.nextInt(100);
            constructedNodes[maxPositionY - 1][i].addEdge(new Edge(constructedNodes[maxPositionY - 1][i + 1], randomCost));
            constructedNodes[maxPositionY - 1][i + 1].addEdge(new Edge(constructedNodes[maxPositionY - 1][i], randomCost));

            randomCost = random.nextInt(100);
            constructedNodes[0][i].addEdge(new Edge(constructedNodes[maxPositionY - 2][i], randomCost));
            constructedNodes[maxPositionY - 2][i].addEdge(new Edge(constructedNodes[0][i], randomCost));
        }

        // corners of line graph
        // left top
        randomCost = random.nextInt(100);
        constructedNodes[0][0].addEdge(new Edge(constructedNodes[0][1], randomCost));
        constructedNodes[0][1].addEdge(new Edge(constructedNodes[0][0], randomCost));

        randomCost = random.nextInt(100);
        constructedNodes[0][0].addEdge(new Edge(constructedNodes[1][0], randomCost));
        constructedNodes[1][0].addEdge(new Edge(constructedNodes[0][0], randomCost));

        // right top
        randomCost = random.nextInt(100);
        constructedNodes[0][maxPositionX - 1].addEdge(new Edge(constructedNodes[0][maxPositionX - 2], randomCost));
        constructedNodes[0][maxPositionX - 2].addEdge(new Edge(constructedNodes[0][maxPositionX - 1], randomCost));

        randomCost = random.nextInt(100);
        constructedNodes[0][maxPositionX - 1].addEdge(new Edge(constructedNodes[1][maxPositionX - 1], randomCost));
        constructedNodes[1][maxPositionX - 1].addEdge(new Edge(constructedNodes[0][maxPositionX - 1], randomCost));

        // left bottom
        randomCost = random.nextInt(100);
        constructedNodes[maxPositionY - 1][0].addEdge(new Edge(constructedNodes[maxPositionY - 2][0], randomCost));
        constructedNodes[maxPositionY - 2][0].addEdge(new Edge(constructedNodes[maxPositionY - 1][0], randomCost));

        randomCost = random.nextInt(100);
        constructedNodes[maxPositionY - 1][0].addEdge(new Edge(constructedNodes[maxPositionY - 1][1], randomCost));
        constructedNodes[maxPositionY - 1][1].addEdge(new Edge(constructedNodes[maxPositionY - 1][0], randomCost));

        //right bottom
        randomCost = random.nextInt(100);
        constructedNodes[maxPositionY - 1][maxPositionX - 1].addEdge(new Edge(constructedNodes[maxPositionY - 2][maxPositionX - 1], randomCost));
        constructedNodes[maxPositionY - 2][maxPositionX - 1].addEdge(new Edge(constructedNodes[maxPositionY - 1][maxPositionX - 1], randomCost));

        randomCost = random.nextInt(100);
        constructedNodes[maxPositionY - 1][maxPositionX - 1].addEdge(new Edge(constructedNodes[maxPositionY - 1][maxPositionX - 2], randomCost));
        constructedNodes[maxPositionY - 1][maxPositionX - 2].addEdge(new Edge(constructedNodes[maxPositionY - 1][maxPositionX - 1], randomCost));

        //setting types for nodes and identifiers
        //constructing paths from each depot
        //find depots
        for (int i = 0; i < maxPositionY; i++) {
            for (int j = 0; j < maxPositionX; j++) {
                if (constructedNodes[i][j].nodeType == 2) // i found a depot
                {
                    constructedNodes[i][j].distance = 0;

                    Set<Node> visited = new HashSet<>();
                    Set<Node> unvisited = new HashSet<>();

                    unvisited.add(constructedNodes[i][j]);

                    while (unvisited.size() != 0) {
                        Node closestNode = null;
                        int closestDistance = Integer.MAX_VALUE;
                        for (var node : unvisited) {
                            int distance = node.distance;
                            if (distance < closestDistance) {
                                closestDistance = distance;
                                closestNode = node;
                            }
                        }
                        unvisited.remove(closestNode);
                        assert closestNode != null;
                        for (Object nodeEdge : closestNode.neighbours) {
                            Edge x = (Edge) (nodeEdge);
                            Node adiacentNode = x.directNeighbour;
                            int price = x.cost;
                            if (!visited.contains(adiacentNode)) {
                                int sourceDistance = closestNode.distance;
                                if (sourceDistance + price < adiacentNode.distance) {
                                    adiacentNode.distance = sourceDistance + price;
                                    LinkedList<Node> shortestPath = new LinkedList<>(closestNode.shortestPath);
                                    shortestPath.add(closestNode);
                                    adiacentNode.shortestPath = shortestPath;
                                }
                            }
                        }
                        visited.add(closestNode);
                    }
                }
            }
        }

        for(int i = 0; i < maxPositionY; i++)
        {
            boolean foundClient = false;
            for(int j = 0; j < maxPositionX; j++)
            {
                if(constructedNodes[i][j].nodeType != 1)
                {
                    continue;
                }
                foundClient = true;
                var aux = constructedNodes[i][j].shortestPath;
                System.out.printf("Path from Depot to Client: %s at X:%d, Y:%d%n ", ((Client)(constructedNodes[i][j].storedObject)).getName(), i, j);
                for(Object node : aux)
                {
                    var p = (Node)(node);
                    if(p.nodeType == 0)
                    {
                        System.out.printf(" -> (tran) X:%d Y:%d ", p.posX, p.posY);
                    }
                    if(p.nodeType > 0)
                    System.out.printf(" -> (client: %s) %d %d ", ((Client)p.storedObject).getName(), p.posX, p.posY);
                }
            }
            if(foundClient)
                System.out.println();
        }
    }
}
