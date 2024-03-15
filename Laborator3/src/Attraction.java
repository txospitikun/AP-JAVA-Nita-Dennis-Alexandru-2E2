public abstract class Attraction implements Comparable<Attraction>
{
    private String name;
    @Override
    public int compareTo(Attraction other)
    {
        if(other != null)
        {
            return this.name.compareTo(other.name);
        }
        return 0;
    }
}
