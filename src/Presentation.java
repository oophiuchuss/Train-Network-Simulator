import RailroadCars.*;

import java.util.ArrayList;
import java.util.List;

public class Presentation {
    public static void main(String[] args) {

//            generating list of stations and connections between them
        StationGenerator t1 = new StationGenerator(10, 7, 8, 0.1, 0.3);
        t1.generate();
        System.out.println("List of stations -> " + t1.stationList);
        //to show connections
        System.out.println("List of connection for first station -> " + t1.stationList.get(0).getAdjacentStations());
        System.out.println();

//            generating list of trainsets with railroad cars and locomotive
        TrainsetGenerator t2 = new TrainsetGenerator(3, 3, 5, t1.stationList);
        t2.generate();
        System.out.println("List of trains -> " + t2.trainsetList);
        //to show railroad cars
        System.out.println("List of attached car to first trainset -> " + t2.trainsetList.get(0).getConnectedCars());
        System.out.println();

//            possibility to search (using ShortestPathAlgorithm class) and set path for trainset
        Trainset tr = t2.trainsetList.get(0);
        tr.setPath(t1.stationList.get(0), t1.stationList.get(9));
        System.out.println("Path from the first station to the 10th station -> " + tr.getPath());
        System.out.println();

//        ability so start and stop trainset as thread (if spped will more than 200km/h, the appropriate message will appear)
        tr.start();
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
        }
        tr.interrupt();
        System.out.println();

//        ability to add railroad cars (if car cannot be added the appropriate message will appear)
        try {
            tr.addRailroadCar(new RailroadCarToxicMaterials(130000, 80000, "Hydrochloric acid", "Plastic", 40, true));
            System.out.println("New car added -> " + tr.getConnectedCars());
        } catch (RailroadCarExceedLimits e) {
            System.err.println(e);
        }
        System.out.println();

//        we can call a method to remove all connections from current station and all who was connected before
        System.out.println("Before removing all connections -> " + t1.stationList.get(5).getAdjacentStations());
        t1.stationList.get(5).removeAllConnections();
        System.out.println("After removing all connections -> " + t1.stationList.get(5).getAdjacentStations());
        System.out.println();

//        there is a method by which we can find station by name from list
        Station forSearch = Station.getStationByName(t1.stationList, "Station 2");
        System.out.println("Searched station by name -> " + forSearch);
        System.out.println();

//        In RailroadHazard there are two connected methods to show redundant message from exception
        new RailroadHazard("First message");
        new RailroadHazard("First message");
        new RailroadHazard("Second message");
        new RailroadHazard("Second message");
        new RailroadHazard("First message");
        System.out.println("Used messages for RailroadHazard Exception");
        RailroadHazard.showRep();
        System.out.println();

//        In RailroadCarExceedLimits there are two connected methods to show redundant message from exception
        new RailroadCarExceedLimits("Third message");
        new RailroadCarExceedLimits("Fourth message");
        new RailroadCarExceedLimits("Fourth message");
        new RailroadCarExceedLimits("Fourth message");
        new RailroadCarExceedLimits("Third message");
        System.out.println("Used messages for RailroadCarExceedLimits Exception");
        RailroadCarExceedLimits.showRep();
        System.out.println();

//        All important objects have methods to show information about this object(for instance Locomotive object)
        Locomotive lc = tr.getHead();
        lc.showInfo();
        System.out.println();

//        In Connection class there are two methods: first return boolean is Station contains in list of Connections,
//        first for searching Connection from list by Station "to"
        Station station = t1.stationList.get(3);
        System.out.println("First method to ensure that connection is in the list: "
            + Connection.isContainsStation(t1.stationList.get(0).getAdjacentStations(), station));
        System.out.println("Second method to search connection with that Station from list: "
            + (Connection.findByStationTo(t1.stationList.get(0).getAdjacentStations(), station) != null ?
            Connection.findByStationTo(t1.stationList.get(0).getAdjacentStations(), station) : "Station wasn't connected"));
        System.out.println();

//        AppState class was implemented to show detailed information about trainsets
        AppState appState = new AppState(t2.trainsetList);
        System.out.println("Please wait 5 seconds");
        appState.start();
        try {
            Thread.sleep(6000);
        } catch (InterruptedException e) {}
        appState.interrupt();
        System.out.println("Now you can check files in source folder. There will be AppState.txt with all information.");
        System.out.println();

//      There are also 12 types of Railroad cars in each there two unique methods
        HeavyRailroadFreightCar a = new HeavyRailroadFreightCar(170000, 120000, "Coal", "Stee;");
        BasicRailroadFreightCar b = new BasicRailroadFreightCar(130000, 90800, "Sand", "Triple Crown");
        PassengerRailroadCar c = new PassengerRailroadCar(72000, Math.random() > 0.5 ? PassengerRailroadCar.ClassType.VIP : PassengerRailroadCar.ClassType.Second, 100);
        RailroadBaggageMailCar d = new RailroadBaggageMailCar(130000, 60000, "InPost");
        RailroadCarExplosives e = new RailroadCarExplosives(130000, 70000, "Azoclathrates", "Carbon steel", Math.random() > 0.5 ? RailroadCarExplosives.ExplosiveClass.A : RailroadCarExplosives.ExplosiveClass.D);
        RailroadCarGaseousMaterials f = new RailroadCarGaseousMaterials(130000, 80000, "Gasoline", "BNSF Railway");
        RailroadCarLiquidMaterials g = new RailroadCarLiquidMaterials(170000, 120000, "Water", "CSX Transportation");
        RailroadCarLiquidToxicMaterials h = new RailroadCarLiquidToxicMaterials(170000, 120000, "Chlorine", "Aluminum", 4, 70, false);
        RailroadPostOffice i = new RailroadPostOffice(130000,  35000, "Orlen");
        RailroadRestaurantCar j = new RailroadRestaurantCar(160000, 50, 5000, "Los Pollos Hermanos");
        RefrigeratedRailroadCar k = new RefrigeratedRailroadCar(200000, 70000, "Meat", "CN Intermodal");
        RailroadCarToxicMaterials l = new RailroadCarToxicMaterials(130000, 80000, "Hydrochloric acid", "Plastic", 70, false);

