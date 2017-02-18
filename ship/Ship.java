package ship;

import characters.Crewmember;
import java.util.ArrayList;
import ship.modules.*;
import ship.weapons.ShipWeapon;
import ship.weapons.Attack;
import ship.shields.*;

public abstract class Ship {

    public final String name;
    ShipModules modules;
    int maxHullIntegrity;
    int remainingHullIntegrity;
    protected ArrayList<Crewmember> crew;
    int crewCapacity;

    CargoBayModule cargo;

    private boolean isDestroyed;

    Ship(String shipName, ShipModules shipModules) {
        name = shipName;
        modules = shipModules;
        maxHullIntegrity = 500;
        remainingHullIntegrity = maxHullIntegrity;
        isDestroyed = false;
    }

    public void sustainFire(Attack attack) {
        ShieldModule shieldModule = modules.getShieldModule();
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
        modules.addWeaponModule(weaponModulePower);
    }

    public ArrayList<WeaponModule> getWeaponModules() {
        return modules.getWeaponModules();
    }

    public boolean equipWeapon(ShipWeapon newWeapon) {
        return modules.equipWeapon(newWeapon);
    }

    public CargoBayModule getCargoBay() {
        return modules.getCargoBayModule();
    }

    public boolean isDestroyed() {
        return isDestroyed;
    }

    public void rechargeShields() {
        modules.getShieldModule().rechargeShields();
    }

}
