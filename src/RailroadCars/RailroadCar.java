package RailroadCars;

import java.util.List;

public abstract class RailroadCar {
    private static int numberOfCar = 0;
    private int uniqueNum;
    private double netWeight = 0; /*in kilograms*/
    private double grossWeight = 0; /*in kilograms*/
    private double maxWeight; /*in kilograms*/
    private double transportedCargo = 0; /*in kilograms*/
    private boolean isElectricityNeeded;
    private double length; /*in metres*/
    private int numberOfAxles;

    public void setNetWeight(double netWeight) {
        this.netWeight = netWeight;
    }

    public void setLength(double length) {
        this.length = length;
    }

    public void setNumberOfAxles(int numberOfAxles) {
        this.numberOfAxles = numberOfAxles;
    }

    public void setTransportedCargo(double transportedCargo) {
        this.transportedCargo = transportedCargo;
    }

    public void setMaxWeight(double maxWeight) {
        this.maxWeight = maxWeight;
    }

    public void setGrossWeight(double grossWeight) {
        this.grossWeight = grossWeight;
    }

    public void setElectricityNeeded(boolean electricityNeeded) {
        isElectricityNeeded = electricityNeeded;
    }

    public void setUniqueNum(int uniqueNum) {
        this.uniqueNum = uniqueNum;
    }

    public double getNetWeight() {
        return netWeight;
    }

    public double getGrossWeight() {
        return grossWeight;
    }

    public double getMaxWeight() {
        return maxWeight;
    }

    public double getTransportedCargo() {
        return transportedCargo;
    }

    public boolean isElectricityNeeded() {
        return isElectricityNeeded;
    }

    public static int getNumberOfCar() {
        return numberOfCar;
    }

    public int getUniqueNum() {
        return uniqueNum;
    }

    public static void increaseNumberOfCar() {
        numberOfCar++;
    }

    public void loadCargo(double loadedWeight) throws NotEnoughCapacity {
    }

    public void unloadCargo(double unloadedWeight) throws NotEnoughCapacity {
    }

    public static RailroadCar getCarByName(List<RailroadCar> carList, String name) {
        for (RailroadCar c : carList
        ) {
            if (c.toString().equals(name)) return c;
        }
        return null;
    }

    public static int neededElectricity(List<RailroadCar> carList) {
        int result = 0;
        for (RailroadCar v : carList
        ) {
            if (v.isElectricityNeeded) result++;
        }
        return result;
    }

    public String info() {
        return this
                + "\n Net weight: " + netWeight + " kg"
                + "\n Gross weight: " + grossWeight + " kg"
                + "\n Maximum available weight: " + maxWeight + " kg"
                + "\n Is electricity needed: " + isElectricityNeeded + " kg"
                + "\n Transported amount of goods: " + getTransportedCargo() + " kg";
    }

    public void showInfo() {
        System.out.println(this
                + "\n Net weight: " + netWeight + " kg"
                + "\n Gross weight: " + grossWeight + " kg"
                + "\n Maximum available weight: " + maxWeight + " kg"
                + "\n Is electricity needed: " + isElectricityNeeded + " kg"
                + "\n Transported amount of goods: " + getTransportedCargo() + " kg"
        );
    }
}
