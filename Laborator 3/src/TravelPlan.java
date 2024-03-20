import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TravelPlan
{
    private List<Attraction> attractions = new ArrayList<>();
    public void addAttraction(Attraction ... value)
    {
        for(var v : value)
        {
            attractions.add(v);
        }
    }
    public void computeTravelPlan()
    {
        System.out.println("=== Printing locations with their intervals, days and prices (if not free). ===");

        Collections.sort(attractions, (a, b) -> a.compareTo(b));
        for(var type : WeekDay.values()) {
            System.out.printf("%s%n", type.toString());
            for (var attraction : attractions) {
                var openingHour = ((Visitable) attraction).getOpeningHour(type);
                var closingHour = ((Visitable) attraction).getClosingHour(type);
                if(openingHour != -1)
                {
                    if(!(attraction instanceof Payable))
                    {
                        System.out.printf("%s is visitable by interval: %d:00-%d:00 and it's free of charge.%n", attraction.getName(), openingHour, closingHour);
                    }
                    else
                    {
                        System.out.printf("%s is visitable by interval: %d:00-%d:00 and it costs %d RON.%n", attraction.getName(), openingHour, closingHour, ((Payable)attraction).getTicketPrice());
                    }
                }
            }
            System.out.println();
        }

        System.out.println("================================================================================");
    }
}
