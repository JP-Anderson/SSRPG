package ship.weapons;

import ship.weapons.ShipWeapon.WeaponType;

public class Attack {

	public final int shieldDamage;
	public final int hullDamage;
	public final double accuracy;
	public final WeaponType weaponType;

	public Attack(int shield, int hull, double acc, WeaponType type) {
		shieldDamage = shield;
		hullDamage = hull;
		accuracy = acc;
		weaponType = type;
	}

}
