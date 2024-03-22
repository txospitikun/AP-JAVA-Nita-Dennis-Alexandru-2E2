import java.awt.*;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;

public class Main
{
    public static void main(String[] args)
    {
        Trip tripPlanner = new Trip("Iasi");
        TravelPlan travelPlan = new TravelPlan();

        Statue statue1 = new Statue("Hydra din Iasi");
        statue1.setTimetable
                (
                    new Pair<WeekDay, TimeInterval>(WeekDay.MONDAY, new TimeInterval(LocalTime.of(8, 0), LocalTime.of(12, 0))),
                    new Pair<WeekDay, TimeInterval>(WeekDay.TUESDAY, new TimeInterval(LocalTime.of(12, 0), LocalTime.of(19, 0))),
                    new Pair<WeekDay, TimeInterval>(WeekDay.WEDNESDAY, new TimeInterval(LocalTime.of(12, 0), LocalTime.of(19, 0)))
                );
        Statue statue2 = new Statue("Bustul lui Caragiale");
        statue2.setTimetable
                (
                        new Pair<WeekDay, TimeInterval>(WeekDay.TUESDAY, new TimeInterval(LocalTime.of(8, 0), LocalTime.of(12, 0))),
                        new Pair<WeekDay, TimeInterval>(WeekDay.SATURDAY, new TimeInterval(LocalTime.of(12, 0), LocalTime.of(19, 0))),
                        new Pair<WeekDay, TimeInterval>(WeekDay.SUNDAY, new TimeInterval(LocalTime.of(12, 0), LocalTime.of(19, 0)))
                );
        Church church1 = new Church("Biserica Domnului");
        church1.setTimetable
                (
                        new Pair<WeekDay, TimeInterval>(WeekDay.WEDNESDAY, new TimeInterval(LocalTime.of(10, 0), LocalTime.of(20, 0)))
                );
        Concert concert1 = new Concert("Beach Please");
        concert1.setTimetable
                (
                        new Pair<WeekDay, TimeInterval>(WeekDay.SUNDAY, new TimeInterval(LocalTime.of(12, 0), LocalTime.of(21, 0)))
                );
        concert1.setTicketPrice(20);

        Concert concert2 = new Concert("Zilele insulei Bacau");
        concert2.setTimetable
                (
                        new Pair<WeekDay, TimeInterval>(WeekDay.MONDAY, new TimeInterval(LocalTime.of(21, 0), LocalTime.of(3, 0))),
                        new Pair<WeekDay, TimeInterval>(WeekDay.TUESDAY, new TimeInterval(LocalTime.of(22, 0), LocalTime.of(4, 0))),
                        new Pair<WeekDay, TimeInterval>(WeekDay.THURSDAY, new TimeInterval(LocalTime.of(22, 0), LocalTime.of(4, 0))),
                        new Pair<WeekDay, TimeInterval>(WeekDay.FRIDAY, new TimeInterval(LocalTime.of(23, 0), LocalTime.of(7, 0)))
                );
        concert2.setTicketPrice(30);

        tripPlanner.addAttraction(concert1);
        tripPlanner.addAttraction(statue1);
        tripPlanner.addAttraction(concert2);
        tripPlanner.addAttraction(church1);
        tripPlanner.addAttraction(statue2);

        travelPlan.addAttraction(concert1, statue1, concert2, church1, statue2);

        tripPlanner.getVisitableNotPayableLocations();

        travelPlan.computeTravelPlan();


        Bonus.Graph bonusGraph = new Bonus.Graph();
        bonusGraph.addNode(concert1, statue1, concert2, church1, statue2);
        bonusGraph.generateEdges();

        Bonus.Coloring coloringGraph = new Bonus.Coloring(bonusGraph);
        coloringGraph.colorHighestNode();

        coloringGraph.colorGreedy();
    }
}