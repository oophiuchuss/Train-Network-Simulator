package RailroadCars;

public class RailroadCarExplosives extends HeavyRailroadFreightCar {
    public enum ExplosiveClass {A, B, C, D}
    private final ExplosiveClass explosiveClass;
    private boolean isSafe;

    public RailroadCarExplosives(double initialWeight, double capacity, String cargo, String materialOfCar, ExplosiveClass explosiveClass) {
        super(initialWeight, capacity, cargo, materialOfCar);
        super.setName("Railroad car for explosives №" + super.getUniqueNum());
        this.explosiveClass = explosiveClass;
        isSafe = true;
    }

    public boolean isSafe() {
        return isSafe;
    }

    public void armExplosive() {
        if (!super.isEmpty() && isSafe) isSafe = false;
    }

    public void defuseExplosive() {
        if (!isSafe && !super.isEmpty()) isSafe = true;
    }

    @Override
    public void loadCargo(double loadedWeight) throws NotEnoughCapacity {
        defuseExplosive();
        super.loadCargo(loadedWeight);
    }

    @Override
    public void unloadCargo(double unloadedWeight) throws NotEnoughCapacity {
        defuseExplosive();
        super.unloadCargo(unloadedWeight);
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
                + "\n Explosive class: " + explosiveClass
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
                + "\n Explosive class: " + explosiveClass
                + "\n Transported amount of " + getCargo() + ": " + getTransportedCargo() + " kg"
        );
    }
}
