import java.time.LocalDate;
import java.util.Map;

public class Church extends Attraction implements Visitable
{
    public Church(String value) {
        super(value);
    }

    @Override
    public Map<WeekDay, TimeInterval> getTimetable() {
        return this.timetable;
    }

    @Override
    public int getOpeningHour(WeekDay date) {
        return Visitable.super.getOpeningHour(date);
    }
}
