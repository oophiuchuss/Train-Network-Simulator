import RailroadCars.*;

import java.util.ArrayList;
import java.util.List;

public class TrainsetGenerator {
    private final int numOfTrains;
    private final int minNumOfCarPerTrain;
    private final int maxNumOfCarPerTrain;
    public List<Station> stations;
    public final List<Trainset> trainsetList = new ArrayList<>();

    public TrainsetGenerator(int numOfTrains, int minNumOfCarPerTrain, int maxNumOfCarPerTrain, List<Station> stations) {
        this.numOfTrains = numOfTrains;
        this.minNumOfCarPerTrain = minNumOfCarPerTrain;
        this.maxNumOfCarPerTrain = maxNumOfCarPerTrain;
        this.stations = stations;
    }

    public void generate() {
        for (int i = 0; i < numOfTrains; i++) {
            List<RailroadCar> carList = new ArrayList<>();
            for (int j = 0; j < minNumOfCarPerTrain + (int) (Math.random() * (maxNumOfCarPerTrain + 1 - minNumOfCarPerTrain)); j++) {
                generateCar(carList);
            }
            double maxWeight = 1500000 + Math.random() * 300000;
            int maxElectricity = 3 + (int) (Math.random() * 7);
            int maxCars = 5 + (int) (Math.random() * 5);
            double velocity = 120 + Math.random() * 40;
            Trainset temp = new Trainset(new Locomotive(maxCars, maxWeight, maxElectricity, velocity), stations.get((int) (Math.random() * stations.size())), new ArrayList<>(), stations);
            for (RailroadCar c : carList
            ) {
                try {
                    temp.addRailroadCar(c);
                } catch (RailroadCarExceedLimits a) {

                }
            }
            trainsetList.add(temp);
        }
    }

    public void generateCar(List<RailroadCar> carList) {
        switch (1 + (int) (Math.random() * (12))) {
            case 1 -> carList.add(new HeavyRailroadFreightCar(170000, 120000, "Coal", "Stee;"));
            case 2 -> carList.add(new BasicRailroadFreightCar(130000, 90800, "Sand", "Triple Crown"));
            case 3 -> carList.add(new PassengerRailroadCar(72000, Math.random() > 0.5 ? PassengerRailroadCar.ClassType.VIP : PassengerRailroadCar.ClassType.Second, 100));
            case 4 -> carList.add(new RailroadBaggageMailCar(130000, 60000, "InPost"));
            case 5 -> carList.add(new RailroadCarExplosives(130000, 70000, "Azoclathrates", "Carbon steel", Math.random() > 0.5 ? RailroadCarExplosives.ExplosiveClass.A : RailroadCarExplosives.ExplosiveClass.D));
            case 6 -> carList.add(new RailroadCarGaseousMaterials(130000, 80000, "Gasoline", "BNSF Railway"));
            case 7 -> carList.add(new RailroadCarLiquidMaterials(170000, 120000, "Water", "CSX Transportation"));
            case 8 -> carList.add(new RailroadCarLiquidToxicMaterials(170000, 120000, "Chlorine", "Aluminum", 2, 40, true));
            case 9 -> carList.add(new RailroadPostOffice(130000,  35000, "Orlen"));
            case 10 -> carList.add(new RailroadRestaurantCar(160000, 50, 5000, "Los Pollos Hermanos"));
            case 11 -> carList.add(new RefrigeratedRailroadCar(200000, 70000, "Meat", "CN Intermodal"));
            case 12 -> carList.add(new RailroadCarToxicMaterials(130000, 80000, "Hydrochloric acid", "Plastic", 40, true));
            default -> System.out.println("Something went wrong");
        }
    }
}
