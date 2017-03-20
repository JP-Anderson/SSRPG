package ship.weapons;

public class HeavyRocket extends ShipWeapon {

	public HeavyRocket() {
		super("Heavy Rocket");
		cost = 500;
		cooldown = 5;
		hullDamage = 190;
		baseHitChance = 0.8;
		shieldDamage = 5;
		requiredWeaponModulePower = 3;
		weaponType = WeaponType.ROCKET;
	}

}