        System.out.println("HeavyRailroadFreightCar has method isEmtpy -> " + a.isEmpty());
        try {
            System.out.println("To " + a + " was loaded maximum cargo.");
            a.loadCargo(a.getCapacity());
        } catch (NotEnoughCapacity ex) {}
        System.out.println("HeavyRailroadFreightCar has method isFull -> " + a.isFull());
        System.out.println();

        System.out.println("BasicRailroadFreightCar has method remainingCapacity -> " + b.remainingCapacity());
        List<BasicRailroadFreightCar> temp1 = new ArrayList<>();
        temp1.add(new BasicRailroadFreightCar(130000, 90800, "Sand", "Triple Crown"));
        temp1.add(new BasicRailroadFreightCar(130000, 80000, "Sand", "Triple Crown"));
        temp1.add(new BasicRailroadFreightCar(130000, 99000, "Sand", "Triple Crown"));
        System.out.println("BasicRailroadFreightCar has static method availableWeigh from list of cars -> " + BasicRailroadFreightCar.availableWeight(temp1));
        System.out.println();

        System.out.println("PassengerRailroadCar has method checkMeal that checks is meal included by class of car -> " + c.checkMeal());
        List<PassengerRailroadCar> temp2 = new ArrayList<>();
        temp2.add(new PassengerRailroadCar(72000, Math.random() > 0.5 ? PassengerRailroadCar.ClassType.VIP : PassengerRailroadCar.ClassType.Second, 100));
        temp2.add(new PassengerRailroadCar(72000, Math.random() > 0.5 ? PassengerRailroadCar.ClassType.VIP : PassengerRailroadCar.ClassType.Second, 100));
        temp2.add(new PassengerRailroadCar(72000, Math.random() > 0.5 ? PassengerRailroadCar.ClassType.VIP : PassengerRailroadCar.ClassType.Second, 100));
        for (PassengerRailroadCar v: temp2
             ) {try {
                v.loadCargo(40);
            } catch (NotEnoughCapacity ex) {}
        }
        System.out.println("PassengerRailroadCar has static method allPassengers which calculates how many passengers-> " + PassengerRailroadCar.allPassengers(temp2));
        System.out.println();

