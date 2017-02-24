package ship.modules;

import ship.weapons.*;
import util.RNG;

public class WeaponModule extends MannableShipModule {

	public final int maxWeaponPowerSupported;
	private ShipWeapon loadedWeapon;
	private int baseTurnsTilWeaponReady;

	public WeaponModule(String newName, int maxWeaponPower) {
		super(newName, ShipModuleType.WEAPON, maxWeaponPower);
		maxWeaponPowerSupported = maxWeaponPower;
		loadedWeapon = null;
	}

	public void decrementTurnsTilWeaponReady() {
		baseTurnsTilWeaponReady = baseTurnsTilWeaponReady > 0 ? baseTurnsTilWeaponReady - 1 : 0;
	}

	public int getBaseTurnsTilWeaponReady() {
		return baseTurnsTilWeaponReady;
	}

	public void resetBaseTurnsTilWeaponReady() {
		baseTurnsTilWeaponReady = loadedWeapon.cooldown;
	}

	@Override
	public void printInformation() {
		super.printInformation();
		System.out.println("  + POWER [" + maxWeaponPowerSupported + "]");
	}

	public boolean setWeapon(ShipWeapon weapon) {
		if (maxWeaponPowerSupported >= weapon.requiredWeaponModulePower) {
			baseTurnsTilWeaponReady = weapon.cooldown;
			loadedWeapon = weapon;
			System.out.println("Equipped " + weapon.name + ".");
			return true;
		} else {
			System.out.println("Couldn't equip " + weapon.name + ".");
			return false;
		}
	}

	public Attack attack() {
		if (loadedWeapon != null && baseTurnsTilWeaponReady == 0)
			if (rollForAttackHitChance())
				return new Attack(loadedWeapon.shieldDamage, loadedWeapon.hullDamage, 0.9, loadedWeapon.weaponType);
		System.out.println("MISS!");
		return null;
	}

	private boolean rollForAttackHitChance() {
		double hitChance = loadedWeapon.baseHitChance;
		double roll = RNG.randZeroToOne();
		if (roll <= hitChance) return true;
		else return false;
	}

	public void removeWeapon() {
		loadedWeapon = null;
	}

	public ShipWeapon getWeapon() {
		return loadedWeapon;
	}

}
