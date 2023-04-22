import RailroadCars.RailroadCar;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class AppState extends Thread {
    private final List<Trainset> trainsetList;
    private boolean isRunning;
    private static int amountOfRecordings = 0;
    private int currentNumOfRecording;

    public AppState(List<Trainset> trainsetList) {
        this.trainsetList = trainsetList;
    }

    @Override
    public void run() {
        isRunning = true;
        while (isRunning) {
            try {
                Thread.sleep(5000);
                writeInFile();
                currentNumOfRecording = amountOfRecordings +1;
                amountOfRecordings++;
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                isRunning = false;
            }
        }
    }

    public void writeInFile() {
        try {
            File stateFile = new File("AppState.txt");
            stateFile.createNewFile();
            FileWriter myWriter = new FileWriter("AppState.txt");
            List<Trainset> temp = sortTrainsets(trainsetList);
            myWriter.write("-----------------------------------------------------------------\n");
            for (Trainset ts : temp
            ) {
                List<RailroadCar> temp2 = sortCars(ts.getConnectedCars());
                myWriter.write(ts + ": \n ID: " + ts.getUniqueNum()
                        + "\n Home station: " + ts.getHomeStation()
                        + "\n Source Station: " + ts.getSourceStation()
                        + "\n Destination Station: " + ts.getDestStation()
                        + "\n Current Station: " + ts.getCurrentStation()
                        + "\n Target Station: " + ts.getTargetStation()
                        + "\n Length of whole path: " + ts.getWholeWeight() + " km"
                        + "\n Travelled distance: " + ts.getTravelledWeight() + " km"
                        + "\nLocomotive: " + ts.getHead()
                        + "\n ID: " + ts.getHead().getUniqueNum()
                        + "\n Maximum of cars: " + ts.getHead().getMaxCars()
                        + "\n Maximum weight: " + ts.getHead().getMaxWeight() + " kg"
                        + "\n Maximum cars that require electricity: " + ts.getHead().getMaxElectricity()
                        + "\n Velocity: " + ts.getHead().getVelocity() + " km/h"
                        + "\nAttached cars: "
                );
                for (RailroadCar v : temp2
                ) {
                    myWriter.write("\n•");
                    myWriter.write(v.info());
                }
                myWriter.write("\n-----------------------------------------------------------------\n");
            }
            myWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
        }
    }

    public List<Trainset> sortTrainsets(List<Trainset> list){
        List<Trainset> result = new ArrayList<>(list);
        Collections.sort(result, (s1, s2) -> {
            if (s1.getWholeWeight() > s2.getWholeWeight()) return -1;
            else if (s2.getWholeWeight() > s1.getWholeWeight()) return 1;
            else return 0;
        });
        return result;
    }

    public List<RailroadCar> sortCars(List<RailroadCar> list){
        List<RailroadCar> result = new ArrayList<>(list);
        Collections.sort(result, (s1, s2) -> {
            if (s1.getGrossWeight() > s2.getGrossWeight()) return -1;
            else if (s2.getGrossWeight() > s1.getGrossWeight()) return 1;
            else return 0;
        });
        return result;
    }
}
