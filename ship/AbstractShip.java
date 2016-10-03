package ship;

import characters.Crewmember;
import java.util.ArrayList;

public class AbstractShip {

    public final String name;

    private int maxHullIntegrity;
    private int remainingHullIntegrity;
    private int maxShields;
    private int remainingShields;

    ArrayList<Crewmember> crew;

    public AbstractShip(String pName) {
        name = pName;
    }
}
