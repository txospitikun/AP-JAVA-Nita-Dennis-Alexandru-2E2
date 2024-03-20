import java.time.LocalDate;
import java.util.Map;

public class Statue extends Attraction implements Visitable
{
    public Statue(String value) {
        super(value);
    }
    @Override
    public Map<WeekDay, TimeInterval> getTimetable() {
        return timetable;
    }

    @Override
    public int getOpeningHour(WeekDay date) {
        return Visitable.super.getOpeningHour(date);
    }
}
