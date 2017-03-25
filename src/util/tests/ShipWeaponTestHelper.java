package util.tests;

import ship.modules.WeaponModule;
import ship.weapons.Attack;
import ship.weapons.ShipWeapon;
import ship.weapons.ShipWeaponsHolder;
import util.rng.MockRandomNumberGenerator;

public class ShipWeaponTestHelper extends TestHelper {

	public static Attack mockEnemyHeavyRocketAttack() {
		ShipWeapon weapon = ShipWeaponsHolder.getWeapon("Heavy Rocket");

		WeaponModule enemyModule = new WeaponModule(consoleIOHandler, "EnemyMockWeapon", 5);
		enemyModule.setWeapon(weapon);
		while (! enemyModule.isReadyToFire()) enemyModule.decrementTurnsTilWeaponReady();
		return enemyModule.attack(new MockRandomNumberGenerator(0.2));
	}

}
