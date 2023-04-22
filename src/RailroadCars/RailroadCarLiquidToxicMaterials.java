package RailroadCars;

import java.util.List;

public class RailroadCarLiquidToxicMaterials extends RailroadCarToxicMaterials implements InterfaceForLiquidMaterials {
    private boolean isCleaned = true;
    private int numOfUnload = 0;
    private final int containmentLevel; /*from 1 to 4*/
    private boolean isHazardous;

    public RailroadCarLiquidToxicMaterials(double initialWeight, double capacity, String cargo, String materialOfCar, int containmentLevel, int maximumConcentrationPercentage, boolean ventilation) {
        super(initialWeight, capacity, cargo, materialOfCar, maximumConcentrationPercentage, ventilation );
        super.setName("Railroad car for liquid, toxic material №" + super.getUniqueNum());
        this.containmentLevel = containmentLevel;
        indicateHazardous();
    }

    public boolean indicateHazardous() {
        if (containmentLevel > 2) isHazardous = false;
        else isHazardous = true;
        return isHazardous;
    }

    public static int numOfHazardousCars(List<RailroadCarLiquidToxicMaterials> carList){
        int result = 0;
        for (RailroadCarLiquidToxicMaterials v: carList
             ) {
            if(v.indicateHazardous()) result++;
        }
    return result;
    }

    @Override
    public boolean checkIsClean() {
        return numOfUnload > 3;
    }

    @Override
    public boolean tankState() {
        if (checkIsClean()) isCleaned = false;
        return isCleaned;
    }

    @Override
    public void unloadCargo(double unloadedWeight) throws NotEnoughCapacity {
        super.unloadCargo(unloadedWeight);
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
                + "\n Containment Level : " + containmentLevel
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
                + "\n Containment Level : " + containmentLevel
                + "\n Transported amount of " + getCargo() + ": " + getTransportedCargo() + " kg");
    }
}

