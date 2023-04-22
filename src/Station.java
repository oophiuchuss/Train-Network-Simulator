import java.util.*;

public class Station {
    private final String stationName;
    private List<Connection> adjacentStations = new ArrayList<>();
    private final HashMap<Station, Queue<Trainset>> queueOfTrainsets = new HashMap<>();
    public Station(String stationName) {
        this.stationName = stationName;
    }

    public String getStationName() {
        return stationName;
    }

    public HashMap<Station, Queue<Trainset>> getQueueOfTrainsets() {
        return queueOfTrainsets;
    }

    public List<Connection> getAdjacentStations() {
        return adjacentStations;
    }

    public Connection getConnectionTo(Station to){
        for (Connection v: this.adjacentStations
        ) {
            if(v.getTo() == to) return v;
        }
        return null;
    }

    public static Station getStationByName(List<Station> stations, String sourceName) {
        for (Station s: stations
        ) {
            if(s.getStationName().equals(sourceName)) return s;
        }
        return null;
    }

    public void removeAllConnections(){
        this.adjacentStations = new ArrayList<>();
        for (Connection v : getAdjacentStations()
        ) {
            Station removeFrom =  v.getTo();
            Connection toRemove = Connection.findByStationTo(removeFrom.getAdjacentStations(), this);
            removeFrom.getAdjacentStations().remove(toRemove);
        }
    }

    @Override
    public String toString() {
        return stationName;
    }
}
