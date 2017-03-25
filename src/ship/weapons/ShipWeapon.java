package ship.weapons;

public class ShipWeapon {

	public final int id;
	public final String name;
	public final WeaponType weaponType;
	public final int cost;
	public final int cooldown;
	public final int shieldDamage;
	public final int hullDamage;
	public final double baseHitChance;
	public final int requiredWeaponModulePower;

	public ShipWeapon(int id, String name, WeaponType weaponType, int cost, int cooldown, int shieldDamage, int hullDamage, double baseHitChance, int requiredWeaponModulePower) {
		this.id = id;
		this.name = name;
		this.weaponType = weaponType;
		this.cost = cost;
		this.cooldown = cooldown;
		this.shieldDamage = shieldDamage;
		this.hullDamage = hullDamage;
		this.baseHitChance = baseHitChance;
		this.requiredWeaponModulePower = requiredWeaponModulePower;
	}

	public enum WeaponType {
		LAZER, PLASMA, ROCKET
	}

}
