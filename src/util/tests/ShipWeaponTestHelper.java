package util.tests;

import ship.modules.WeaponModule;
import ship.weapons.Attack;
import ship.weapons.HeavyRocket;
import util.rng.MockRandomNumberGenerator;

public class ShipWeaponTestHelper extends TestHelper {

	public static Attack mockEnemyHeavyRocketAttack() {
		WeaponModule enemyModule = new WeaponModule(consoleIOHandler, "EnemyMockWeapon", 5);
		enemyModule.setWeapon(new HeavyRocket());
		while (! enemyModule.isReadyToFire()) enemyModule.decrementTurnsTilWeaponReady();
		return enemyModule.attack(new MockRandomNumberGenerator(0.2));
	}

}
