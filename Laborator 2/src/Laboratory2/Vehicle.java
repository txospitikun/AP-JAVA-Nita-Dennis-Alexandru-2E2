package Laboratory2;

/**
 * Abstract class that represents a single vehicle.
 */
public abstract class Vehicle
{
    /**
     * The name of the vehicle in question.
     * */
    private String m_name;
    /**
     * The depot object that the vehicle is in.
     */
    private Depot m_depot;

    /**
     * Boolean that checks if the vehicle is being used by a client.
     */
    private boolean isUsed = false;

    /**
     * The constructor which initializes an instance of a vehicle with a provided name.
     * @param m_name The name of the vehicle.
     */
    public Vehicle(String m_name)
    {
        this.m_name = m_name;
    }

    /**
     * @return Vehicle's name.
     */
    public String getName() {
        return m_name;
    }

    /**
     * Sets the vehicle's name.
     * @param m_name The vehicle's name.
     */
    public void setName(String m_name) {
        this.m_name = m_name;
    }

    /**
     * @return Returns the depot which the vehicle is in.
     */
    public Depot getDepot() {
        return m_depot;
    }

    /**
     * Sets the vehicle's depot.
     * @param m_depot The depot object.
     */
    public void setDepot(Depot m_depot) {
        this.m_depot = m_depot;
    }

    /**
     * Check if two vehicles are identical based on their name.
     * @param obj The vehicle object which we use in the equality check.
     */
    @Override
    public boolean equals(Object obj)
    {
        if(!(obj instanceof Vehicle other))
        {
            return false;
        }
        return m_name.equals(other.m_name);
    }

    /**
     * Overrides toString method to print the Vehicle's name and it's depot.
     * @return The correct string format to display a vehicle's information.
     */
    @Override
    public String toString() {
        return "Vehicle{" +
                "m_name='" + m_name + '\'' +
                ", m_depot=" + m_depot.getName() +
                '}';
    }

    /**
     * @return if the car is being used or not.
     */
    public boolean isUsed() {
        return isUsed;
    }

    /**
     * Sets the used boolean.
     * @param used The boolean which is being used.
     */
    public void setUsed(boolean used) {
        isUsed = used;
    }
}
