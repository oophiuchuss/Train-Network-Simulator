public class Locomotive {
    private String name;
    private final int uniqueNum;
    private static int numOfLocomotives = 0;
    private final int maxCars;
    private final double maxWeight; /*in kilograms*/
    private final int maxElectricity;
    private int usedElectricity = 0;
    private double velocity; /*in kilometres per hour*/

    public Locomotive(int maxCars, double maxWeight, int maxElectricity, double velocity) {
        this.maxCars = maxCars;
        this.maxWeight = maxWeight;
        this.maxElectricity = maxElectricity;
        this.uniqueNum = numOfLocomotives + 1;
        numOfLocomotives++;
        this.name = "Locomotive №" + uniqueNum;
        this.velocity = velocity;
    }

    public Locomotive(int maxCars, double maxWeight, int maxElectricity, double velocity, String name) {
        this.maxCars = maxCars;
        this.maxWeight = maxWeight;
        this.maxElectricity = maxElectricity;
        this.uniqueNum = numOfLocomotives + 1;
        numOfLocomotives++;
        this.name = name;
        this.velocity = velocity;
    }

    public void setUsedElectricity(int usedElectricity) {
        this.usedElectricity = usedElectricity;
    }

    public void setVelocity(double velocity) {
        this.velocity = velocity;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMaxCars() {
        return maxCars;
    }

    public double getMaxWeight() {
        return maxWeight;
    }

    public int getMaxElectricity() {
        return maxElectricity;
    }

    public int getUsedElectricity() {
        return usedElectricity;
    }

    public String getName() {
        return name;
    }

    public int getUniqueNum() {
        return uniqueNum;
    }

    public double getVelocity() {
        return velocity;
    }

    @Override
    public String toString() {
        return name;
    }

    public void showInfo() {
        System.out.println(this
                + "\nID: " + uniqueNum
                + ": \nMaximum of cars: " + maxCars
                + "\nMaximum weight: " + maxWeight + " kg"
                + "\nMaximum cars that require electricity: " + maxElectricity
                + "\nVelocity: " + velocity + "km/h"
        );
    }
}
