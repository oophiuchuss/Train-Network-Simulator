package RailroadCars;

public class HeavyRailroadFreightCar extends RailroadCar {
    private String name;

    private final String cargo;
    private final String materialOfCar;

    private final double capacity; /*in kilograms*/
    private final double weightOfCar; /*in kilograms*/

    public HeavyRailroadFreightCar(double initialWeight, double capacity, String cargo, String materialOfCar) {
        super.setUniqueNum(RailroadCar.getNumberOfCar() + 1);
        RailroadCar.increaseNumberOfCar();
        this.name = "Heavy railroad freight car №" + super.getUniqueNum();
        this.weightOfCar = initialWeight;
        this.setGrossWeight(weightOfCar);
        this.setMaxWeight(initialWeight + capacity);
        this.capacity = capacity;
        super.setElectricityNeeded(false);
        this.cargo = cargo;
        this.materialOfCar = materialOfCar;
    }

    @Override
    public String toString() {
        return name;
    }

    public void loadCargo(double loadedWeight) throws NotEnoughCapacity {
        if (getNetWeight() + loadedWeight <= capacity) {
            setNetWeight(getNetWeight() + loadedWeight);
            setGrossWeight(weightOfCar + getNetWeight());
        } else throw new NotEnoughCapacity("Too heavy");
    }

    public void unloadCargo(double unloadedWeight) throws NotEnoughCapacity {
        if (getNetWeight() - unloadedWeight >= 0) {
            setNetWeight(getNetWeight() - unloadedWeight);
            setGrossWeight(weightOfCar + getNetWeight());
            setTransportedCargo(getTransportedCargo() + unloadedWeight);
        } else throw new NotEnoughCapacity("There are not enough cargo");
    }

    public String info() {
        return this
                + "\n Net weight: " + getNetWeight() + " kg"
                + "\n Gross weight: " + getGrossWeight() + " kg"
                + "\n Maximum available weight: " + getMaxWeight() + " kg"
                + "\n Is electricity needed: " + isElectricityNeeded()
                + "\n Car weight: " + weightOfCar + " kg"
                + "\n Capacity for cargo: " + capacity + " kg"
                + "\n Type of cargo: " + cargo
                + "\n Transported amount of " + cargo + ": " + getTransportedCargo() + " kg";

    }

    public void showInfo() {
        System.out.println(this
                + "\n Net weight: " + getNetWeight() + " kg"
                + "\n Gross weight: " + getGrossWeight() + " kg"
                + "\n Maximum available weight: " + getMaxWeight() + " kg"
                + "\n Is electricity needed: " + isElectricityNeeded()
                + "\n Car weight: " + weightOfCar + " kg"
                + "\n Capacity for cargo: " + capacity + " kg"
                + "\n Type of cargo: " + cargo
                + "\n Transported amount of " + cargo + ": " + getTransportedCargo() + " kg"
        );
    }

    public String getCargo() {
        return cargo;
    }

    public double getCapacity() {
        return capacity;
    }

    public double getWeightOfCar() {
        return weightOfCar;
    }

    public boolean isEmpty() {
        return getNetWeight() == 0;
    }

    public boolean isFull() {
        return getNetWeight() == capacity;
    }

    public void setName(String name) {
        this.name = name;
    }
}
