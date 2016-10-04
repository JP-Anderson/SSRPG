package ship;

import characters.Crewmember;
import java.util.ArrayList;

public abstract class AbstractShip {

    public final String name;

    protected int maxHullIntegrity;
    protected int remainingHullIntegrity;
    protected int maxShields;
    protected int remainingShields;

    protected ArrayList<Crewmember> crew;

    protected int crewCapacity;

    public AbstractShip(String pName) {
        name = pName;
    }
}
