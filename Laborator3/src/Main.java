public class Main
{
    public static void main(String[] args)
    {
        Trip tripPlanner = new Trip("Iasi");

        Statue eastStatue = new Statue();
        Statue northenStatue = new Statue();
        Church northenChurch = new Church();
        Concert middleConcert = new Concert();
        Concert beachPleaseConcert = new Concert();

        tripPlanner.addAttraction(middleConcert);
        tripPlanner.addAttraction(beachPleaseConcert);

    }
}