        System.out.println("RailroadBaggageMailCar has method loseBaggage (with probability 1%) losing baggage and letters");
        d.loseBaggage(100);
        List<Integer> listOfInts = new ArrayList<>();
        listOfInts.add(1);
        listOfInts.add(12);
        listOfInts.add(14);
        listOfInts.add(14);
        listOfInts.add(11);
        listOfInts.add(2);
        List<Double> listOfDouble = new ArrayList<>();
        listOfDouble.add(0.2);
        listOfDouble.add(10.2);
        listOfDouble.add(0.22);
        listOfDouble.add(0.03);
        listOfDouble.add(21.2);
        System.out.println("RailroadBaggageMailCar has static method which calculating average of loosed baggage and letters(working with all kinds of number) -> Integers:" + RailroadBaggageMailCar.avgLost(listOfInts) + " Doubles: " +RailroadBaggageMailCar.avgLost(listOfDouble));
        System.out.println();

        try {
            e.loadCargo(10);
        } catch (NotEnoughCapacity ex) {}
        e.armExplosive();
        System.out.println("RailroadCarExplosives has method armExplosive which checks condition and makes car not safe -> isSafe: " + e.isSafe());
        e.defuseExplosive();
        System.out.println("RailroadCarExplosives has method defuseExplosive which checks condition and makes car safe -> isSafe: " + e.isSafe());
        System.out.println();

        f.increasePressure(20);
        System.out.println("RailroadCarGaseousMaterials has method increasePressure to increase pressure -> current Pressure: " + f.getCurrentPressure());
        f.decreasePressure(10);
        System.out.println("RailroadCarGaseousMaterials has method decreasePressure to decrease pressure -> current Pressure: " + f.getCurrentPressure());
        System.out.println();

        try {
            g.loadCargo(40);
            g.unloadCargo(10);
            g.unloadCargo(10);
            g.unloadCargo(10);
            g.unloadCargo(10);
        } catch (NotEnoughCapacity ex) {
            throw new RuntimeException(ex);
        }
        System.out.print("RailroadCarLiquidMaterials has method tankCleaning that set isCleaned to true and reset number of unloads -> Before:" + g.isCleaned());
        g.tankCleaning();
        System.out.println(" After: " + g.isCleaned());
        System.out.println("RailroadCarLiquidMaterials has static method getBasicCarAt that give an element from list which can be any type of BasicRailroadFreightCar -> " + RailroadCarLiquidMaterials.getBasicCarAt(temp1, 2) );
        System.out.println();

        List<RailroadCarLiquidToxicMaterials> temp3 = new ArrayList<>();
        temp3.add(new RailroadCarLiquidToxicMaterials(170000, 120000, "Chlorine", "Aluminum", 2, 70, true  ));
        temp3.add(new RailroadCarLiquidToxicMaterials(170000, 120000, "Chlorine", "Aluminum", 4,90, false  ));
        temp3.add(new RailroadCarLiquidToxicMaterials(170000, 120000, "Chlorine", "Aluminum", 1,10, false ));
        temp3.add(new RailroadCarLiquidToxicMaterials(170000, 120000, "Chlorine", "Aluminum", 3,30, true) );
        System.out.println("RailroadCarLiquidToxicMaterials has method indicateHazardous which determine is car is hazardous -> " + h.indicateHazardous());
        System.out.println("RailroadCarLiquidToxicMaterials has static method which calculates amount of hazardous cars -> " + RailroadCarLiquidToxicMaterials.numOfHazardousCars(temp3));
        System.out.println();

        i.setCurrentTemperature(-20);
        System.out.print("RailroadPostOffice has method checkTemperature which is called while loading cargo -> Before: " + i.getCurrentTemperature() );
        i.checkTemperature();
        System.out.println(" After: " + i.getCurrentTemperature());
        System.out.print("RailroadPostOffice has method changeTemperature which is called while unloading cargo ->Before: " + i.getCurrentTemperature() );
        i.changeTemperature();
        System.out.println(" After: " + i.getCurrentTemperature());
        System.out.println();

