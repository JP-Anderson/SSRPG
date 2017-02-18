package ship;

import characters.Crewmember;
import java.util.ArrayList;
import ship.modules.*;
import ship.weapons.ShipWeapon;
import ship.weapons.Attack;
import ship.shields.*;

public abstract class Ship {

    public final String name;

    int maxHullIntegrity;
    int remainingHullIntegrity;

    protected ArrayList<Crewmember> crew;
    int crewCapacity;

    // todo: create getters/setters for this
    // todo: don't use hardcoded modules
    EngineModule engines = new EngineModule("Engines1", 5);
    ShieldModule shieldModule = new ShieldModule("Shields1", 2);

    // Will need to set this based on type of PlayerShip, must also be upgradeable
    private int numberOfAvailableWeaponModules = 2;
    private ArrayList<WeaponModule> weaponModules;

    CargoBayModule cargo;

    private boolean isDestroyed;

    Ship(String pName) {
        name = pName;
        cargo = new CargoBayModule("CargoModule1", 15);
        weaponModules = new ArrayList<>();
        shieldModule.shields(new BasicShieldsMk2());
        // Need to generate different hull integritys across shipStatus
        maxHullIntegrity = 500;
        remainingHullIntegrity = maxHullIntegrity;
        isDestroyed = false;
    }

    public void sustainFire(Attack attack) {
        int originalShields = shieldModule.shields().getRemainingShields();
        int originalHull = remainingHullIntegrity;

        System.out.println("Attack: sD " + attack.shieldDamage + " hD " + attack.hullDamage);
        Attack shieldedAttack = shieldModule.shieldAttack(attack);
        System.out.println("Shielded Attack: sD " + shieldedAttack.shieldDamage + " hD " + shieldedAttack.hullDamage);
        takeHullDamage(shieldedAttack);
        System.out.println("Shields: " + originalShields + " => " + shieldModule.shields().getRemainingShields());
        System.out.println("Hull: " + originalHull + " => " + remainingHullIntegrity);
    }
    
    private void takeHullDamage(Attack shieldedAttack) {
        remainingHullIntegrity = remainingHullIntegrity - shieldedAttack.hullDamage;
        if (remainingHullIntegrity < 0) {
            isDestroyed = true;
        }
    }

    public void addWeaponModule(int weaponModulePower) {
        if (weaponModules.size() < numberOfAvailableWeaponModules) {
            weaponModules.add(new WeaponModule("Weapon1",weaponModulePower));
        }
    }

    public ArrayList<WeaponModule> getWeaponModules() {
        return weaponModules;
    }

    public boolean equipWeapon(ShipWeapon newWeapon) {
        for (WeaponModule m : weaponModules) {
            if (m.maxWeaponPowerSupported >= newWeapon.requiredWeaponModulePower
            && m.getWeapon() == null) {
                System.out.println("This weapon has been equipped.");
                m.setWeapon(newWeapon);
                return true;
            }
        }

        System.out.println("Cannot equip weapon.");
        return false;
    }

    public CargoBayModule getCargoBay() {
        return cargo;
    }

    public boolean isDestroyed() {
        return isDestroyed;
    }

    public void rechargeShields() {
        shieldModule.rechargeShields();
    }

}
