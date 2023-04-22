package RailroadCars;

public class RailroadPostOffice extends RailroadCar {
    private final String name;
    private final String mailName;

    private double currentTemperature; /*in Celsius*/
    private final double capacityOfMail; /*in kilograms*/
    private final double weightOfCar;/*in kilograms*/

    public RailroadPostOffice(double initialWeight, double capacityOfMail, String mailName) {
        super.setUniqueNum(RailroadCar.getNumberOfCar() + 1);
        RailroadCar.increaseNumberOfCar();
        this.name = "Railroad post office №" + super.getUniqueNum();
        this.weightOfCar = initialWeight;
        this.setGrossWeight(weightOfCar);
        this.setMaxWeight(initialWeight + capacityOfMail);
        this.capacityOfMail = capacityOfMail;
        super.setElectricityNeeded(true);
        this.mailName = mailName;
        currentTemperature = 20;
    }

    public double getCurrentTemperature() {
        return currentTemperature;
    }

    public void setCurrentTemperature(double currentTemperature) {
        this.currentTemperature = currentTemperature;
    }

    @Override
    public String toString() {
        return name;
    }

    //Spontaneously changing temperature while unloading packages and letters
    public void changeTemperature() {
        if (Math.random() > 0.5) currentTemperature += Math.random() * 5;
        else currentTemperature -= Math.random() * 5;
    }

    //checks the comfortable temperature for cargo and people while loading packages and letters
    public void checkTemperature() {
        if (currentTemperature < 15) currentTemperature += 10;
        else if (currentTemperature > 30) currentTemperature -= 10;
    }

    public void loadCargo(double loadedWeight) throws NotEnoughCapacity {
        if (getNetWeight() + loadedWeight <= capacityOfMail) {
            setNetWeight(getNetWeight() + loadedWeight);
            setGrossWeight(weightOfCar + getNetWeight());
            checkTemperature();
        } else throw new NotEnoughCapacity("Too heavy");
    }

    public void unloadCargo(double unloadedWeight) throws NotEnoughCapacity {
        if (getNetWeight() - unloadedWeight >= 0) {
            setNetWeight(getNetWeight() - unloadedWeight);
            setGrossWeight(weightOfCar + getNetWeight());
            setTransportedCargo(getTransportedCargo() + unloadedWeight);
            changeTemperature();
        } else throw new NotEnoughCapacity("There are not enough mail");
    }

    public String info() {
        return this
                + "\n Net weight: " + getNetWeight() + " kg"
                + "\n Gross weight: " + getGrossWeight() + " kg"
                + "\n Maximum available weight: " + getMaxWeight() + " kg"
                + "\n Is electricity needed: " + isElectricityNeeded()
                + "\n Car weight: : " + weightOfCar + " kg"
                + "\n Capacity for cargo: " + capacityOfMail + " kg"
                + "\n Name of mail company: " + mailName
                + "\n Transported amount of mail packages and letters: " + getTransportedCargo() + " kg";
    }

    public void showInfo() {
        System.out.println(this
                + "\n Net weight: " + getNetWeight() + " kg"
                + "\n Gross weight: " + getGrossWeight() + " kg"
                + "\n Maximum available weight: " + getMaxWeight() + " kg"
                + "\n Is electricity needed: " + isElectricityNeeded()
                + "\n Car weight: : " + weightOfCar + " kg"
                + "\n Capacity for cargo: " + capacityOfMail + " kg"
                + "\n Name of mail company: " + mailName
                + "\n Transported amount of mail packages and letters: " + getTransportedCargo() + " kg"
        );
    }
}
