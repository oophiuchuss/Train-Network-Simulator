import RailroadCars.*;

import java.util.*;

public class Menu {
    private final List<Station> stations;
    private final List<Trainset> trainsetList;
    private final AppState appState;
    private final HashMap<String, Runnable> basic = new HashMap<>();
    private final List<RailroadCar> unAttachedCar = new ArrayList<>();
    private boolean isRunning;
    private boolean isLooped;

    public Menu(List<Station> stations, List<Trainset> trainsetList, AppState appState) {
        this.stations = stations;
        this.trainsetList = trainsetList;
        this.appState = appState;
        basic.put("1", () -> createStation());
        basic.put("2", () -> createConnection());
        basic.put("3", () -> createTrainset());
        basic.put("4", () -> createCar());
        basic.put("5", () -> attachCar());
        basic.put("6", () -> loadCar());
        basic.put("7", () -> unloadCar());
        basic.put("8", () -> showByID());
        basic.put("9", () -> removeTrainset());
        basic.put("10", () -> removeStation());
        basic.put("11", () -> removeCar());
        basic.put("12", () -> showListOfTrainsets());
        basic.put("13", () -> showListOfStations());
        basic.put("14", () -> showListOfUnattachedCars());
        basic.put("0", () -> exit());
    }

    public void start() {
        Scanner scanner = new Scanner(System.in);
        isRunning = true;
        while (isRunning) {
            displayMenu();
            String choice = scanner.nextLine();
            Runnable option = basic.get(choice);

            if (option != null) {
                option.run();
            } else {
                System.out.println("Invalid choice, please try again.");
            }
        }
    }

    //commands
    private void displayMenu() {
        System.out.println("Train Network Menu:");
        System.out.println("1. Create Station");
        System.out.println("2. Create Connection");
        System.out.println("3. Create Trainset");
        System.out.println("4. Create Car");
        System.out.println("5. Attach Car");
        System.out.println("6. Load Car");
        System.out.println("7. Unload Car");
        System.out.println("8. Show information of trainset by ID");
        System.out.println("9. Remove trainset");
        System.out.println("10.Remove Station");
        System.out.println("11.Remove Car");
        System.out.println("12.Show list of trainsets");
        System.out.println("13.Show list of stations");
        System.out.println("14.Show list of unattached cars");
        System.out.println("0.Exit");
    }

    public void createStation() {
        Scanner s = new Scanner(System.in);
        String name;
        {
            System.out.println("Enter name for station: ");
            name = s.nextLine();
            if (Station.getStationByName(stations, name) == null) isLooped = false;
            else System.out.println("This name is already used.");
        }
        Station userStation = new Station(name);
        stations.add(userStation);
        for (Station v : stations
        ) {
            int numOfCon = 3 + (int) (Math.random() * (3));
            for (int i = 0; i < numOfCon; i++) {
                int indexForRand = (int) (Math.random() * (stations.size()));
                Station toAdd = stations.get(indexForRand);
                if (toAdd != v && !Connection.isContainsStation(v.getAdjacentStations(), toAdd)) {
                    double weight = 1 + (Math.random() * 5);
                    v.getAdjacentStations().add(new Connection(toAdd, weight));
                    toAdd.getAdjacentStations().add(new Connection(v, weight));
                }
            }
        }
        System.out.println("Station " + name + " created. For stability of network several connections was created automatically.");
    }

    public void createConnection() {
        Scanner s = new Scanner(System.in);
        Station sourceStation = null;
        Station targetStation = null;
        isLooped = true;
        while (isLooped) {
            System.out.print("Enter source station name: ");
            String sourceName = s.nextLine();
            sourceStation = Station.getStationByName(stations, sourceName);
            if (sourceStation != null) isLooped = false;
            else System.out.println("Invalid name, please try again.");
        }
        isLooped = true;
        while (isLooped) {
            System.out.print("Enter target station name: ");
            String targetName = s.nextLine();
            targetStation = Station.getStationByName(stations, targetName);
            if (targetStation != null) if (sourceStation != targetStation)
                if (!Connection.isContainsStation(sourceStation.getAdjacentStations(), targetStation)) isLooped = false;
                else System.out.println("That connection already exists.");
            else System.out.println("Invalid name, connection should be between two different stations.");
            else System.out.println("Invalid name, please try again.");
        }
        System.out.print("Enter connection distance: ");
        double distance = s.nextDouble();
        s.nextLine();
        Connection connection1 = new Connection(targetStation, distance);
        sourceStation.getAdjacentStations().add(connection1);
        Connection connection2 = new Connection(sourceStation, distance);
        targetStation.getAdjacentStations().add(connection2);
        System.out.println("Connection between " + sourceStation + " and " + targetStation + " created.");

    }

