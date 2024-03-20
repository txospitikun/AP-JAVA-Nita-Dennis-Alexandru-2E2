import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public abstract class Attraction implements Comparable<Attraction>
{
    private String name;

    private int ticketPrice = -1;
    protected Map<WeekDay, TimeInterval> timetable;
    public Attraction(String value)
    {
        this.name = value;
        this.timetable = new HashMap<>();
    }
    @Override
    public int compareTo(Attraction other)
    {
        if(other != null)
        {
            return this.name.compareTo(other.name);
        }
        return 0;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setTimetable(Pair<WeekDay, TimeInterval> ... x)
    {
        for(var value : x)
        {
            this.timetable.put(value.first(), value.second());
        }
    }

    public boolean isOpenOnDay(WeekDay day)
    {
        return timetable.get(day) != null;
    }
}
