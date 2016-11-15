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

    protected EngineModule engineModule;

    protected int numberOfAvailableWeaponModules;
    protected ArrayList<WeaponModule> weaponModules;


    public AbstractShip(String pName) {
        name = pName;
    }

    // TODO: create an AttackAttempt class and provide a WeaponModule param
    public void fireWeapon(int weaponModulesIndex) {

    }
}
