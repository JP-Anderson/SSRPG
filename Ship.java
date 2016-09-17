import java.util.ArrayList;

public class Ship {

    public final String name;
    private int fuelCapacity;
    private int remainingFuel;
    private int crewCapacity;
    private Scanner scanner;
    private ArrayList<Crewmember> crew;
    private GridPoint location;

    // todo: create getters/setters for this
    private EngineModule engines = new EngineModule("Engines MK1",5);

    public Ship(String pName, int fuel, int crew, GridMap map, GridPoint startLocation) {
        name = pName;
        fuelCapacity = fuel;
        remainingFuel = fuel;
        crewCapacity = crew;
        location = startLocation;
        scanner = Scanner.getScanner(7,map,startLocation);
    }

    public GridPoint getLocation() {
        return location;
    }

    public void setLocation(GridPoint gridPoint) {
        location = gridPoint;
        scanner.setShipLocation(gridPoint);
    }

    public void shipStatus() {
        System.out.println("Ship status:");
        System.out.println("Remaining Fuel: " + remainingFuel + "/" + fuelCapacity);
        engines.printInformation();
    }

    public boolean travel(GridPoint gridPoint, int distance) {
        location = gridPoint;
        scanner.setShipLocation(gridPoint);
        int fuelCost = distance * engines.fuelEfficiency;
        if (fuelCost <= remainingFuel) {
            System.out.println("Used " + fuelCost + " fuel.");
            remainingFuel = remainingFuel - fuelCost;
            return true;
        }
        return false;
    }

    public void scan() {
        scanner.scan();
    }

}
