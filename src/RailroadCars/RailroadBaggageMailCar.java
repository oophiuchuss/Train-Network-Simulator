package RailroadCars;

import java.util.List;

public class RailroadBaggageMailCar extends RailroadCar {
    private final String name;
    private final String mailName;
    private double lostWeight = 0; /*in kilograms*/
    private final double capacity; /*in kilograms*/
    private final double weightOfCar; /*in kilograms*/

    public RailroadBaggageMailCar(double initialWeight, double capacity, String mailName) {
        super.setUniqueNum(RailroadCar.getNumberOfCar() + 1);
        RailroadCar.increaseNumberOfCar();
        this.name = "Railroad baggage and mail car  №" + super.getUniqueNum();
        this.weightOfCar = initialWeight;
        super.setGrossWeight(weightOfCar);
        this.setMaxWeight(initialWeight + capacity);
        this.capacity = capacity;
        super.setElectricityNeeded(false);
        this.mailName = mailName;
    }

    @Override
    public String toString() {
        return name;
    }

    public void loseBaggage(double initialWeight) {
        if (Math.random() < 0.01) {
            double toLose = 2 + (Math.random() * 3);
            lostWeight += toLose;
            setNetWeight(getNetWeight() + initialWeight - toLose);
            setGrossWeight(weightOfCar + getNetWeight());
        }
    }

    public static double avgLost(List<? extends Number> listOfLostWeight) {
        double sum = 0;
        for (Number v : listOfLostWeight
        ) {
            sum += Double.parseDouble(v.toString());
        }
        return sum / listOfLostWeight.size();
    }

    public void loadCargo(double loadedWeight) throws NotEnoughCapacity {
        if (getNetWeight() + loadedWeight <= capacity) {
            setNetWeight(getNetWeight() + loadedWeight);
            setGrossWeight(weightOfCar + getNetWeight());
            loseBaggage(loadedWeight);
        } else throw new NotEnoughCapacity("Too heavy");
    }

    public void unloadCargo(double unloadedWeight) throws NotEnoughCapacity {
        if (getNetWeight() - unloadedWeight >= 0) {
            setNetWeight(getNetWeight() - unloadedWeight);
            setGrossWeight(weightOfCar + getNetWeight());
            setTransportedCargo(getTransportedCargo() + unloadedWeight);
        } else throw new NotEnoughCapacity("There are not enough baggage or mail");
    }

    public String info() {
        return this
                + "\n Net weight: " + getNetWeight() + " kg"
                + "\n Gross weight: " + getGrossWeight() + " kg"
                + "\n Maximum available weight: " + getMaxWeight() + " kg"
                + "\n Is electricity needed: " + isElectricityNeeded()
                + "\n Car weight: " + weightOfCar + " kg"
                + "\n Capacity for cargo: " + capacity + " kg"
                + "\n Name of mail company: " + mailName + " kg"
                + "\n Transported amount of mail and baggage: " + getTransportedCargo() + " kg";
    }

    public void showInfo() {
        System.out.println(this
                + "\n Net weight: " + getNetWeight() + " kg"
                + "\n Gross weight: " + getGrossWeight() + " kg"
                + "\n Maximum available weight: " + getMaxWeight() + " kg"
                + "\n Is electricity needed: " + isElectricityNeeded()
                + "\n Car weight: " + weightOfCar + " kg"
                + "\n Capacity for cargo: " + capacity + " kg"
                + "\n Name of mail company: " + mailName + " kg"
                + "\n Transported amount of mail and baggage: " + getTransportedCargo() + " kg"
        );
    }
}
