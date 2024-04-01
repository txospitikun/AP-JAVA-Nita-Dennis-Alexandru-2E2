package org.example;

import com.github.javafaker.Faker;
import org.jgrapht.Graph;
import org.jgrapht.alg.flow.EdmondsKarpMFImpl;
import org.jgrapht.alg.matching.DenseEdmondsMaximumCardinalityMatching;
import org.jgrapht.alg.matching.GreedyMaximumCardinalityMatching;
import org.jgrapht.alg.matching.HopcroftKarpMaximumCardinalityBipartiteMatching;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DefaultUndirectedGraph;
import org.jgrapht.alg.*;

import java.util.*;
import java.util.stream.Collectors;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args)
    {
        Faker nameFaker = new Faker();
        Random random = new Random();


        List<Person> persons = new ArrayList<>();
        List<Destination> destinationList = new ArrayList<>();
        Map<Destination, List<Person>> destinationReq = new HashMap<>();


        destinationList.add(new Destination(nameFaker.company().name()));
        destinationList.add(new Destination(nameFaker.company().name()));


        Driver person1 = new Driver(nameFaker.name().lastName(), 50);
        Driver person2 = new Driver(nameFaker.name().lastName(), 41);
        Driver person3 = new Driver(nameFaker.name().lastName(), 41);
        Passenger person4 = new Passenger(nameFaker.name().lastName(), 22);
        Passenger person5 = new Passenger(nameFaker.name().lastName(), 15);
        Passenger person6 = new Passenger(nameFaker.name().lastName(), 17);

        persons.addAll(Arrays.asList(person1, person2, person3, person4, person5));


        //persons who want to go to first destination
        List<Person> destinationOneList = new ArrayList<>(Arrays.asList(person1, person3, person4));
        List<Person> destinationTwoList = new ArrayList<>(Arrays.asList(person2, person5, person6));

        destinationReq.put(destinationList.get(0), destinationOneList);
        destinationReq.put(destinationList.get(1), destinationTwoList);

        for(var destionationPersonsPair : destinationReq.entrySet())
        {
            var destination = destionationPersonsPair.getKey();
            var personList = destionationPersonsPair.getValue();

            //find the drivers
            var drivers = personList.stream().filter(x -> x instanceof Driver).map(x -> (Driver)x).collect(Collectors.toList());
            //find the passengers
            var untakenPassengers = personList.stream().filter(x -> x instanceof Passenger).map(x -> (Passenger)x).collect(Collectors.toList());
            for(var driver : drivers)
            {
                if(!untakenPassengers.isEmpty())
                {
                    System.out.println("Driver " + driver.name + " takes passenger " + untakenPassengers.get(0).name + " and goes to: " + destination.getDestination() + ".");
                    untakenPassengers.remove(0);
                }
                else
                {
                    System.out.println("Driver " + driver.name + " goes alone to: " + destination.getDestination() + ".");
                }
            }

            for(var x : untakenPassengers)
            {
                System.out.println("Passenger " + x.name + " hadn't a driver to go with!");
            }
        }

        Graph<Person, DefaultEdge> undirectedGraph = new DefaultUndirectedGraph<Person, DefaultEdge>(DefaultEdge.class);



        Set<Person> randomDrivers = new HashSet<>() {
        };
        Set<Person> randomPassengers = new HashSet<>();

        for(int i = 0; i < 5000; i++)
        {
            randomPassengers.add(new Passenger(nameFaker.name().lastName(), 0));
        }

        for(int i = 0; i < 5000; i++)
        {
            randomDrivers.add(new Driver(nameFaker.name().lastName(), 0));
        }

        for(var driver : randomDrivers)
        {
            undirectedGraph.addVertex(driver);
            for(var passenger : randomPassengers)
            {
                undirectedGraph.addVertex(passenger);
                if(random.nextInt(10) == 0)
                {
                    undirectedGraph.addEdge(driver, passenger);
                }
            }
        }
        EdmondsKarpMFImpl<Person, DefaultEdge> edmondKarp = new EdmondsKarpMFImpl<>(undirectedGraph);
        System.out.println(edmondKarp.calculateMaximumFlow(randomDrivers.iterator().next(), randomPassengers.iterator().next()));





        /* Compulsory
        for(int i = 1; i <= 10; i++)
        {
            int isDriver = random.nextInt(2);
            if(isDriver == 0)
            {
                random_persons.add(new Driver("Driver" + i, 5*i));
            }
            else
            {
                random_persons.add(new Passenger("Passenger" + (50-i), 6*i));
            }
        }


        List<Driver> drivers = random_persons.stream().filter(x -> x instanceof Driver).map(x -> (Driver)x).collect(Collectors.toList());
        Collections.sort(drivers, ((u, v) -> ((Integer)u.age).compareTo((Integer)v.age)));
        TreeSet<Passenger> passengers = new TreeSet<>((u, v) -> u.name.compareTo(v.name));
        passengers.addAll(random_persons.stream().filter(x -> x instanceof Passenger).map(x -> (Passenger)x).collect(Collectors.toList()));

        for(var driver : drivers)
        {
            System.out.println(driver);
        }

        for(var passenger : passengers)
        {
            System.out.println(passenger);
        }

        */

    }
}