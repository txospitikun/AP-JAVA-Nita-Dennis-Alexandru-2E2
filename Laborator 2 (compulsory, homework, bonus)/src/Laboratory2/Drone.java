package Laboratory2;

/**
 * Extended vehicle class that represents trucks.
 */
public class Drone extends Vehicle
{
    private float m_flightDuration;
    public Drone(String m_name) {
        super(m_name);
    }

    /**
     * @return Drone's maximum flight duration.
     */
    public float getFlightDuration() {
        return m_flightDuration;
    }

    /**
     * Sets drone's maximum flight duration.
     */
    public void setFlightDuration(float m_flightDuration) {
        this.m_flightDuration = m_flightDuration;
    }
}
