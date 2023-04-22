package RailroadCars;

import java.util.List;

public class RailroadCarToxicMaterials extends HeavyRailroadFreightCar {
    private int maximumConcentrationPercentage; /*in percents*/
    private boolean ventilation;

    public RailroadCarToxicMaterials(double initialWeight, double capacity, String cargo, String materialOfCar, int maximumConcentrationPercentage, boolean ventilation) {
        super(initialWeight, capacity, cargo, materialOfCar);
        super.setName("Railroad car for toxic materials №" + super.getUniqueNum());
        this.maximumConcentrationPercentage = maximumConcentrationPercentage;
        this.ventilation = ventilation;
    }

    public int getMaximumConcentrationPercentage() {
        return maximumConcentrationPercentage;
    }

    public void setMaximumConcentrationPercentage(int maximumConcentrationPercentage) {
        this.maximumConcentrationPercentage = maximumConcentrationPercentage;
    }

    public void makeSafe() {
        if (!ventilation && maximumConcentrationPercentage > 60)
            maximumConcentrationPercentage -= 10 + (int) (Math.random() * 30);
    }

    public static boolean adjustConcentration(List<RailroadCarToxicMaterials> carList) {
        int sum = 0;
        boolean wasMadeSafe = false;
        for (RailroadCarToxicMaterials v : carList
        ) {
            sum += v.maximumConcentrationPercentage;
        }
        if (sum / carList.size() > 60) {
            wasMadeSafe = true;
            for (RailroadCarToxicMaterials v : carList
            ) {
                v.makeSafe();
            }
        }
        return wasMadeSafe;
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
