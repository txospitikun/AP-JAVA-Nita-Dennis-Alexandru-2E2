import java.util.*;

public class Bonus
{
    public static class Graph
    {
        public Map<Attraction, Set<Attraction>> adjList;
        public Graph()
        {
            this.adjList = new HashMap<>();
        }

        public void addNode(Attraction ... value)
        {
            for(var v : value)
            {
                adjList.put(v, new HashSet<>());
            }

        }

        public void addEdge(Attraction value1, Attraction value2)
        {
            adjList.get(value1).add(value2);
            adjList.get(value2).add(value1);
        }

        public void generateEdges()
        {
            List<Attraction> allAttractions = new ArrayList<>(getAttractions());
            for(int i = 0; i < allAttractions.size(); i++)
            {
                for(int j = i + 1; j < allAttractions.size(); j++)
                {
                    boolean hasCompatibleDay = false;
                    for(var day : WeekDay.values())
                    {
                        if (compatibile(allAttractions.get(i), allAttractions.get(j), day))
                        {
                            hasCompatibleDay = true;
                            break;
                        }
                    }

                    if(!hasCompatibleDay)
                    {
                        System.out.println(allAttractions.get(i).getName() + " comp " + allAttractions.get(j).getName());
                        addEdge(allAttractions.get(i), allAttractions.get(j));
                    }
                }
            }
        }

        public boolean compatibile(Attraction value1, Attraction value2, WeekDay day)
        {
            return value1.isOpenOnDay(day) && value2.isOpenOnDay(day) && !value1.timetable.containsKey(value2.timetable.keySet()) && !value2.timetable.containsKey(value1.timetable.keySet());
        }

        public Set<Attraction> getAttractions()
        {
            Set<Attraction> returnSet = new HashSet<>();
            for(var v : adjList.entrySet())
            {
                returnSet.add(v.getKey());
            }
            return returnSet;
        }

    }

    public static class Coloring
    {
        public Graph graph;
        public Map<Attraction, Integer> coloring;

        public Coloring(Graph value)
        {
            this.graph = value;
            this.coloring = new HashMap<>();
        }

        public void colorHighestNode()
        {
            List<Attraction> attractionList = new ArrayList<>(graph.getAttractions());
            attractionList.sort((v1, v2) ->
                    graph.adjList.get(v1).size() - graph.adjList.get(v2).size());

            Set<Integer> usedColors = new HashSet<>();

            for(var attraction : attractionList)
            {
                Set<Integer> adjacentColors = new HashSet<>();
                for(var adj : graph.adjList.get(attraction))
                {
                    if(coloring.containsKey(adj))
                    {
                        adjacentColors.add(coloring.get(adj));
                    }
                }

                int color = 0;
                while(adjacentColors.contains(color))
                {
                    color += 1;
                }

                coloring.put(attraction, color);
                usedColors.add(color);
            }

            System.out.println("Number of colors used: " + usedColors.size());
            System.out.println("Coloring result:");

            List<List<Attraction>> attractionPool = new ArrayList<>();

            for (int i = 0; i < 100; i++) {
                attractionPool.add(new ArrayList<>());
            }

            int counter = -1;
            var nextColor = -1;

            for (Attraction attraction : attractionList) {
                attractionPool.get(coloring.get(attraction)).add(attraction);
            }

            Set<Set<WeekDay>> finalSet = new HashSet<>();
            for(var attr : attractionPool)
            {
                Set<WeekDay> intersectionSet = new HashSet<>();

                intersectionSet.add(WeekDay.MONDAY);
                intersectionSet.add(WeekDay.TUESDAY);
                intersectionSet.add(WeekDay.WEDNESDAY);
                intersectionSet.add(WeekDay.THURSDAY);
                intersectionSet.add(WeekDay.FRIDAY);
                intersectionSet.add(WeekDay.SATURDAY);
                intersectionSet.add(WeekDay.SUNDAY);

                for(var currentAttr : attr)
                {
                    intersectionSet.retainAll(currentAttr.timetable.keySet());
                }
                finalSet.add(intersectionSet);
            }

            List<List<WeekDay>> outputDays = new ArrayList<>();
            for(var set : finalSet)
            {
                List<WeekDay> newList = new ArrayList<>();
                if(!(set.isEmpty() || set.size() == 7))
                {
                    for(var l : set)
                    {
                        newList.add(l);
                    }
                }
                outputDays.add(newList);
            }
            for (Attraction attraction : attractionList) {
                System.out.println(attraction.getName() + ": Day " + outputDays.get(coloring.get(attraction)+1));
            }
        }


        public void colorGreedy()
        {
            List<Attraction> attractionList = new ArrayList<>(graph.getAttractions());

            Set<Integer> usedColors = new HashSet<>();

            for(var attraction: attractionList)
            {
                int saturationGrade = 0;
                Set<Attraction> adjacentAttractions = graph.adjList.get(attraction);
                for (Attraction adj : adjacentAttractions) {
                    if (coloring.containsKey(adj)) {
                        saturationGrade++;
                    }
                }
                attraction.saturationGrade = saturationGrade;
            }

            attractionList.sort(Comparator.comparing(v -> v.saturationGrade));

            for(var attraction : attractionList)
            {
                Set<Integer> adjacentColors = new HashSet<>();
                for(var adj : graph.adjList.get(attraction))
                {
                    if(coloring.containsKey(adj))
                    {
                        adjacentColors.add(coloring.get(adj));
                    }
                }

                int color = 0;
                while(adjacentColors.contains(color))
                {
                    color += 1;
                }

                coloring.put(attraction, color);
                usedColors.add(color);
            }

            System.out.println("Number of colors used: " + usedColors.size());
            System.out.println("Coloring result:");

            List<List<Attraction>> attractionPool = new ArrayList<>();

            for (int i = 0; i < 100; i++) {
                attractionPool.add(new ArrayList<>());
            }

            int counter = -1;
            var nextColor = -1;

            for (Attraction attraction : attractionList) {
                attractionPool.get(coloring.get(attraction)).add(attraction);
            }

            Set<Set<WeekDay>> finalSet = new HashSet<>();
            for(var attr : attractionPool)
            {
                Set<WeekDay> intersectionSet = new HashSet<>();

                intersectionSet.add(WeekDay.MONDAY);
                intersectionSet.add(WeekDay.TUESDAY);
                intersectionSet.add(WeekDay.WEDNESDAY);
                intersectionSet.add(WeekDay.THURSDAY);
                intersectionSet.add(WeekDay.FRIDAY);
                intersectionSet.add(WeekDay.SATURDAY);
                intersectionSet.add(WeekDay.SUNDAY);

                for(var currentAttr : attr)
                {
                    intersectionSet.retainAll(currentAttr.timetable.keySet());
                }
                finalSet.add(intersectionSet);
            }

            List<List<WeekDay>> outputDays = new ArrayList<>();
            for(var set : finalSet)
            {
                List<WeekDay> newList = new ArrayList<>();
                if(!(set.isEmpty() || set.size() == 7))
                {
                    for(var l : set)
                    {
                        newList.add(l);
                    }
                }
                outputDays.add(newList);
            }
            for (Attraction attraction : attractionList) {
                System.out.println(attraction.getName() + ": Day " + outputDays.get(coloring.get(attraction)+1));
            }
        }
    }
}
