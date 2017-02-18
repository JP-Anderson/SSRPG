package ship;

import map.GridMap;
import map.GridPoint;
import characters.Crewmember;
import ship.modules.CargoBayModule;

import java.util.ArrayList;

public class PlayerShip extends Ship {

    private int fuelCapacity;
    private int remainingFuel;
    private Scanner scanner;
    private ArrayList<Crewmember> crew;
    private GridPoint location;

    private int money;

    public void setMoney (int m) {
        money = m;
    }
    public int getMoney() {
        return money;
    }

    public PlayerShip(String pName, ShipModules modules, int fuel, int crewCap) {
        super(pName, modules);
        fuelCapacity = fuel;
        remainingFuel = fuel;
        crewCapacity = crewCap;
        crew = new ArrayList<>();
    }

    public void initialiseCrew(ArrayList<Crewmember> newCrew) {
        crew = newCrew;
    }

    public void initialiseMap(GridPoint startLocation, GridMap map) {
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
        CargoBayModule cargo = modules.getCargoBayModule();
        System.out.println("PlayerShip status:");
        System.out.println(crew.size() + "/" + crewCapacity + " crew");
        System.out.println(" CREDS total: " + money);
        System.out.println(" Remaining Fuel: " + remainingFuel + "/" + fuelCapacity);
        // TODO: cargo bay is now a module, so need to put this in the module print information function
        System.out.println(" Cargo Bay: " + cargo.getFilledCapacity() + " units out of " + cargo.getMaxCapacity());
        System.out.println(" Modules: ");
        modules.getEngineModule().printInformation();
        modules.getShieldModule().printInformation();
    }

    public boolean travel(GridPoint gridPoint, int distance) {
        location = gridPoint;
        scanner.setShipLocation(gridPoint);
        int fuelCost = distance * modules.getEngineModule().fuelEfficiency;
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

    public ArrayList<Crewmember> getCrew() {
        return crew;
    }

    public int getFuelCapacity() { return fuelCapacity; }

    public int getRemainingFuel() { return remainingFuel; }

    public void setRemainingFuel(int newFuel) { remainingFuel = newFuel; }

    public void setCrew(ArrayList<Crewmember> newCrew) {
        crew = newCrew;
    }

}
