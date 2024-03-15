import java.time.LocalDate;
import java.util.ArrayList;
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

    public void addAttraction(Attraction value)
    {
        attractions.add(value);
    }

    public List<Attraction> getAttractions()
    {
        return this.attractions;
    }

}
