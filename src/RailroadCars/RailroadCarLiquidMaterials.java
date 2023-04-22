package RailroadCars;

import java.util.List;

public class RailroadCarLiquidMaterials extends BasicRailroadFreightCar implements InterfaceForLiquidMaterials {
    private boolean isCleaned = true;
    private int numOfUnload = 0; /*how many times was car unloaded*/

    public RailroadCarLiquidMaterials(double initialWeight, double capacity, String cargo, String company) {
        super(initialWeight, capacity, cargo, company);
        super.setName("Railroad car for liquid materials №" + super.getUniqueNum());
    }

    public boolean isCleaned() {
        return isCleaned;
    }

    public void tankCleaning() {
        isCleaned = true;
        numOfUnload = 0;
    }

    public static <T extends BasicRailroadFreightCar> T getBasicCarAt(List<T> carList, int index) {
        if (carList == null || index < 0 || index >= carList.size()) {
            return null;
        }
        return carList.get(index);
    }

    @Override
    public boolean checkIsClean() {
        return numOfUnload < 3;
    }

    @Override
    public boolean tankState() {
        if (checkIsClean()) isCleaned = false;
        return isCleaned;
    }

    @Override
    public void unloadCargo(double unloadedWeight) throws NotEnoughCapacity {
        super.unloadCargo(unloadedWeight);
        if(checkIsClean()) isCleaned =true;
        else isCleaned = false;
        numOfUnload++;
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
