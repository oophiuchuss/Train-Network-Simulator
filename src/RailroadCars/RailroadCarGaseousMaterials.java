package RailroadCars;

public class RailroadCarGaseousMaterials extends BasicRailroadFreightCar {
    private final double maxPressure = 3450; /*In Kilopascals*/
    private double currentPressure = 0;

    public RailroadCarGaseousMaterials(double initialWeight, double capacity, String cargo, String company) {
        super(initialWeight, capacity, cargo, company);
        super.setName("Railroad car for gaseous materials №" + super.getUniqueNum());
    }

    public double getCurrentPressure() {
        return currentPressure;
    }

    public void increasePressure(double toAdd) {
        if (currentPressure + toAdd <= maxPressure) currentPressure += toAdd;
    }

    public void decreasePressure(double toRemove) {
        if (currentPressure - toRemove >= 0) currentPressure -= toRemove;
    }

    public String info() {
        return this
                + "\n Net weight: " + getNetWeight() + " kg"
                + "\n Gross weight: " + getGrossWeight() + " kg"
                + "\n Maximum available weight: " + getMaxWeight() + " kg"
                + "\n Is electricity needed: " + isElectricityNeeded()
                + "\n Car weight: " + getWeightOfCar() + " kg"
                + "\n Capacity for cargo: " + getCapacity() + " kg"
                + "\n Type of cargo: " + getCargo()
                + "\n Transported amount of " + getCargo() + ": " + getTransportedCargo() + " kg";
    }

    public void showInfo() {
        System.out.println(this
                + "\n Net weight: " + getNetWeight() + " kg"
                + "\n Gross weight: " + getGrossWeight() + " kg"
                + "\n Maximum available weight: " + getMaxWeight() + " kg"
                + "\n Is electricity needed: " + isElectricityNeeded()
                + "\n Car weight: " + getWeightOfCar() + " kg"
                + "\n Capacity for cargo: " + getCapacity() + " kg"
                + "\n Type of cargo: " + getCargo()
                + "\n Transported amount of " + getCargo() + ": " + getTransportedCargo() + " kg"
        );
    }
}