    public void createTrainset() {
        Scanner s = new Scanner(System.in);
        System.out.println("Enter name for Trainset: ");
        String nameTr = s.nextLine();

        Station homeStation = null;
        isLooped = true;
        while (isLooped) {
            System.out.print("Enter home station name for trainset: ");
            String stationName = s.nextLine();
            homeStation = Station.getStationByName(stations, stationName);
            if (homeStation != null) isLooped = false;
            else System.out.println("Invalid name, please try again.");
        }
        System.out.println("Enter name for Locomotive: ");
        String nameLc = s.nextLine();
        int maxCars = 0;
        double maxWeight = 0;
        int maxElectricity = 0;
        double velocity = 0;
        isLooped = true;
        while (isLooped) {
            System.out.println("Enter maximum of cars for Locomotive: ");
            maxCars = s.nextInt();
            s.nextLine();
            if (maxCars >= 0) isLooped = false;
            else System.out.println("Value should be positive. Please try again.");
        }
        isLooped = true;
        while (isLooped) {
            System.out.println("Enter maximum weight for Locomotive: ");
            maxWeight = s.nextDouble();
            s.nextLine();
            if (maxWeight >= 0) isLooped = false;
            else System.out.println("Value should be positive. Please try again.");
        }
        isLooped = true;
        while (isLooped) {
            System.out.println("Enter maximum of Electricity grid for cars for Locomotive: ");
            maxElectricity = s.nextInt();
            s.nextLine();
            if (maxElectricity >= 0) isLooped = false;
            else System.out.println("Value should be positive. Please try again.");
        }
        isLooped = true;
        while (isLooped) {
            System.out.println("Enter velocity for Locomotive: ");
            velocity = s.nextDouble();
            s.nextLine();
            if (velocity >= 0) isLooped = false;
            else System.out.println("Value should be positive. Please try again.");
        }
        Trainset result = new Trainset(nameTr, new Locomotive(maxCars, maxWeight, maxElectricity, velocity, nameLc), homeStation, new ArrayList<>(), stations);
        trainsetList.add(result);
        System.out.println("Trainset " + nameTr + " was created.");


        Station sourceStation = null;
        Station destStation = null;
        isLooped = true;
        while (isLooped) {
            System.out.print("Enter source station name for trainset: ");
            String sourceName = s.nextLine();
            sourceStation = Station.getStationByName(stations, sourceName);
            if (sourceStation != null) isLooped = false;
            else System.out.println("Invalid name, please try again.");
        }
        isLooped = true;
        while (isLooped) {
            System.out.print("Enter destination station name for trainset: ");
            String destName = s.nextLine();
            destStation = Station.getStationByName(stations, destName);
            if (destStation != null) isLooped = false;
            else System.out.println("Invalid name, please try again.");
        }
        result.setPath(sourceStation, destStation);
        result.start();
        System.out.println(result + " was created.");
    }

