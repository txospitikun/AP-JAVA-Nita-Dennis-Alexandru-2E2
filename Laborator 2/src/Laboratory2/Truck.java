package Laboratory2;

/**
 * Extended vehicle class that represents drones.
 */
public class Truck extends Vehicle
{
    private int m_capacity;
    public Truck(String m_name) {
        super(m_name);
    }

    /**
     * @return The truck's capacity.
     */
    public int getCapacity() {
        return m_capacity;
    }

    /**
     * Sets the truck's capacity.
     */
    public void setCapacity(int m_capacity) {
        this.m_capacity = m_capacity;
    }
}
