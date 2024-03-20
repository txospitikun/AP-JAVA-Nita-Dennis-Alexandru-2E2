import java.time.LocalDate;
import java.util.Map;

public class Concert extends Attraction implements Visitable, Payable
{
    private int ticketPrice;

    public Concert(String value) {
        super(value);
    }

    @Override
    public Map<WeekDay, TimeInterval> getTimetable()
    {
        return timetable;
    }

    public void setTimetable(Map<WeekDay, TimeInterval> timetable)
    {
        this.timetable = timetable;
    }

    @Override
    public int getTicketPrice()
    {
        return ticketPrice;
    }

    @Override
    public void setTicketPrice(int value) {
        this.ticketPrice = value;
    }


}
