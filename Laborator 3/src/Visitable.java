import java.time.LocalTime;
import java.time.LocalDate;
import java.util.Map;

public interface Visitable
{
    Map<WeekDay, TimeInterval> getTimetable();
    default int getOpeningHour(WeekDay date)
    {
        var returnedTimetable = getTimetable();
        var returnedTimeInterval = returnedTimetable.get(date);
        if(returnedTimeInterval == null)
        {
            return -1;
        }
        return returnedTimeInterval.first().getHour();
    }

    default int getClosingHour(WeekDay date)
    {
        var returnedTimetable = getTimetable();
        var returnedTimeInterval = returnedTimetable.get(date);
        if(returnedTimeInterval == null)
        {
            return -1;
        }
        return returnedTimeInterval.second().getHour();
    }

}
