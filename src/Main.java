import java.util.List;

public class Main {
    public static void main(String[] args) {
        StationGenerator t1 = new StationGenerator(100, 5, 10, 5, 15);
        t1.generate();
        System.out.println("100 stations with connections have been created.");
        TrainsetGenerator t2 = new TrainsetGenerator(25,5,10,t1.stationList);
        t2.generate();
        System.out.println("25 trainsets with connected cars have been created.");
        List<Trainset> a = t2.trainsetList;
        for (Trainset v: a
             ) {
            int index1 = (int)(Math.random()*t1.stationList.size());
            int index2 = (int)(Math.random()*t1.stationList.size());
            while(index1 == index2){
                index1 = (int)(Math.random()*t1.stationList.size());
                index2 = (int)(Math.random()*t1.stationList.size());
            }
            v.setPath(t1.stationList.get(index1), t1.stationList.get(index2));

        }
        for (Trainset v: a
             ) {
            v.start();
        }

        AppState appState = new AppState(a);
        appState.start();

        Menu menu = new Menu(t1.stationList, a, appState);
        menu.start();
    }
}


