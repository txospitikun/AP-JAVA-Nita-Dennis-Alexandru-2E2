import Laboratory2.*;

import java.time.LocalTime;

/**
 * The main class of the application.
 * */
public class Main {
    public static void main(String[] args)
    {
        Problem problem = new Problem();
        //Random positions for clients and depots are generated in the constructors
        Client client1 = new Client("Bombica", ClientType.REGULAR, LocalTime.of(5,30), LocalTime.of(6, 0));
        Client client2 = new Client("Cazan", ClientType.PREMIUM, LocalTime.of(4,30), LocalTime.of(7, 0));
        Client client5 = new Client("Tudor", ClientType.PREMIUM, LocalTime.of(23, 30), LocalTime.of(23, 40));
        Client client3 = new Client("Dennis", ClientType.REGULAR, LocalTime.of(16,30), LocalTime.of(21, 0));
        Client client4 = new Client("Marian", ClientType.REGULAR, LocalTime.of(17, 30), LocalTime.of(19, 20));

        Depot depot1 = new Depot("Gara De Nord");
        Depot depot2 = new Depot("Nicolina");
        Depot depot3 = new Depot("Casa Sindicatelor");

        Vehicle vehicle1 = new Truck("Logan");
        Vehicle vehicle2 = new Drone("Suzuki");
        Vehicle vehicle3 = new Truck("Golf");
        Vehicle vehicle4 = new Drone("Porche");
        Vehicle vehicle5 = new Truck("Audi");

        depot1.setVehicles(vehicle1, vehicle2);
        depot2.setVehicles(vehicle3, vehicle4);
        depot3.setVehicles(vehicle5);

        problem.addDepot(depot1);
        problem.addDepot(depot2);
        problem.addDepot(depot3);

        problem.addClient(client1);
        problem.addClient(client2);
        problem.addClient(client3);
        problem.addClient(client4);
        problem.addClient(client5);


        //HOMEWORK
        String result = problem.assign();

        System.out.println(result);

        System.out.println(client1);
        System.out.println(client2);

        System.out.println(vehicle1);

        System.out.println(depot2);
    }
}