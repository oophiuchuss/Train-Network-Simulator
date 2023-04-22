package RailroadCars;

public class RailroadRestaurantCar extends RailroadCar {
    private final String name;
    private final String restName;
    private final double numOfSeats;
    private final double maxWeightForGoods; /*in kilograms*/
    private final double weightOfCar; /*in kilograms*/

    public RailroadRestaurantCar(double initialWeight, int numOfSeats, double maxWeightForGoods, String restName) {
        super.setUniqueNum(RailroadCar.getNumberOfCar() + 1);
        RailroadCar.increaseNumberOfCar();
        this.name = "Railroad restaurant car №" + super.getUniqueNum();
        this.weightOfCar = initialWeight;
        this.setGrossWeight(weightOfCar);
        this.setMaxWeight(initialWeight + maxWeightForGoods);
        this.maxWeightForGoods = maxWeightForGoods;
        super.setElectricityNeeded(true);
        this.restName = restName;
        this.numOfSeats = numOfSeats;
    }

    //calculate amount of food for maximum people that are seating in restaurant
    public double neededFoodForMaxPeople() {
        return numOfSeats * (0.5 + Math.random() * 0.7);
    }

    //assume for one pearson should be spent around 1 kg of goods
    public void serveMeal(int amountOfPeople) {
        double neededFood = amountOfPeople * (0.5 + Math.random() * 0.7);
        if (getNetWeight() - neededFood >= 0) {
            setNetWeight(getNetWeight() - neededFood);
            setGrossWeight(weightOfCar + getNetWeight());
        }
    }

    @Override
    public String toString() {
        return name;
    }

    public void loadCargo(double loadedWeight) throws NotEnoughCapacity {
        if (getNetWeight() + loadedWeight <= maxWeightForGoods) {
            setNetWeight(getNetWeight() + loadedWeight);
            setGrossWeight(weightOfCar + getNetWeight());
        } else throw new NotEnoughCapacity("Too heavy");
    }

    public void unloadCargo(double unloadedWeight) throws NotEnoughCapacity {
        if (getNetWeight() - unloadedWeight >= 0) {
            setNetWeight(getNetWeight() - unloadedWeight);
            setGrossWeight(weightOfCar + getNetWeight());
            setTransportedCargo(getTransportedCargo() + unloadedWeight);
        } else throw new NotEnoughCapacity("There are not enough goods");
    }

    public String info() {
        return this
                + "\n Net weight: " + getNetWeight() + " kg"
                + "\n Gross weight: " + getGrossWeight() + " kg"
                + "\n Maximum available weight: " + getMaxWeight() + " kg"
                + "\n Is electricity needed: " + isElectricityNeeded()
                + "\n Car weight: " + weightOfCar + " kg"
                + "\n Maximum weight of goods for service: " + maxWeightForGoods + " kg"
                + "\n Name of restaurant: " + restName
                + "\n Transported amount of goods: " + getTransportedCargo() + " kg";
    }

    public void showInfo() {
        System.out.println(this
                + "\n Net weight: " + getNetWeight() + " kg"
                + "\n Gross weight: " + getGrossWeight() + " kg"
                + "\n Maximum available weight: " + getMaxWeight() + " kg"
                + "\n Is electricity needed: " + isElectricityNeeded()
                + "\n Car weight: " + weightOfCar + " kg"
                + "\n Maximum weight of goods for service: " + maxWeightForGoods + " kg"
                + "\n Name of restaurant: " + restName
                + "\n Transported amount of goods: " + getTransportedCargo() + " kg"
        );
    }
}