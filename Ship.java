import java.util.ArrayList;

public class Ship {

    public final String name;
    private int fuelCapacity;
    private int remainingFuel;
    private int crewCapacity;
    private Scanner scanner;
    private ArrayList<Crewmember> crew;
    private GridPoint location;

    public Ship(String pName, int fuel, int crew, GridMap map, GridPoint startLocation) {
        name = pName;
        fuelCapacity = fuel;
        crewCapacity = crew;
        location = startLocation;
        scanner = Scanner.getScanner(11,map,startLocation);
    }

    public GridPoint getLocation() {
        return location;
    }

    public void setLocation(GridPoint gridPoint) {
        location = gridPoint;
        scanner.setShipLocation(gridPoint);
    }

    public void scan() {
        scanner.scan();
    }

}
