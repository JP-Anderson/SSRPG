package ship;

import characters.Crewmember;
import java.util.ArrayList;
import ship.modules.*;
import ship.weapons.ShipWeapon;
import ship.weapons.Attack;

public abstract class AbstractShip {

    public final String name;

    protected int maxHullIntegrity;
    protected int remainingHullIntegrity;
    protected int maxShields;
    protected int remainingShields;

    protected ArrayList<Crewmember> crew;
    protected int crewCapacity;

    protected EngineModule engineModule;

    // Will need to set this based on type of Ship, must also be upgradeable
    protected int numberOfAvailableWeaponModules = 2;
    protected ArrayList<WeaponModule> weaponModules;

    protected CargoBay cargo;


    public AbstractShip(String pName) {
        name = pName;
        cargo = new CargoBay(15);
        weaponModules = new ArrayList<>();
    }

    public void addWeaponModule(int weaponModulePower) {
        if (weaponModules.size() < numberOfAvailableWeaponModules) {
            weaponModules.add(new WeaponModule(weaponModulePower));
        }
    }

    public ArrayList<WeaponModule> getWeaponModules() {
        return weaponModules;
    }

    public boolean equipWeapon(ShipWeapon newWeapon) {
        for (WeaponModule m : weaponModules) {
            if (m.maxWeaponPowerSupported <= newWeapon.requiredWeaponModulePower
            && m.getWeapon() == null) {
                System.out.println("This weapon has been equipped.");
                m.setWeapon(newWeapon);
                return true;
            }
        }

        System.out.println("Cannot equip weapon.");
        return false;
    }

    // TODO: create an AttackAttempt class and provide a WeaponModule param
    public Attack fireWeapon(int weaponModulesIndex) {
        WeaponModule weaponModule = weaponModules.get(weaponModulesIndex);
        if (weaponModule != null) {
            return weaponModule.attack();
        }
        return null;
    }

    public CargoBay getCargoBay() {
        return cargo;
    }

}
