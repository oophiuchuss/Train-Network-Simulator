package RailroadCars;

import java.util.List;

public class PassengerRailroadCar extends RailroadCar {
    public enum ClassType {VIP, First, Second}
    private final String name;
    private final ClassType categoryOfClass;
    private boolean isMealIncluded; /* Field to show is for this car all passengers could be served*/
    private final int numOfSeats;
    private final double weightOfCar;
    private int amountOfPas = 0;

    public PassengerRailroadCar(double initialWeight, ClassType categoryOfClass, int numOfSeats) {
        super.setUniqueNum(RailroadCar.getNumberOfCar()+1);
        RailroadCar.increaseNumberOfCar();
        this.name = "Passenger Railroad Car №" + super.getUniqueNum();
        this.numOfSeats = numOfSeats;
        this.setMaxWeight(numOfSeats * (88.45) + initialWeight);
        this.categoryOfClass = categoryOfClass;
        this.weightOfCar = initialWeight;
        super.setGrossWeight(weightOfCar);
        super.setElectricityNeeded(true);
        checkMeal();
    }

    @Override
    public String toString() {
        return name;
    }

    public int getAmountOfPas() {
        return amountOfPas;
    }

    public static int allPassengers(List<PassengerRailroadCar> carList) {
        int sum = 0;
        for (PassengerRailroadCar v : carList
        ) {
            sum += v.getAmountOfPas();
        }
        return sum;
    }

    public boolean checkMeal() {
        switch (categoryOfClass) {
            case VIP -> isMealIncluded = true;
            case First -> isMealIncluded = true;
            case Second -> isMealIncluded = false;
        }
        return isMealIncluded;
    }

    public void loadCargo(double amountD) throws NotEnoughCapacity {
        int amount = (int) amountD;
        if (this.amountOfPas + amount <= numOfSeats) {
            this.amountOfPas += amount;
            setNetWeight(getNetWeight() + amount * (88.45));
            setGrossWeight(weightOfCar + getNetWeight());
        } else throw new NotEnoughCapacity("Not enough seats");
    }

    public void unloadCargo(double amountD) throws NotEnoughCapacity {
        int amount = (int) amountD;
        if (this.amountOfPas - amount >= 0) {
            this.amountOfPas -= amount;
            setNetWeight(getNetWeight() - amount * (88.45));
            setGrossWeight(weightOfCar + getNetWeight());
            setTransportedCargo(getTransportedCargo() + amount);
        } else throw new NotEnoughCapacity("There are not enough passengers");
    }

    public String info() {
        return this
                + "\n Net weight: " + getNetWeight() + " kg"
                + "\n Gross weight: " + getGrossWeight() + " kg"
                + "\n Number of passengers: " + amountOfPas
                + "\n Maximum available weight: " + getMaxWeight() + " kg"
                + "\n Is electricity needed: " + isElectricityNeeded()
                + "\n Car weight: " + weightOfCar + " kg"
                + "\n Number of seats " + numOfSeats + " kg"
                + "\n Class of car: " + categoryOfClass + " kg"
                + "\n Transported amount of people: " + (int) getTransportedCargo();
    }

    public void showInfo() {
        System.out.println(this
                + "\n Net weight: " + getNetWeight() + " kg"
                + "\n Gross weight: " + getGrossWeight() + " kg"
                + "\n Number of passengers: " + amountOfPas
                + "\n Maximum available weight: " + getMaxWeight() + " kg"
                + "\n Is electricity needed: " + isElectricityNeeded()
                + "\n Car weight: " + weightOfCar + " kg"
                + "\n Number of seats " + numOfSeats + " kg"
                + "\n Class of car: " + categoryOfClass + " kg"
                + "\n Transported amount of people: " + (int) getTransportedCargo()
        );
    }
}
