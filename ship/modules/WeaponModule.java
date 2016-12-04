package ship.modules;

import ship.weapons.*;
import util.RNG;

public class WeaponModule extends ShipModule {

    public final int maxWeaponPowerSupported;
    private ShipWeapon loadedWeapon;
    private int turnsTilWeaponReady;

    public WeaponModule(int maxWeaponPower) {
        maxWeaponPowerSupported = maxWeaponPower;
        loadedWeapon = null;
    }

    public void decrementTurnsTilWeaponReady() {
        turnsTilWeaponReady = turnsTilWeaponReady > 0 ?
            turnsTilWeaponReady-1:0;
    }

    public int getTurnsTilWeaponReady() {
        return turnsTilWeaponReady;
    }

    public void resetTurnsTilWeaponReady() {
        turnsTilWeaponReady = loadedWeapon.cooldown;
    }

    @Override
    public void printInformation() {
        System.out.println(" - WEAPON MODULE ["+name+"]");
        System.out.println("  + POWER ["+maxWeaponPowerSupported+"]");
    }

    public boolean setWeapon(ShipWeapon weapon) {
        if (maxWeaponPowerSupported >= weapon.requiredWeaponModulePower) {
            turnsTilWeaponReady = weapon.cooldown;
            loadedWeapon = weapon;
            System.out.println("Equipped " + weapon.name + ".");
            return true;
        } else {
            System.out.println("Couldn't equip " + weapon.name + ".");
            return false;
        }
    }

    public Attack attack() {
        if (loadedWeapon != null && turnsTilWeaponReady == 0)
            if (rollForAttackHitChance()) return new Attack(loadedWeapon.shieldDamage, loadedWeapon.hullDamage, 0.9, loadedWeapon.weaponType);
        System.out.println("MISS!");
        return null;
    }

    private boolean rollForAttackHitChance() {
        double hitChance = loadedWeapon.baseHitChance;
        double roll = RNG.randZeroToOne();
        if (roll<= hitChance) return true;
        else return false;
    }

    public void removeWeapon() {
        loadedWeapon = null;
    }

    public ShipWeapon getWeapon() { return loadedWeapon; }

}
