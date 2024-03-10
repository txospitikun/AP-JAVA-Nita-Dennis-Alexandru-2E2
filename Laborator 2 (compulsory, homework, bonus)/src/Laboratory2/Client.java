package Laboratory2;

import java.time.LocalTime;
import java.util.Random;

/**
 * Represents a client, being a real person which owns cars.
 */
public class Client
{
    /**
     * For testing purposes.
     */
    public float positionX = 0f;
    public float positionY = 0f;

    public boolean isSatifised = false;


    /**
     * The client type.
     */
    private ClientType m_clientType;
    /**
     * The client's name.
     */
    private String m_name;
    /**
     * LocalTime which represents the start of the visiting interval.
     */
    private LocalTime m_visitingInterval_start;
    /**
     * LocalTime which represents the end of the visiting interval.
     */
    private LocalTime m_visitingInterval_end;

    /**
     * Default constructor.
     */
    public Client() {}

    /**
     * Constructor that initialises an instance of the class with a specified name and client type.
     * @param m_name The name of the client.
     * @param m_clientType The client type.
     */
    public Client(String m_name, ClientType m_clientType)
    {
        this.m_name = m_name;
        this.m_clientType = m_clientType;

        generateRandomPosition();
    }

    /**
     * Constructor that initialises an instance of the class with a specified name, client type and visiting interval.
     * @param m_name The name of the client.
     * @param m_visitingInterval_start LocalTime which represents the start of the visiting interval.
     * @param m_visitingInterval_end LocalTime which represents the start of the visiting interval.
     * @param m_clientType The client type.
     */
    public Client(String m_name, ClientType m_clientType, LocalTime m_visitingInterval_start, LocalTime m_visitingInterval_end)
    {
        this.m_name = m_name;
        this.m_visitingInterval_start = m_visitingInterval_start;
        this.m_visitingInterval_end = m_visitingInterval_end;
        this.m_clientType = m_clientType;
    }

    /**
     * @return The client type.
     */
    public ClientType getClientType() {
        return m_clientType;
    }

    /**
     * Sets the client type.
     * @param m_clientType The provided client type.
     */
    public void setClientType(ClientType m_clientType) {
        this.m_clientType = m_clientType;
    }

    /**
     * @return The client's name.
     */
    public String getName() {
        return m_name;
    }

    /**
     * Sets the client's name.
     * @param m_name The client name.
     */
    public void setName(String m_name) {
        this.m_name = m_name;
    }



    /**
     * @return The client's start visiting interval.
     */
    public LocalTime getStartVisitingInterval() {
        return m_visitingInterval_start;
    }

    /**
     * Sets the client's start visiting interval.
     * @param value The client start visiting interval.
     */
    public void setStartVisitingInterval(LocalTime value) {
        this.m_visitingInterval_start = value;
    }


    /**
     * @return The client's start visiting interval.
     */
    public LocalTime getEndVisitingInterval() {
        return m_visitingInterval_end;
    }

    /**
     * Sets the client's start visiting interval.
     * @param value The client start visiting interval.
     */
    public void setEndVisitingInterval(LocalTime value) {
        this.m_visitingInterval_end = value;
    }



    /**
     * Generates randoms cartesian positions for the clients on 2D plane.
     */
    public void generateRandomPosition()
    {
        Random rand = new Random();

        this.positionX = rand.nextFloat(30);
        this.positionY = rand.nextFloat(30);
    }

    /**
     * @return A string containing the correct display format for a client.
     */
    @Override
    public String toString() {
        return "Client{" +
                "m_clientType=" + m_clientType +
                ", m_name='" + m_name + '\'' +
                ", m_visitingInterval_start=" + m_visitingInterval_start +
                ", m_visitingInterval_end=" + m_visitingInterval_end +
                '}';
    }
}
