import java.time.LocalDate;
import java.util.Map;

public class Church extends Location implements Visitable
{
    @Override
    public Map<LocalDate, TimeInterval> getTimetable() {
        return null;
    }

    @Override
    public int getOpeningHour(LocalDate date) {
        return Visitable.super.getOpeningHour(date);
    }
}