    public void createCar() {
        Scanner scanner = new Scanner(System.in);
        RailroadCar toAdd = null;
        double initialWeight = 0;
        double capacity = 0;
        //All created cars by user will be stored in a spacial list of unAttachedCars
        System.out.println("Which type of Railroad car do you want to create?");
        System.out.println("1. Passenger railroad car");
        System.out.println("2. Railroad post office");
        System.out.println("3. Railroad baggage and mail car");
        System.out.println("4. Railroad restaurant car");
        System.out.println("5. Basic railroad freight car");
        System.out.println("6. Heavy railroad freight car");
        System.out.println("7. Refrigerated railroad car");
        System.out.println("8. Railroad car for liquid materials");
        System.out.println("9. Railroad car for gaseous materials");
        System.out.println("10.Railroad car for explosives");
        System.out.println("11.Railroad car for toxic materials");
        System.out.println("12.Railroad car for liquid, toxic material");
        isLooped = true;
        while (isLooped) {
            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1:
                    PassengerRailroadCar.ClassType classType = null;
                    int numOfSeats = 0;
                    isLooped = true;
                    while (isLooped) {
                        System.out.println("Enter the initial weight of the car: ");
                        initialWeight = scanner.nextDouble();
                        scanner.nextLine();
                        if (initialWeight <= 0) System.out.println("Initial weight can't be negative or zero");
                        else isLooped = false;
                    }
                    isLooped = true;
                    while (isLooped) {
                        System.out.println("Enter the type of class for the car ( VIP, First, Second): ");
                        String tmp = scanner.nextLine();

                        switch (tmp) {
                            case "VIP" -> classType = PassengerRailroadCar.ClassType.VIP;
                            case "First" -> classType = PassengerRailroadCar.ClassType.First;
                            case "Second" -> classType = PassengerRailroadCar.ClassType.Second;
                            default -> System.out.println("Choose between those three types, please.");
                        }
                        if (classType != null) isLooped = false;
                    }
                    isLooped = true;
                    while (isLooped) {
                        System.out.println("Enter the number of seats for the car: ");
                        numOfSeats = scanner.nextInt();
                        scanner.nextLine();
                        if (numOfSeats <= 0) System.out.println("Number of seats can't be negative or zero");
                        else isLooped = false;
                    }
                    toAdd = new PassengerRailroadCar(initialWeight, classType, numOfSeats);
                    isLooped = false;
                    break;
                case 2:
                    isLooped = true;
                    while (isLooped) {
                        System.out.println("Enter the initial weight of the car: ");
                        initialWeight = scanner.nextDouble();
                        scanner.nextLine();
                        if (initialWeight <= 0) System.out.println("Initial weight can't be negative or zero");
                        else isLooped = false;
                    }
                    isLooped = true;
                    while (isLooped) {
                        System.out.println("Enter the capacity of mail for the car: ");
                        capacity = scanner.nextDouble();
                        scanner.nextLine();
                        if (capacity <= 0) System.out.println("Capacity can't be negative or zero");
                        else isLooped = false;
                    }
                    System.out.println("Enter name of the mail company: ");
                    String name = scanner.nextLine();
                    toAdd = new RailroadPostOffice(initialWeight, capacity, name);
                    isLooped = false;
                    break;
                case 3:

                    isLooped = true;
                    while (isLooped) {
                        System.out.println("Enter the initial weight of the car:");
                        initialWeight = scanner.nextDouble();
                        scanner.nextLine();
                        if (initialWeight <= 0) System.out.println("Initial weight can't be negative or zero");
                        else isLooped = false;
                    }
                    isLooped = true;
                    while (isLooped) {
                        System.out.println("Enter the capacity of baggage and packages for the car: ");
                        capacity = scanner.nextDouble();
                        scanner.nextLine();
                        if (capacity <= 0) System.out.println("Capacity can't be negative or zero");
                        else isLooped = false;
                    }
                    System.out.println("Enter name of the mail company: ");
                    name = scanner.nextLine();
                    toAdd = new RailroadBaggageMailCar(initialWeight, capacity, name);
                    isLooped = false;
                    break;
                case 4:
                    numOfSeats = 0;
                    isLooped = true;
                    while (isLooped) {
                        System.out.println("Enter the initial weight of the car:");
                        initialWeight = scanner.nextDouble();
                        scanner.nextLine();
                        if (initialWeight <= 0) System.out.println("Initial weight can't be negative or zero");
                        else isLooped = false;
                    }
                    isLooped = true;
                    while (isLooped) {
                        System.out.println("Enter the number of seats for the car:");
                        numOfSeats = scanner.nextInt();
                        scanner.nextLine();
                        if (numOfSeats <= 0) System.out.println("Number of seats can't be negative or zero");
                        else isLooped = false;
                    }
                    isLooped = true;
                    while (isLooped) {
                        System.out.println("Enter the capacity of goods for the car: ");
                        capacity = scanner.nextDouble();
                        scanner.nextLine();
                        if (capacity <= 0) System.out.println("Capacity can't be negative or zero");
                        else isLooped = false;
                    }
                    System.out.println("Enter the name of the restaurant: ");
                    name = scanner.nextLine();
                    toAdd = new RailroadRestaurantCar(initialWeight, numOfSeats, capacity, name);

                    isLooped = false;
                    break;
                case 5:


                    isLooped = true;
                    while (isLooped) {
                        System.out.println("Enter the initial weight of the car:");
                        initialWeight = scanner.nextDouble();
                        scanner.nextLine();
                        if (initialWeight <= 0) System.out.println("Initial weight can't be negative or zero");
                        else isLooped = false;
                    }
                    isLooped = true;
                    while (isLooped) {
                        System.out.println("Enter the capacity of cargo for the car: ");
                        capacity = scanner.nextDouble();
                        scanner.nextLine();
                        if (capacity <= 0) System.out.println("Capacity can't be negative or zero");
                        else isLooped = false;
                    }
                    System.out.println("Enter the name of transported cargo: ");
                    String cargo = scanner.nextLine();
                    System.out.println("Enter the name of the railroad company: ");
                    name = scanner.nextLine();
                    toAdd = new BasicRailroadFreightCar(initialWeight, capacity, cargo, name);

                    isLooped = false;
                    break;
                case 6:

                    isLooped = true;
                    while (isLooped) {
                        System.out.println("Enter the initial weight of the car:");
                        initialWeight = scanner.nextDouble();
                        scanner.nextLine();
                        if (initialWeight <= 0) System.out.println("Initial weight can't be negative or zero");
                        else isLooped = false;
                    }
                    isLooped = true;
                    while (isLooped) {
                        System.out.println("Enter the capacity of cargo for the car: ");
                        capacity = scanner.nextDouble();
                        scanner.nextLine();
                        if (capacity <= 0) System.out.println("Capacity can't be negative or zero");
                        else isLooped = false;
                    }
                    System.out.println("Enter the name of transported cargo: ");
                    cargo = scanner.nextLine();
                    System.out.println("Enter the material of the car: ");
                    name = scanner.nextLine();
                    toAdd = new HeavyRailroadFreightCar(initialWeight, capacity, cargo, name);

                    isLooped = false;
                    break;
                case 7:

                    isLooped = true;
                    while (isLooped) {
                        System.out.println("Enter the initial weight of the car:");
                        initialWeight = scanner.nextDouble();
                        scanner.nextLine();
                        if (initialWeight <= 0) System.out.println("Initial weight can't be negative or zero");
                        else isLooped = false;
                    }
                    isLooped = true;
                    while (isLooped) {
                        System.out.println("Enter the capacity of cargo for the car: ");
                        capacity = scanner.nextDouble();
                        scanner.nextLine();
                        if (capacity <= 0) System.out.println("Capacity can't be negative or zero");
                        else isLooped = false;
                    }
                    System.out.println("Enter the name of transported cargo: ");
                    cargo = scanner.nextLine();
                    System.out.println("Enter the name of the railroad company: ");
                    name = scanner.nextLine();
                    toAdd = new RefrigeratedRailroadCar(initialWeight, capacity, cargo, name);

                    isLooped = false;
                    break;
                case 8:

                    isLooped = true;
                    while (isLooped) {
                        System.out.println("Enter the initial weight of the car:");
                        initialWeight = scanner.nextDouble();
                        scanner.nextLine();
                        if (initialWeight <= 0) System.out.println("Initial weight can't be negative or zero");
                        else isLooped = false;
                    }
                    isLooped = true;
                    while (isLooped) {
                        System.out.println("Enter the capacity of cargo for the car: ");
                        capacity = scanner.nextDouble();
                        scanner.nextLine();
                        if (capacity <= 0) System.out.println("Capacity can't be negative or zero");
                        else isLooped = false;
                    }
                    System.out.println("Enter the name of transported cargo: ");
                    cargo = scanner.nextLine();

                    System.out.println("Enter the name of the railroad company: ");
                    name = scanner.nextLine();

                    toAdd = new RailroadCarLiquidMaterials(initialWeight, capacity, cargo, name);

                    isLooped = false;
                    break;
                case 9:
                    isLooped = true;
                    while (isLooped) {
                        System.out.println("Enter the initial weight of the car:");
                        initialWeight = scanner.nextDouble();
                        scanner.nextLine();
                        if (initialWeight <= 0) System.out.println("Initial weight can't be negative or zero");
                        else isLooped = false;
                    }
                    isLooped = true;
                    while (isLooped) {
                        System.out.println("Enter the capacity of cargo for the car: ");
                        capacity = scanner.nextDouble();
                        scanner.nextLine();
                        if (capacity <= 0) System.out.println("Capacity can't be negative or zero");
                        else isLooped = false;
                    }
                    System.out.println("Enter the name of transported cargo: ");
                    cargo = scanner.nextLine();
                    System.out.println("Enter the name of the railroad company: ");
                    name = scanner.nextLine();
                    toAdd = new RailroadCarGaseousMaterials(initialWeight, capacity, cargo, name);
                    isLooped = false;
                    break;
                case 10:
                    isLooped = true;
                    while (isLooped) {
                        System.out.println("Enter the initial weight of the car:");
                        initialWeight = scanner.nextDouble();
                        scanner.nextLine();
                        if (initialWeight <= 0) System.out.println("Initial weight can't be negative or zero");
                        else isLooped = false;
                    }
                    isLooped = true;
                    while (isLooped) {
                        System.out.println("Enter the capacity of cargo for the car: ");
                        capacity = scanner.nextDouble();
                        scanner.nextLine();
                        if (capacity <= 0) System.out.println("Capacity can't be negative or zero");
                        else isLooped = false;
                    }
                    System.out.println("Enter the name of transported cargo: ");
                    cargo = scanner.nextLine();
                    System.out.println("Enter the material of the car: ");
                    name = scanner.nextLine();
                    RailroadCarExplosives.ExplosiveClass explosiveClass = null;
                    isLooped = true;
                    while (isLooped) {
                        System.out.println("Enter the type of explosive class for the material ( A, B, C, D): ");
                        String tmp = scanner.nextLine();
                        switch (tmp) {
                            case "A" -> explosiveClass = RailroadCarExplosives.ExplosiveClass.A;
                            case "B" -> explosiveClass = RailroadCarExplosives.ExplosiveClass.B;
                            case "C" -> explosiveClass = RailroadCarExplosives.ExplosiveClass.C;
                            case "D" -> explosiveClass = RailroadCarExplosives.ExplosiveClass.D;
                            default -> System.out.println("Choose between those four types, please.");
                        }
                        if (explosiveClass != null) isLooped = false;
                    }
                    toAdd = new RailroadCarExplosives(initialWeight, capacity, cargo, name, explosiveClass);

                    isLooped = false;
                    break;
                case 11:
                    isLooped = true;
                    while (isLooped) {
                        System.out.println("Enter the initial weight of the car:");
                        initialWeight = scanner.nextDouble();
                        scanner.nextLine();
                        if (initialWeight <= 0) System.out.println("Initial weight can't be negative or zero");
                        else isLooped = false;
                    }
                    isLooped = true;
                    while (isLooped) {
                        System.out.println("Enter the capacity of cargo for the car: ");
                        capacity = scanner.nextDouble();
                        scanner.nextLine();
                        if (capacity <= 0) System.out.println("Capacity can't be negative or zero");
                        else isLooped = false;
                    }
                    System.out.println("Enter the name of transported cargo: ");
                    cargo = scanner.nextLine();
                    System.out.println("Enter the material of the car: ");
                    name = scanner.nextLine();
                    int maxConPer = 0;
                    isLooped = true;
                    while (isLooped) {
                        System.out.println("Enter the percentage of maximum concentration of toxic material: ");
                        maxConPer = scanner.nextInt();
                        scanner.nextLine();
                        if (maxConPer <= 0 || maxConPer > 100)
                            System.out.println("Percentage can't be negative, zero or more then hundred");
                        else isLooped = false;
                    }
                    boolean ventilation = false;
                    isLooped = true;
                    while (isLooped) {
                        System.out.println("Is ventilation in toxic railroad car is on (y/n): ");
                        String tmp = scanner.nextLine();
                        if (tmp.equals("y")) {
                            ventilation = true;
                            isLooped = false;
                        } else if (tmp.equals("n")) {
                            ventilation = false;
                            isLooped = false;
                        } else System.out.println("Invalid input. Please, try again.");
                    }
                    toAdd = new RailroadCarToxicMaterials(initialWeight, capacity, cargo, name, maxConPer, ventilation);

                    isLooped = false;
                    break;
                case 12:
                    isLooped = true;
                    while (isLooped) {
                        System.out.println("Enter the initial weight of the car:");
                        initialWeight = scanner.nextDouble();
                        scanner.nextLine();
                        if (initialWeight <= 0) System.out.println("Initial weight can't be negative or zero");
                        else isLooped = false;
                    }
                    isLooped = true;
                    while (isLooped) {
                        System.out.println("Enter the capacity of cargo for the car: ");
                        capacity = scanner.nextDouble();
                        scanner.nextLine();
                        if (capacity <= 0) System.out.println("Capacity can't be negative or zero");
                        else isLooped = false;
                    }
                    System.out.println("Enter the name of transported cargo: ");
                    cargo = scanner.nextLine();
                    System.out.println("Enter the material of the car: ");
                    name = scanner.nextLine();
                    int containmentLevel = 0;
                    isLooped = true;
                    while (isLooped) {
                        System.out.println("Enter the containment level of toxic, liquid material:(1-4) ");
                        containmentLevel = scanner.nextInt();
                        scanner.nextLine();
                        if (containmentLevel < 1 || containmentLevel > 4)
                            System.out.println("Containment level can be only: 1,2,3,4");
                        else isLooped = false;
                    }
                    maxConPer = 0;
                    isLooped = true;
                    while (isLooped) {
                        System.out.println("Enter the percentage of maximum concentration of toxic material: ");
                        maxConPer = scanner.nextInt();
                        scanner.nextLine();
                        if (maxConPer <= 0 || maxConPer > 100)
                            System.out.println("Percentage can't be negative, zero or more then hundred");
                        else isLooped = false;
                    }
                    ventilation = false;
                    isLooped = true;
                    while (isLooped) {
                        System.out.println("Is ventilation in toxic railroad car is on (y/n): ");
                        String tmp = scanner.nextLine();
                        if (tmp.equals("y")) {
                            ventilation = true;
                            isLooped = false;
                        } else if (tmp.equals("n")) {
                            ventilation = false;
                            isLooped = false;
                        } else System.out.println("Invalid input. Please, try again.");
                    }
                    toAdd = new RailroadCarLiquidToxicMaterials(initialWeight, capacity, cargo, name, containmentLevel, maxConPer,ventilation);
                default:
                    System.out.println("Invalid choice. Try again.");
            }
            if (toAdd == null) System.out.println("The car wasn't created. Please try again.");
            else isLooped = false;
        }
        unAttachedCar.add(toAdd);
        System.out.println(toAdd + " was created.");
    }

    public void attachCar() {

        Scanner s = new Scanner(System.in);
        String carName;
        RailroadCar toAttach = null;
        isLooped = true;
        while (isLooped) {
            System.out.print("Enter name of railroad car to attach: ");
            carName = s.nextLine();
            toAttach = RailroadCar.getCarByName(unAttachedCar, carName);
            if (toAttach != null) isLooped = false;
            else System.out.println("Invalid name, please try again.");
        }
        Trainset toUse = null;
        isLooped = true;
        while (isLooped) {
            System.out.print("Enter name of Trainset to which car will be attached: ");
            String name = s.nextLine();
            toUse = Trainset.getTrainsetByName(trainsetList, name);
            if (toUse != null) isLooped = false;
            else System.out.println("Invalid name, please try again.");
        }
        boolean isAttached = true;
        try {
            toUse.addRailroadCar(toAttach);
        } catch (RailroadCarExceedLimits a) {
            System.err.println(a.getMessage());
            isAttached = false;
        }
        if (isAttached) {
            unAttachedCar.remove(toAttach);
            System.out.println(toAttach + " was attached to " + toUse);
        }
    }

    public void loadCar() {
        Scanner s = new Scanner(System.in);
        String userInput;
        boolean isLoaded = false;
        isLooped = true;
        while (isLooped) {
            System.out.println("Railroad car that you want to load is connected to any trainset? (y/n)");
            userInput = s.nextLine();
            switch (userInput) {
                case "y": {
                    Trainset fromTs = null;
                    isLooped = true;
                    while (isLooped) {
                        System.out.print("Enter name of Trainset in which needed car is located: ");
                        String name = s.nextLine();
                        fromTs = Trainset.getTrainsetByName(trainsetList, name);
                        if (fromTs != null) isLooped = false;
                        else System.out.println("Invalid name, please try again.");
                    }
                    RailroadCar toLoad = null;
                    String carName;
                    isLooped = true;
                    while (isLooped) {
                        System.out.print("Enter name of railroad car to load: ");
                        carName = s.nextLine();
                        toLoad = RailroadCar.getCarByName(fromTs.getConnectedCars(), carName);
                        if (toLoad != null) isLooped = false;
                        else System.out.println("Invalid name, please try again.");
                    }
                    double loadedWeight;
                    System.out.print("Enter weight of cargo to load (number of people to load): ");
                    loadedWeight = s.nextDouble();
                    s.nextLine();
                    isLoaded = true;
                    try {
                        toLoad.loadCargo(loadedWeight);
                    } catch (NotEnoughCapacity e) {
                        System.err.println(e.getMessage());
                        isLoaded = false;
                    }
                    if (isLoaded) System.out.println("Cargo was loaded.");
                }
                isLooped = false;
                break;
                case "n": {
                    RailroadCar toLoad = null;
                    String carName;
                    isLooped = true;
                    while (isLooped) {
                        System.out.print("Enter name of railroad car to load: ");
                        carName = s.nextLine();
                        toLoad = RailroadCar.getCarByName(unAttachedCar, carName);
                        if (toLoad != null) isLooped = false;
                        else System.out.println("Invalid name, please try again.");
                    }
                    double loadedWeight;
                    System.out.print("Enter weight of cargo to load (number of people to load): ");
                    loadedWeight = s.nextDouble();
                    s.nextLine();
                    isLoaded = true;
                    try {
                        toLoad.loadCargo(loadedWeight);
                    } catch (NotEnoughCapacity e) {
                        System.err.println(e.getMessage());
                        isLoaded = false;
                    }
                    if (isLoaded) System.out.println("Cargo was loaded.");
                }
                default:
                    System.out.println("Invalid input. Please try again");
            }
            if (isLoaded) isLooped = false;
        }
    }

    public void unloadCar() {
        Scanner s = new Scanner(System.in);
        String userInput;
        boolean isUnloaded = false;
        isLooped = true;
        while (isLooped) {
            System.out.println("Railroad car that you want to unload is connected to any trainset? (y/n)");
            userInput = s.nextLine();
            switch (userInput) {
                case "y": {
                    Trainset fromTs = null;
                    isLooped = true;
                    while (isLooped) {
                        System.out.print("Enter name of Trainset in which needed car is located: ");
                        String name = s.nextLine();
                        fromTs = Trainset.getTrainsetByName(trainsetList, name);
                        if (fromTs != null) isLooped = false;
                        else System.out.println("Invalid name, please try again.");
                    }
                    RailroadCar toUnload = null;
                    String carName;
                    isLooped = true;
                    while (isLooped) {
                        System.out.print("Enter name of railroad car to unload: ");
                        carName = s.nextLine();
                        toUnload = RailroadCar.getCarByName(fromTs.getConnectedCars(), carName);
                        if (toUnload != null) isLooped = false;
                        else System.out.println("Invalid name, please try again.");
                    }
                    double unloadedWeight;
                    System.out.print("Enter weight of cargo to unload (number of people to unload): ");
                    unloadedWeight = s.nextDouble();
                    s.nextLine();
                    isUnloaded = true;
                    try {
                        toUnload.unloadCargo(unloadedWeight);
                    } catch (NotEnoughCapacity e) {
                        System.err.println(e.getMessage());
                        isUnloaded = false;
                    }
                    if (isUnloaded) System.out.println("Cargo was unloaded.");
                }
                isLooped = false;
                break;
                case "n": {
                    RailroadCar toUnload = null;
                    String carName;
                    isLooped = true;
                    while (isLooped) {
                        System.out.print("Enter name of railroad car to unload: ");
                        carName = s.nextLine();
                        s.nextLine();
                        toUnload = RailroadCar.getCarByName(unAttachedCar, carName);
                        if (toUnload != null) isLooped = false;
                        else System.out.println("Invalid name, please try again.");
                    }
                    double unloadedWeight;
                    System.out.print("Enter weight of cargo to unload (number of people to unload): ");
                    unloadedWeight = s.nextDouble();
                    s.nextLine();
                    isUnloaded = true;
                    try {
                        toUnload.unloadCargo(unloadedWeight);
                    } catch (NotEnoughCapacity e) {
                        System.err.println(e.getMessage());
                        isUnloaded = false;
                    }
                    if (isUnloaded) System.out.println("Cargo was unloaded.");
                }
                default:
                    System.out.println("Invalid input. Please try again");
            }
            if (isUnloaded) isLooped = false;
        }
    }

    public void showByID() {
        Scanner s = new Scanner(System.in);
        Trainset toShow = null;
        isLooped = true;
        while (isLooped) {
            System.out.print("Enter ID of Trainset to show: ");
            int id = s.nextInt();
            for (Trainset v : trainsetList) {
                if (v.getUniqueNum() == id) toShow = v;
            }
            if (toShow != null) isLooped = false;
            else System.out.println("Invalid name, please try again.");
        }
        toShow.showInfo();
    }

    public void removeTrainset() {
        Scanner s = new Scanner(System.in);
        Trainset toRemove = null;
        isLooped = true;
        while (isLooped) {
            System.out.print("Enter name of Trainset to remove: ");
            String name = s.nextLine();
            toRemove = Trainset.getTrainsetByName(trainsetList, name);
            if (toRemove != null) isLooped = false;
            else System.out.println("Invalid name, please try again.");
        }
        toRemove.interrupt();
        trainsetList.remove(toRemove);
        if (toRemove.getCurrentStation().getQueueOfTrainsets() != null && toRemove.getCurrentStation().getQueueOfTrainsets().get(toRemove.getTargetStation()).contains(toRemove))
            toRemove.getCurrentStation().getQueueOfTrainsets().get(toRemove.getTargetStation()).remove(toRemove);
        System.out.println(toRemove + " is removed.");
    }

    public void removeStation() {
        Scanner s = new Scanner(System.in);
        Station toRemove = null;
        isLooped = true;
        while (isLooped) {
            System.out.print("Enter name of Station to remove: ");
            String name = s.nextLine();
            toRemove = Station.getStationByName(stations, name);
            if (toRemove != null) isLooped = false;
            else System.out.println("Invalid name, please try again.");
        }
        List<Trainset> temp = new ArrayList<>();
        for (Trainset v : trainsetList
        )
            if (v.getCurrentStation() == toRemove) {
                v.interrupt();
                temp.add(v);
                if (v.getCurrentStation().getQueueOfTrainsets().get(v.getTargetStation()) != null && v.getCurrentStation().getQueueOfTrainsets().get(v.getTargetStation()).contains(v))
                    v.getCurrentStation().getQueueOfTrainsets().get(v.getTargetStation()).remove(v);
            }
        for (Trainset v : temp
        ) {
            trainsetList.remove(v);
        }

        // Make all trainsets in the system wait
        for (Trainset trainset : trainsetList) {
            synchronized (trainset) {
                trainset.waitForSignal();
            }
        }

        // Remove the station from the list of stations
        stations.remove(toRemove);

        // Remove all connections to the station
        for (Station station : stations) {
            station.getAdjacentStations().remove(Connection.findByStationTo(station.getAdjacentStations(), toRemove));
        }

        // Remove all trainsets in the queue at the station
        for (Station station : stations) {
            Map<Station, Queue<Trainset>> queueOfTrainsets = station.getQueueOfTrainsets();
            if (queueOfTrainsets.containsKey(toRemove)) {
                Queue<Trainset> queue = queueOfTrainsets.get(toRemove);
                for (Trainset trainset : queue) {
                    trainset.interrupt();
                    trainsetList.remove(trainset);
                    System.out.println(trainset + " was removed because it was using station - " + toRemove);
                }
                queue.clear();
                queueOfTrainsets.remove(toRemove);
            }
        }

        // Notify all trainsets in the system
        for (Trainset trainset : trainsetList) {
            trainset.setPath(trainset.getCurrentStation(), trainset.getDestStation());
            synchronized (trainset) {
                trainset.resumeSignal();
            }
        }
    }

    public void removeCar() {
        Scanner s = new Scanner(System.in);
        RailroadCar toRemove = null;
        Trainset from = null;
        isLooped = true;
        while (isLooped) {
            System.out.print("Enter name of Trainset from which railroad car will be removed: ");
            String name = s.nextLine();
            from = Trainset.getTrainsetByName(trainsetList, name);
            if (from != null) isLooped = false;
            else System.out.println("Invalid name, please try again.");
        }
        System.out.println("Cars that are attached to " + from + ": ");
        for (RailroadCar v : from.getConnectedCars()
        ) {
            System.out.println(v);
        }
        isLooped = true;
        while (isLooped) {
            System.out.print("Enter name of railroad car which will be removed: ");
            String name = s.nextLine();
            toRemove = RailroadCar.getCarByName(from.getConnectedCars(), name);
            if (toRemove != null) isLooped = false;
            else System.out.println("Invalid name, please try again.");
        }
        from.getConnectedCars().remove(toRemove);
        System.out.println(toRemove + " was removed.");
    }

    public void showListOfTrainsets() {
        System.out.println("List of trainsets: ");
        for (Trainset v : trainsetList) {
            System.out.println(v + " with ID:" + v.getUniqueNum() + " and current station is " + v.getCurrentStation());
        }
    }

    public void showListOfStations() {
        System.out.println("List of stations and connections between them: ");
        for (Station v : stations) {
            System.out.println(v + " is connected with: ");
            for (Connection c : v.getAdjacentStations()
            ) {
                System.out.println("      " + c.getTo());
            }
        }
    }

    public void showListOfUnattachedCars() {
        System.out.println("List of unattached cars: ");
        for (RailroadCar v : unAttachedCar
        ) {
            System.out.println(v);
        }
    }

    public void exit() {
        isRunning = false;
        for (Trainset v : trainsetList
        ) {
            for (Thread a: v.getThreads()
                 ) {
                a.interrupt();
            }
            v.interrupt();
        }
        appState.interrupt();
    }

}
