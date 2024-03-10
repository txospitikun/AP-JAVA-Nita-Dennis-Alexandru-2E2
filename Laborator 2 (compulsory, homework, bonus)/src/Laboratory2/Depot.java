package Laboratory2;

import java.util.Arrays;
import java.util.Objects;
import java.util.Random;

/**
 * A named depot which has a collection of vehicles.
 */
public class Depot
{
    /**
     * For testing purposes.
     */
    public float positionX = 0;
    public float positionY = 0;

    /**
     * The depot's name.
     */
    private String m_name;
    /**
     * A collection that contains all the vehicles that are in the depot.
     * <font color="red">It is an ArrayList!</font>
     */
    private Vehicle[] m_vehicles;

    /**
     * Basic constructor which initialises an instance of a Depot with a specific name.
     * @param m_name The name.
     */
    public Depot(String m_name)
    {
        this.m_name = m_name;
        generateRandomPosition();
    }

    /**
     * Sets the depot's name.
     * @param m_name The new name.
     */
    public void setName(String m_name)
    {
        this.m_name = m_name;
    }

    /**
     * @return Depot's name.
     */
    public String getName()
    {
        return this.m_name;
    }

    /**
     * Takes a dynamic number of vehicles and sets them as the depot's collection of vehicles.
     * @param vehicles The collection of vehicle we use to assign the existing collection.
     */
    public void setVehicles(Vehicle ... vehicles)
    {
        this.m_vehicles = vehicles;
        for(var v : vehicles)
        {
            v.setDepot(this);
        }
    }

    /**
     * @return The collection of vehicles.
     *
     */
    public Vehicle[] getVehicles()
    {
        return this.m_vehicles;
    }

    /**
     * Generates randoms cartesian positions for the depots on 2D plane.
     */
    public void generateRandomPosition()
    {
        Random rand = new Random();

        this.positionX = rand.nextFloat(1000);
        this.positionY = rand.nextFloat(1000);
    }

    /**
     * Check equality between two depots based on their name and vehicle collection.
     * @param o The depot which we use in the equality check.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Depot depot = (Depot) o;
        return Objects.equals(m_name, depot.m_name) && Arrays.equals(m_vehicles, depot.m_vehicles);
    }

    /**
     * @return Prints the depot name and all the vehicles.
     */
    @Override
    public String toString() {
        StringBuilder resultString = new StringBuilder();
        resultString.append("Depot{m_name='").append(m_name).append("'\nm_vehicles=\n{\n");
        for(var v : m_vehicles)
        {
            resultString.append(v.toString()).append('\n');
        }
        resultString.deleteCharAt(resultString.length()-1);
        resultString.append("\n}");
        return resultString.toString();
    }
}
