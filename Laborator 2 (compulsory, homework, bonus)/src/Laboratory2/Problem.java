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
    public void Bonus()
    {
        class Node
        {
            public int type = 0; //0 - client, 1 - depot
            public int positionX;
            public int positionY;

            public Node(int x, int y, int type)
            {
                this.positionX = x;
                this.positionY = y;
                this.type = type;
            }
        }

        class Edge
        {
            public Node source;
            public Node dest;
            public int weight = 0;
            public Edge(Node s, Node d, int w)
            {
                this.source = s;
                this.dest = d;
                this.weight = w;
            }
        }

        class Graph
        {
            Map<Node, List<Edge>> list = new HashMap<>();

            public void addNode(Node value)
            {
                list.put(value, new ArrayList<>());
            }

            public void addEdge(Node s, Node d, int w)
            {
                Edge newEdge = new Edge(s, d, w);
                list.get(s).add(newEdge);
                list.get(d).add(newEdge);
            }
        }

        Graph lineGraph = new Graph();

        for(int i = 0; i < m_clients.size(); i++)
        {
            Node newNode = new Node((int)m_clients.get(i).positionX, (int)m_clients.get(i).positionY, 0);
            lineGraph.addNode(newNode);
        }

        for(int i = 0; i < m_depots.size(); i++)
        {
            Node newNode = new Node((int)m_depots.get(i).positionX, (int)m_depots.get(i).positionY, 1);
            lineGraph.addNode(newNode);
        }
    }


}
