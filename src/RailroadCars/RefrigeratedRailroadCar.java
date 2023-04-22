package RailroadCars;

public class RefrigeratedRailroadCar extends BasicRailroadFreightCar {
    private boolean isCooling = false;
    private double temperature = -10 + Math.random()*20; /*in Celsius*/
    public RefrigeratedRailroadCar(double initialWeight, double capacity, String cargo, String company) {
        super(initialWeight, capacity, cargo, company);
        super.setName("Refrigerated railroad car №" + super.getUniqueNum());
        super.setElectricityNeeded(true);
    }

    public double getTemperature() {
        return temperature;
    }

    public boolean isCooling() {
        return isCooling;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public void setCooling(boolean cooling) {
        isCooling = cooling;
    }


    public void checkTemp(){
        if(!isCooling && temperature > 0) {
            isCooling = true;
            temperature-=10;
        }
    }

    //temperature is changing when cargo is loading or unloading and the car is not cooling
    public void changeTemp(){
        if(!isCooling)temperature+= Math.random()*6;
    }

    @Override
    public void unloadCargo(double unloadedWeight) throws NotEnoughCapacity {
        super.unloadCargo(unloadedWeight);
        changeTemp();
        checkTemp();
    }

    @Override
    public void loadCargo(double loadedWeight) throws NotEnoughCapacity {
        super.loadCargo(loadedWeight);
        changeTemp();
        checkTemp();
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
