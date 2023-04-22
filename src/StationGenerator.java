import java.util.ArrayList;
import java.util.List;

public class StationGenerator {
    private final int numStations;
    private final int minNumCon;
    private final int maxNumCon;
    private final double minWeight; /*minimal distance between two stations*/
    private final double maxWeight; /*maximum distance between two stations*/
    public final List<Station> stationList = new ArrayList<>();

    public StationGenerator(int numStations, int minNumCon, int maxNumCon, double minWeight, double maxWeight) {
        this.numStations = numStations;
        this.minNumCon = minNumCon;
        this.maxNumCon = maxNumCon;
        this.minWeight = minWeight;
        this.maxWeight = maxWeight;
    }

    public void generate() {
        for (int i = 0; i < numStations; i++) {
            stationList.add(new Station("Station " + (i + 1)));
        }
        //randomly choose station from list for connection
        for (Station v : stationList
        ) {
            generateConnection(v, stationList);
        }
    }

    public void generateConnection(Station v, List<Station> stationList) {
        int numOfCon = minNumCon + (int) (Math.random() * (maxNumCon + 1 - minNumCon));
        for (int i = 0; i < numOfCon; i++) {
            int indexForRand = (int) (Math.random() * (numStations));
            Station toAdd = stationList.get(indexForRand);
            if (toAdd != v && !Connection.isContainsStation(v.getAdjacentStations(), toAdd)) {
                double weight = minWeight + (Math.random() * (maxWeight - minWeight));
                v.getAdjacentStations().add(new Connection(toAdd, weight));
                toAdd.getAdjacentStations().add(new Connection(v, weight));
            }
        }
    }
}
