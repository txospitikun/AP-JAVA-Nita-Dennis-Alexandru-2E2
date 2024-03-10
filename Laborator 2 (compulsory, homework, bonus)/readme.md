Compulsory (1p)

Create an object-oriented model of the problem. You should have (at least) the following classes: Depot, Vehicle, Client.
I have created each class.
A client may be of two types, regular and premium. Use an enum in order to implement this feature.
I implemented the types PREMIUM and REGULAR.
Each class should have appropriate constructors, getters and setters.
Each class has constructors, getters and setters.
The toString method form the Object class must be properly overridden for all the classes.
Create and print on the screen an object of each class.
With toString overloaded it was easy to print to the screen each object.
Homework (2p)
Create classes that describe the problem and its solution.
Override the equals method form the Object class for the Depot, Vehicle, Client classes. The problem should not allow adding the same depot, vehicle or client twice.
Vehicles may be of different types. Create dedicated classes for trucks and drones. Trucks have a capacity property, while drones have a maximum flight duration (these properties will not be used by the algorithms). The Vehicle class will become abstract.
Implement the method getVehicles in the Problem class, returning an array of all the vehicles, form all depots.
All of above were easy and they've been made.
Assume that the times required to travel between the locations (depot-to-client or client-to-client) are known (You may generate them randomly). Implement a simple greedy algorithm for allocating clients to vehicles.
I made a greedy asynchronous algorithm that (based on clients with compatible time intervals) its starts from depots and goes to each client then goes back the depot.
Write doc comments in your source code and generate the class documentation using javadoc.
I did.
Bonus (2p)
Consider that the locations of depots and clients are nodes of a connected subgraph of a grid graph. The vehicles can only travel along the edges of this graph. The time required to move between adjacent nodes is given by a cost matrix.
I generated random positions for each client and depot, and the weights of the edges too, were randomized.
I have used Dijskra's algorithm to find the shortest path from a depot to compatibile clients (clients with compatibile time intervals).
I have created a simple undirected graph model with classes: (Edge and generic class Node).
Modify the algorithm that allocates clients to vehicles, such that the vehicles will travel on the shortest paths when moving from the depot to the clients, from one client to another or back to the depot.
Can you improve the complexity of the algorithm if all the values in the cost matrix are the same?
Yes, using Warshall's algorithm, the time complexity would be O(nm).
Create a random problem generator and test your algorithm for large instances. (Drawing a textual representation of the map, using Unicode symbols would be nice).
(Warning: No points are awarded unless the implementation can be clearly explained).
