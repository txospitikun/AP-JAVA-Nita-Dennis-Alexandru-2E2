import java.time.LocalDate;
import java.util.Map;

public class Concert extends Attraction implements Visitable, Payable
{
    private Map<LocalDate, TimeInterval> timetable;
    private double ticketPrice;

    @Override
    public Map<LocalDate, TimeInterval> getTimetable()
    {
        return timetable;
    }

    public void setTimetable(Map<LocalDate, TimeInterval> timetable)
    {
        this.timetable = timetable;
    }

    @Override
    public double getTicketPrice()
    {
        return ticketPrice;
    }


}
