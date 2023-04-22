import RailroadCars.RailroadCar;

import java.util.*;

public class Trainset extends Thread {
    enum Status {Running, TryStart, Arrived, FinalArrived, Stopped, NULL}

    private final String name;
    private final Locomotive head;
    private final int uniqueNum;
    private static int numOfTrainsets = 0;
    private List<RailroadCar> connectedCars = new ArrayList<>(); /*At 0 position first car after Locomotive,
                                                                   and one after another is going by index*/
    private final Station homeStation;
    private Station sourceStation;
    private Station destStation;
    private Station currentStation;
    private Station targetStation;
    private List<Station> stations;
    private Connection connection;
    private Queue<Trainset> queue;
    private int index = 0;
    private double wholeWeight; /*in kilograms*/
    private double weightRoute; /*in kilograms*/
    private double travelledWeight = 0; /*in kilograms*/
    private double weightLeft; /*in kilograms*/
    private Status status = Status.NULL;
    private List<Station> path;
    private List<Thread> threads = new LinkedList<>();

    public Trainset(Locomotive head, Station homeStation, List<RailroadCar> connectedCars, List<Station> stations) {
        this.head = head;
        this.homeStation = homeStation;
        this.connectedCars = connectedCars;
        this.stations = stations;
        this.uniqueNum = numOfTrainsets + 1;
        numOfTrainsets++;
        this.name = "Trainset №" + uniqueNum;
    }

    public Trainset(String name, Locomotive head, Station homeStation, List<RailroadCar> connectedCars, List<Station> stations) {
        this.head = head;
        this.homeStation = homeStation;
        this.connectedCars = connectedCars;
        this.stations = stations;
        this.uniqueNum = numOfTrainsets + 1;
        numOfTrainsets++;
        this.name = name;
    }

    public Station getHomeStation() {
        return homeStation;
    }

    public Station getDestStation() {
        return destStation;
    }

    public Locomotive getHead() {
        return head;
    }

    public List<RailroadCar> getConnectedCars() {
        return connectedCars;
    }

    public Station getSourceStation() {
        return sourceStation;
    }

    public double getWholeWeight() {
        return wholeWeight;
    }

    public double getTravelledWeight() {
        return travelledWeight;
    }

    public List<Station> getPath() {
        return path;
    }

    public static Trainset getTrainsetByName(List<Trainset> trainsetList, String name) {
        for (Trainset t : trainsetList
        ) {
            if (t.toString().equals(name)) return t;
        }
        return null;
    }

    public Station getCurrentStation() {
        return currentStation;
    }

    public Station getTargetStation() {
        return targetStation;
    }

    public int getUniqueNum() {
        return uniqueNum;
    }

    public void setPath(Station from, Station to) {
        this.wholeWeight = 0;
        this.sourceStation = from;
        this.destStation = to;
        ShortestPathAlgorithm shortestPathAlgorithm = new ShortestPathAlgorithm(this.stations);
        shortestPathAlgorithm.execute(from);
        this.path = shortestPathAlgorithm.getShortestPathTo(to);
        this.currentStation = from;
        from.getQueueOfTrainsets().getOrDefault(path.get(0), new LinkedList<>()).add(this);
        for (int i = 0; i < path.size() - 1; i++) {
            Station currentStation = path.get(i);
            Station targetStation = path.get(i + 1);
            wholeWeight += currentStation.getConnectionTo(targetStation).getWeight();
        }
        this.connection = path.get(0).getConnectionTo(path.get(1));
    }

    public void addRailroadCar(RailroadCar car) throws RailroadCarExceedLimits {
        if (connectedCars.size() + 1 <= head.getMaxCars()) {
            double sumWeight = this.sumOfWeight();
            if (sumWeight + car.getMaxWeight() <= head.getMaxWeight()) {
                if ((head.getMaxElectricity() - head.getUsedElectricity()
                        - (car.isElectricityNeeded() ? 1 : 0)) >= 0) {
                    if (car.isElectricityNeeded()) this.head.setUsedElectricity(getHead().getUsedElectricity() + 1);
                    this.connectedCars.add(car);
                } else throw new RailroadCarExceedLimits(head + ": Not enough electricity for car");
            } else throw new RailroadCarExceedLimits(head + ": Car's maximum weight is to big");
        } else throw new RailroadCarExceedLimits(head + ": Amount of attached cars are on maximum");
    }

    public double sumOfWeight() {
        double result = 0;

        for (RailroadCar v : connectedCars
        ) {
            result += v.getMaxWeight();
        }
        return result;
    }

    @Override
    public void run() {
        status = Status.TryStart;
        while (status != Status.NULL && !Thread.currentThread().isInterrupted()) {
            currentStation = path.get(index);
            targetStation = path.get(index + 1);
            switch (status) {
                case TryStart:
                    Thread tryStartThread = new Thread(() -> {
                        if (!Thread.currentThread().isInterrupted()) tryStart();
                        else Thread.currentThread().interrupt();
                    });
                    threads.add(tryStartThread);
                    tryStartThread.start();
                    break;
                case Running:
                    Thread velocity = new Thread(() -> {
                        if (!Thread.currentThread().isInterrupted())
                            changeVelocity();
                        else Thread.currentThread().interrupt();
                    });
                    threads.add(velocity);
                    velocity.start();
                    Thread runningThread = new Thread(() -> {
                        if (!Thread.currentThread().isInterrupted())
                            move();
                        else Thread.currentThread().interrupt();

                    });
                    threads.add(runningThread);
                    runningThread.start();
                    break;
                case Arrived:
                    Thread arrivedThread = new Thread(() -> {
                        if (!Thread.currentThread().isInterrupted())
                            arrived();
                        else Thread.currentThread().interrupt();
                    });
                    threads.add(arrivedThread);
                    arrivedThread.start();
                    index++;
                    break;
                case FinalArrived:
                    Thread finalArrivedThread = new Thread(() -> {
                        if (!Thread.currentThread().isInterrupted())
                            finalArrived();
                        else Thread.currentThread().interrupt();
                    });
                    threads.add(finalArrivedThread);
                    finalArrivedThread.start();
                    break;
                case NULL:
                    for (Thread a : threads
                    ) {
                        a.interrupt();
                    }
            }

            // wait for all threads to finish before moving to the next status
            for (Thread thread : threads) {
                try {
                    thread.join();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    status = Status.NULL;
                }
            }
            // clear the threads list for the next status

            for (Thread a : threads
            ) {
                a.interrupt();
            }
            threads.clear();
        }
        synchronized (queue){
            queue.notifyAll();
            synchronized (connection){
                connection.notifyAll();
            }
        }
    }

