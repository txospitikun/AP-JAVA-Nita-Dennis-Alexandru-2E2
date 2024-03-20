import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Trip
{
    private String city;
    private LocalDate start, end;
    private List<Attraction> attractions = new ArrayList<>();

    public Trip(String value)
    {
        this.city = value;
    }
    public void setCity(String value)
    {
        this.city = value;
    }

    public String getCity()
    {
        return this.city;
    }

    public void setStartAndEndDate(Pair<LocalDate, LocalDate> value)
    {
        this.start = value.first();
        this.end = value.second();
    }

    public Pair<LocalDate, LocalDate> getStartAndEndDate()
    {
        return new Pair<LocalDate, LocalDate>(this.start, this.end);
    }

    public void getVisitableNotPayableLocations()
    {
        System.out.println("=== Printing locations that are visitable, but not payable ordered by their opening hour. ===");

        Collections.sort(attractions, (a, b) -> a.compareTo(b));
        for(var type : WeekDay.values()) {
            System.out.printf("%s%n", type.toString());
            for (var attraction : attractions) {
                if (attraction instanceof Visitable && !(attraction instanceof Payable)) {
                    var openingHour = ((Visitable) attraction).getOpeningHour(type);
                    if(openingHour != -1)
                    {
                        System.out.printf("%s is visitable and free by hour: %d.%n", attraction.getName(), openingHour);
                    }
                }
            }
            System.out.println();
        }

        System.out.println("=============================================================================================");
    }

    public void addAttraction(Attraction value)
    {
        attractions.add(value);
    }

    public List<Attraction> getAttractions()
    {
        return this.attractions;
    }

}