        try {
            j.loadCargo(10);
            j.loadCargo(10);
            j.loadCargo(10);
            System.out.println();
        } catch (NotEnoughCapacity ex) {}
        System.out.println("RailroadRestaurantCar has method neededFoodForMaxPeople which calculates amount(kg) of goods for maximum people -> " + j.neededFoodForMaxPeople());
        System.out.print("RailroadRestaurantCar has method serveMeal which is subtraction from goods needed amount gor serving n people -> Before: " + j.getNetWeight() );
        j.serveMeal(3);
        System.out.println(" After: " + j.getNetWeight());
        System.out.println();

        System.out.print("RefrigeratedRailroadCar has method changeTemp which changes temperature when loading/unloading cargo -> Temperature and isCooling before: " + k.getTemperature() + " " + k.isCooling());
        k.changeTemp();
        System.out.println(" After: "  + k.getTemperature() + " " + k.isCooling());
        k.setTemperature(20);
        System.out.print("RefrigeratedRailroadCar has method checkTemp which checks and changes conditions -> Temperature and isCooling before: " + k.getTemperature() + " " + k.isCooling());
        k.checkTemp();
        System.out.println(" After: "  + k.getTemperature() + " " + k.isCooling());
        System.out.println();

        List<RailroadCarToxicMaterials> temp4 = new ArrayList<>();
        temp4.add( new RailroadCarToxicMaterials(130000, 80000, "Hydrochloric acid", "Plastic", 70, false));
        temp4.add( new RailroadCarToxicMaterials(130000, 80000, "Hydrochloric acid", "Plastic", 70, false));
        temp4.add( new RailroadCarToxicMaterials(130000, 80000, "Hydrochloric acid", "Plastic", 70, false));
        temp4.add( new RailroadCarToxicMaterials(130000, 80000, "Hydrochloric acid", "Plastic", 70, false));
        System.out.print("RailroadCarToxicMaterials has method makeSafe which decreasing when is needed concentration percentage -> Before: " + l.getMaximumConcentrationPercentage());
        l.makeSafe();
        System.out.println(" After: " + l.getMaximumConcentrationPercentage());
        System.out.println("RailroadCarToxicMaterials has static method adjustConcentration which calculate average concentration percentage and return boolean was array made safe -> " + RailroadCarToxicMaterials.adjustConcentration(temp4));
        System.out.println();

//      There also two methods in Interface for liquid materials
        try{
            g.loadCargo(10);
            g.unloadCargo(1);
            g.unloadCargo(1);
            g.unloadCargo(1);
        }catch (NotEnoughCapacity ex){}
        System.out.println("InterfaceForLiquidMaterials has abstract method checkIsClean that checks amount of unloads -> " + g.checkIsClean());
        System.out.println("InterfaceForLiquidMaterials has abstract method tankState that checks is tank cleaned or not -> " + g.tankState());
        System.out.println();

//        There also two methods in NotEnoughCapacity Exception
        new NotEnoughCapacity("Fifth message");
        new NotEnoughCapacity("Fourth message");
        new NotEnoughCapacity("Fourth message");
        new NotEnoughCapacity("Fouth message");
        new NotEnoughCapacity("Fifth message");
        System.out.println("Used messages for NotEnoughCapacity Exception");
        NotEnoughCapacity.showRep();
        System.out.println();

//        There is Menu class with 15 different methods for manipulating with network (It is better to try in Main.java file)
        StationGenerator menuStations =new StationGenerator(100, 2, 4, 1, 10);
        menuStations.generate();
        TrainsetGenerator menuTrainsets = new TrainsetGenerator(25,0,2,menuStations.stationList);
        menuTrainsets.generate();

        Menu menu = new Menu(menuStations.stationList, menuTrainsets.trainsetList, new AppState(t2.trainsetList));
        menu.start();

    }
}