    public synchronized void tryStart() {
        synchronized (currentStation.getQueueOfTrainsets()) {
            if (!currentStation.getQueueOfTrainsets().containsKey(targetStation)) {
                currentStation.getQueueOfTrainsets().put(targetStation, new LinkedList<>());
            }
            queue = currentStation.getQueueOfTrainsets().get(targetStation);
            currentStation.getQueueOfTrainsets().notifyAll();
        }
        synchronized (queue) {
            queue.add(this);
            while (queue.peek() != this && !Thread.currentThread().isInterrupted()) {
                try {
                    queue.wait(1);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    status = Status.NULL;
                }
            }
            connection = currentStation.getConnectionTo(targetStation);
            if (connection != null) synchronized (connection) {
                while (!connection.isAvailable()&& !Thread.currentThread().isInterrupted()) {
                    try {
                        connection.wait(1);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        status = Status.NULL;
                    }
                }
                connection.setAvailable(false);
                if (targetStation.getConnectionTo(currentStation) != null)
                    targetStation.getConnectionTo(currentStation).setAvailable(false);
            }
            status = Status.Running;
        }
    }

    public void changeVelocity() {
        double chVel;
        while (status == Status.Running) {
            try {
                checkVelocity();
            } catch (RailroadHazard e) {
                System.err.println(e.getMessage());
            }
            if (Math.random() < 0.5)
                chVel = (head.getVelocity() * 3) / 100;
            else chVel = -(head.getVelocity() * 3) / 100;
            head.setVelocity(head.getVelocity() + chVel);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                interrupt();
                status = Status.NULL;
            }
        }

    }

    public void move() {
        weightRoute = connection.getWeight();
        weightLeft = connection.getWeight();
        System.out.println(this + " is moving from " + currentStation + " to " + targetStation + " distance is " + weightLeft + " km");
        while (status == Status.Running && weightLeft > 0) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException a) {
                status = Status.NULL;
            }
            weightLeft -= 0.000277778 * head.getVelocity();
            travelledWeight += 0.000277778 * head.getVelocity();
        }

        if (targetStation == destStation) {
            status = Status.FinalArrived;
        } else status = Status.Arrived;
    }

    public synchronized void arrived() {
        try {
            System.out.println(this + " has reached " + targetStation);
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            status = Status.NULL;
        }
        connection.setAvailable(true);
        if (targetStation.getConnectionTo(currentStation) != null)
            targetStation.getConnectionTo(currentStation).setAvailable(true);

        synchronized (queue) {
            queue.poll();
            queue.notifyAll();
        }
        status = Status.TryStart;
    }

    public synchronized void finalArrived() {
        connection.setAvailable(true);
        if (targetStation.getConnectionTo(currentStation) != null)
            targetStation.getConnectionTo(currentStation).setAvailable(true);

        synchronized (queue) {
            queue.poll();
            queue.notifyAll();
        }
        try {
            System.out.println(this + " has reached the final station " + destStation);
            travelledWeight = 0;
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            interrupt();
            status = Status.NULL;
        }

        Station temp = sourceStation;
        sourceStation = destStation;
        destStation = temp;
        this.setPath(sourceStation, destStation);
        index = 0;
        status = Status.TryStart;
    }

    public synchronized void waitForSignal() {
        while (status == Status.Stopped) {
            try {
                wait();
            } catch (InterruptedException e) {
                interrupt();
                status = Status.NULL;
            }
        }
    }

    public synchronized void resumeSignal() {
        status = Status.TryStart;
        notifyAll();
    }

    @Override
    public String toString() {
        return name;
    }

    private void checkVelocity() throws RailroadHazard {
        if (this.head.getVelocity() >= 200)
            throw new RailroadHazard(this + " exceeds the speed of 200km/h!\n"
                    + this + " with ID:" + this.getUniqueNum() + ", current station: " + this.getCurrentStation()
                    + ", target station is: " + this.getTargetStation() + ", velocity: " + this.head.getVelocity());
    }

    public void showBasicInfo() {
        System.out.println(this + " with locomotive: " + head);
        head.showInfo();
        System.out.println("Attached cars: ");
        for (RailroadCar v : connectedCars
        ) {
            System.out.println(" " + v);
        }
    }

    public void showInfo() {
        System.out.println(this + " with locomotive: " + "\"" + head + "\"");
        System.out.println("Path: " + path);
        head.showInfo();
        for (RailroadCar v : connectedCars
        ) {
            v.showInfo();
        }

        System.out.println("% of the distance completed between the starting and destination railway stations: "
                + ((travelledWeight * 100) / wholeWeight)
                + "\n% of the distance completed between the nearest railway stations on your route: "
                + (100 - ((weightLeft * 100) / weightRoute))
        );
    }

    public List<Thread> getThreads() {
        return threads;
    }

}
