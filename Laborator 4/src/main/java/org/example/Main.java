package org.example;

import java.util.*;
import java.util.stream.Collectors;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args)
    {
        List<Person> random_persons = new ArrayList<>();
        Random random = new Random();

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



    }
}