package RailroadCars;

public interface InterfaceForLiquidMaterials {
    double maxPressure = 690; /*in Kilopascals*/
    boolean isSafe = true;
    boolean tankState();
    boolean checkIsClean();
}
