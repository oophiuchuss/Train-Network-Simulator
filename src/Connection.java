
import java.util.List;

public class Connection {
    private final Station to;
    private final double weight; /*kilometres to Station "to"*/
    private boolean isAvailable = true;

    public Connection(Station to, double weight) {
        this.to = to;
        this.weight = weight;
    }

    public Station getTo() {
        return to;
    }

    public double getWeight() {
        return weight;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public static boolean isContainsStation(List<Connection> connectionList, Station toFind) {
        for (Connection v : connectionList) {
            if (v.to == toFind) return true;
        }
        return false;
    }

    public static Connection findByStationTo(List<Connection> connections, Station to) {
        for (Connection v : connections) {
            if (v.getTo() == to) return v;
        }
        return null;
    }

    @Override
    public String toString() {
        return "Connection to -> " + to;
    }
}
