import java.util.*;
public class ShortestPathAlgorithm {
    private final List<Station> stations;
    private Set<Station> visitedStations;
    private PriorityQueue<Station> priorityQueue;
    private Map<Station, Double> distance = new HashMap<>();
    private Map<Station, Station> previousStation;
    private Station sourceStation;

    public ShortestPathAlgorithm(List<Station> stations) {
        this.stations = stations;
        for (Station station : stations) {
            distance.put(station, Double.POSITIVE_INFINITY);
        }
    }

    public void execute(Station sourceStation) {
        this.sourceStation=sourceStation;
        visitedStations = new HashSet<>();
        priorityQueue = new PriorityQueue<>(Comparator.comparingDouble(distance::get));
        distance = new HashMap<>();
        previousStation = new HashMap<>();

        for (Station station : stations) {
            distance.put(station, Double.POSITIVE_INFINITY);
        }

        distance.put(sourceStation, 0.0);
        priorityQueue.offer(sourceStation);

        while (!priorityQueue.isEmpty()) {
            Station currentStation = priorityQueue.poll();
            if (visitedStations.contains(currentStation)) {
                continue;
            }
            visitedStations.add(currentStation);

            for (Connection connection : currentStation.getAdjacentStations()) {
                Station neighborStation = connection.getTo();
                double weight = connection.getWeight();
                double altDistance = distance.get(currentStation) + weight;
                if (altDistance < distance.get(neighborStation)) {
                    distance.put(neighborStation, altDistance);
                    previousStation.put(neighborStation, currentStation);
                    priorityQueue.offer(neighborStation);
                }
            }
        }
    }

    public List<Station> getShortestPathTo(Station targetStation) {
        List<Station> path = new ArrayList<>();
        Station currentStation = targetStation;
        while (previousStation.containsKey(currentStation)) {
            path.add(currentStation);
            currentStation = previousStation.get(currentStation);
        }
        path.add(sourceStation);
        Collections.reverse(path);

        return path;
    }
}
