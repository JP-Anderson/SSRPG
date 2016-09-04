import java.util.ArrayList;

public class Ship {

  public final String name;
  private int fuelCapacity;
  private int remainingFuel;
  private int crewCapacity;
  private ArrayList<Crewmember> crew;

  public Ship(String pName, int fuel, int crew) {
    name = pName;
    fuelCapacity = fuel;
    crewCapacity = crew;
  }


}
