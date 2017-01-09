package ship;

import characters.Crewmember;
import java.util.ArrayList;
import ship.modules.*;
import ship.weapons.ShipWeapon;
import ship.weapons.Attack;
import ship.shields.*;

public abstract class AbstractShip {

    public final String name;

    protected int maxHullIntegrity;
    protected int remainingHullIntegrity;

    protected ArrayList<Crewmember> crew;
    protected int crewCapacity;

    // todo: create getters/setters for this
    // todo: don't use hardcoded modules
    protected EngineModule engines = new EngineModule("Engines1", 5);
    protected ShieldModule shieldModule = new ShieldModule("Shields1", 2);

    // Will need to set this based on type of Ship, must also be upgradeable
    protected int numberOfAvailableWeaponModules = 2;
    protected ArrayList<WeaponModule> weaponModules;

    protected CargoBay cargo;

    protected boolean isDestroyed;

    public AbstractShip(String pName) {
        name = pName;
        cargo = new CargoBay(15);
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

    public CargoBay getCargoBay() {
        return cargo;
    }

    public boolean isDestroyed() {
        return isDestroyed;
    }

    public void rechargeShields() {
        shieldModule.rechargeShields();
    }

    private void takeHullDamage(Attack shieldedAttack) {
        remainingHullIntegrity = remainingHullIntegrity - shieldedAttack.hullDamage;
        if (remainingHullIntegrity < 0) {
            isDestroyed = true;
        }
    }

}
