package RailroadCars;

import java.util.HashMap;

public class NotEnoughCapacity extends Exception {
    private static final HashMap<String, Integer> repetitions = new HashMap<>();
    private static int counter = 0;

    public NotEnoughCapacity(String text) {
        super(text);
        counter++;
        addRep(text);
    }

    public static void showRep() {
        for (String text : repetitions.keySet()
        ) {
            System.out.println(text + " -> " + repetitions.get(text));
        }
    }

    public void addRep(String text) {
        int x = repetitions.getOrDefault(text, 0) + 1;
        repetitions.put(text, x);
    }
}
