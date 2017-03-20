package ship.modules;

import arch.view.View;
import ship.weapons.*;
import util.rng.RNG;
import util.rng.RandomNumberGenerator;

public class WeaponModule extends MannableShipModule {

	public final int maxWeaponPowerSupported;
	private ShipWeapon loadedWeapon;
	private int baseTurnsTilWeaponReady;

	public WeaponModule(View view, String newName, int maxWeaponPower) {
		super(view, newName, ShipModuleType.WEAPON, maxWeaponPower);
		maxWeaponPowerSupported = maxWeaponPower;
		loadedWeapon = null;
	}

	public void decrementTurnsTilWeaponReady() {
		baseTurnsTilWeaponReady = baseTurnsTilWeaponReady > 0 ? baseTurnsTilWeaponReady - 1 : 0;
	}

	public int getBaseTurnsTilWeaponReady() {
		return baseTurnsTilWeaponReady;
	}

	public boolean isReadyToFire() {
		return baseTurnsTilWeaponReady == 0;
	}

	public void resetBaseTurnsTilWeaponReady() {
		baseTurnsTilWeaponReady = loadedWeapon.cooldown;
	}

	@Override
	public void printInformation() {
		super.printInformation();
		view.outputHandler.sendStringToView("  + POWER [" + maxWeaponPowerSupported + "]");
	}

	public boolean setWeapon(ShipWeapon weapon) {
		if (maxWeaponPowerSupported >= weapon.requiredWeaponModulePower) {
			baseTurnsTilWeaponReady = weapon.cooldown;
			loadedWeapon = weapon;
			view.outputHandler.sendStringToView("Equipped " + weapon.name + ".");
			return true;
		} else {
			view.outputHandler.sendStringToView("Couldn't equip " + weapon.name + ".");
			return false;
		}
	}

	public Attack attack(RandomNumberGenerator randomNumGenerator) {
		if (loadedWeapon != null && baseTurnsTilWeaponReady == 0) {
			if (rollForAttackHitChance(randomNumGenerator)) {
				return new Attack(
						loadedWeapon.shieldDamage,
						loadedWeapon.hullDamage,
						loadedWeapon.baseHitChance,
						loadedWeapon.weaponType);
			}
			view.outputHandler.sendStringToView("MISS!");
		}
		return null;
	}

	private boolean rollForAttackHitChance(RandomNumberGenerator randomNumGenerator) {
		double hitChance = loadedWeapon.baseHitChance;
		double roll = randomNumGenerator.randZeroToOne();
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
