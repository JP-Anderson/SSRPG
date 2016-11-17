package ship;

import characters.Crewmember;
import java.util.ArrayList;
import ship.modules.*;

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

    protected CargoBay cargo;


    public AbstractShip(String pName) {
        name = pName;
        cargo = new CargoBay(15);
    }

    // TODO: create an AttackAttempt class and provide a WeaponModule param
    public void fireWeapon(int weaponModulesIndex) {

    }

    public CargoBay getCargoBay() {
        return cargo;
    }

}
