package ship.shields;

import arch.view.ConsoleIOHandler;
import org.junit.jupiter.api.Test;
import ship.modules.ShieldModule;
import ship.modules.WeaponModule;
import ship.weapons.Attack;
import ship.weapons.ShipWeapon;
import ship.weapons.ShipWeaponsHolder;
import util.rng.MockRandomNumberGenerator;

import static org.junit.jupiter.api.Assertions.*;

class ShieldSystemTest {

	private ConsoleIOHandler consoleIOHandler = new ConsoleIOHandler();

	@Test
	public void shieldStateIsChargedWithFullHealth() {
		ShieldSystem shields = basicShieldSystem();
		ShieldModule shieldModule = loadShieldModule(shields);

		assertTrue(shields.getRemainingShields() == shields.getMaxShields());
		assertTrue(shields.getShieldState() == ShieldSystem.ShieldState.CHARGED);
	}

	@Test
	public void shieldStateIsDamagedWhenRemainingShieldsNotEmptyOrFull() {
		ShieldSystem shields = basicShieldSystem();
		WeaponModule weaponModule = loadWeaponModule(ShipWeaponsHolder.getWeapon("BurstLaserMk3"));
		ShieldModule shieldModule = loadShieldModule(shields);

		Attack attack = weaponModule.attack(getMockRNGWhichWillAlwaysRollSameDouble(0.1));

		Attack shieldedAttack = shieldModule.shieldAttack(attack);

		assertTrue(shields.getRemainingShields() > 0
				&& shields.getRemainingShields() < shields.getMaxShields());
		assertTrue(shields.getShieldState() == ShieldSystem.ShieldState.DAMAGED);
	}

	@Test
	public void shieldStateIsDepletedWhenShieldsAreEmpty() {
		ShieldSystem shields = basicShieldSystem();
		WeaponModule weaponModule = loadWeaponModule(ShipWeaponsHolder.getWeapon("BurstLaserMk3"));
		ShieldModule shieldModule = loadShieldModule(shields);

		MockRandomNumberGenerator rng = getMockRNGWhichWillAlwaysRollSameDouble(0.1);

		for (int i = 0; i < 5; i++ ) {
			Attack attack = weaponModule.attack(rng);
			shieldModule.shieldAttack(attack);
			chargeWeapon(weaponModule);
		}

		assertTrue(shields.getRemainingShields() == 0);
		assertTrue(shields.getShieldState() == ShieldSystem.ShieldState.DEPLETED);
	}

	@Test
	public void shieldStateBecomesRechargingAfterXRounds() {
		ShieldSystem shields = basicShieldSystem();
		WeaponModule weaponModule = loadWeaponModule(ShipWeaponsHolder.getWeapon("BurstLaserMk3"));
		ShieldModule shieldModule = loadShieldModule(shields);

		MockRandomNumberGenerator rng = getMockRNGWhichWillAlwaysRollSameDouble(0.1);
		for (int i = 0; i < 5; i++ ) {
			Attack attack = weaponModule.attack(rng);
			shieldModule.shieldAttack(attack);
			chargeWeapon(weaponModule);
		}

		int turnsUntilRecharge = shields.getActualTurnsTilRecharge();

		for (int i = 0; i < turnsUntilRecharge; i++) {
			assertTrue(shields.getShieldState() == ShieldSystem.ShieldState.DEPLETED);
			assertTrue(shields.getRemainingShields() == 0);
			shieldModule.rechargeShields();
		}

		assertTrue(shields.getShieldState() == ShieldSystem.ShieldState.RECHARGING);
	}

	@Test
	public void shieldRechargesBackToChargedStateWhenNotDamaged() {
		ShieldSystem shields = basicShieldSystem();
		WeaponModule weaponModule = loadWeaponModule(ShipWeaponsHolder.getWeapon("BurstLaserMk3"));
		ShieldModule shieldModule = loadShieldModule(shields);

		MockRandomNumberGenerator rng = getMockRNGWhichWillAlwaysRollSameDouble(0.1);
		for (int i = 0; i < 5; i++ ) {
			Attack attack = weaponModule.attack(rng);
			shieldModule.shieldAttack(attack);
			chargeWeapon(weaponModule);
		}

		while (shields.getShieldState() != ShieldSystem.ShieldState.RECHARGING) {
			shieldModule.rechargeShields();
		}

		int shieldReadingOne = shields.getRemainingShields();
		shieldModule.rechargeShields();
		int shieldReadingTwo = shields.getRemainingShields();
		assertTrue(shieldReadingTwo - shieldReadingOne == shields.getRechargePerTurn());

		while (shields.getShieldState() != ShieldSystem.ShieldState.CHARGED) {
			shieldModule.rechargeShields();
		}

		assertTrue(shields.getShieldState() == ShieldSystem.ShieldState.CHARGED);
		assertTrue(shields.getRemainingShields() == shields.getMaxShields());
	}

	@Test
	public void shieldTakesCorrectShieldDamageFromLaserAttack() {
		ShieldSystem shields = basicShieldSystem();
		WeaponModule weaponModule = loadWeaponModule(ShipWeaponsHolder.getWeapon("BurstLaserMk3"));
		ShieldModule shieldModule = loadShieldModule(shields);

		Attack attack = weaponModule.attack(getMockRNGWhichWillAlwaysRollSameDouble(0.1));
		shieldModule.shieldAttack(attack);

		int baseShieldDamage = weaponModule.getWeapon().shieldDamage;
		int expectedShieldsRemaining = shields.getMaxShields() - baseShieldDamage;

		assertEquals(shields.getRemainingShields(), expectedShieldsRemaining);
	}

	@Test
	public void shieldTakesCorrectShieldDamageFromRocketAttack() {
		ShieldSystem shields = basicShieldSystem();
		WeaponModule weaponModule = loadWeaponModule(ShipWeaponsHolder.getWeapon("Heavy Rocket"));
		ShieldModule shieldModule = loadShieldModule(shields);

		Attack attack = weaponModule.attack(getMockRNGWhichWillAlwaysRollSameDouble(0.1));
		shieldModule.shieldAttack(attack);

		int baseShieldDamage = weaponModule.getWeapon().shieldDamage;
		int expectedShieldsRemaining = shields.getMaxShields() - baseShieldDamage;

		assertEquals(shields.getRemainingShields(), expectedShieldsRemaining);
	}

	@Test
	public void fullShieldReducesHullDamageOfLaserAttack() {
		ShieldSystem shields = basicShieldSystem();
		WeaponModule weaponModule = loadWeaponModule(ShipWeaponsHolder.getWeapon("BurstLaserMk3"));
		ShieldModule shieldModule = loadShieldModule(shields);

		Attack attack = weaponModule.attack(getMockRNGWhichWillAlwaysRollSameDouble(0.1));
		Attack shieldedAttack = shieldModule.shieldAttack(attack);

		int baseHullDamage = weaponModule.getWeapon().hullDamage;
		double absorption = shields.getHullDamageAbsorption();
		int expectedDamage = (int) (baseHullDamage * (1-absorption));

		assertEquals(shieldedAttack.hullDamage, expectedDamage);
	}

	@Test
	public void nearEmptyShieldReducesHullDamageOfLaserAttack() {
		ShieldSystem shields = basicShieldSystem();
		WeaponModule weaponModule = loadWeaponModule(ShipWeaponsHolder.getWeapon("BurstLaserMk3"));
		ShieldModule shieldModule = loadShieldModule(shields);

		MockRandomNumberGenerator rng = getMockRNGWhichWillAlwaysRollSameDouble(0.1);
		for (int i = 0; i < 4; i++ ) {
			Attack attack = weaponModule.attack(rng);
			shieldModule.shieldAttack(attack);
			chargeWeapon(weaponModule);
		}

		Attack attack = weaponModule.attack(rng);
		Attack shieldedAttack = shieldModule.shieldAttack(attack);

		int baseHullDamage = weaponModule.getWeapon().hullDamage;
		double absorption = shields.getHullDamageAbsorption();
		int expectedDamage = (int) (baseHullDamage * (1-absorption));

		assertEquals(shieldedAttack.hullDamage, expectedDamage);
	}

	@Test
	public void emptyShieldDoesntReduceHullDamageOfLaserAttack() {
		ShieldSystem shields = basicShieldSystem();
		WeaponModule weaponModule = loadWeaponModule(ShipWeaponsHolder.getWeapon("BurstLaserMk3"));
		ShieldModule shieldModule = loadShieldModule(shields);

		MockRandomNumberGenerator rng = getMockRNGWhichWillAlwaysRollSameDouble(0.1);
		for (int i = 0; i < 5; i++ ) {
			Attack attack = weaponModule.attack(rng);
			shieldModule.shieldAttack(attack);
			chargeWeapon(weaponModule);
		}

		Attack attack = weaponModule.attack(rng);
		Attack shieldedAttack = shieldModule.shieldAttack(attack);

		int baseHullDamage = weaponModule.getWeapon().hullDamage;

		assertEquals(shieldedAttack.hullDamage, baseHullDamage);
	}

	@Test
	public void shieldDoesntReduceRocketBasedHullDamage() {
		ShieldSystem shields = basicShieldSystem();
		WeaponModule weaponModule = loadWeaponModule(ShipWeaponsHolder.getWeapon("Heavy Rocket"));
		ShieldModule shieldModule = loadShieldModule(shields);

		MockRandomNumberGenerator rng = getMockRNGWhichWillAlwaysRollSameDouble(0.1);

		Attack attack = weaponModule.attack(rng);
		Attack shieldedAttack = shieldModule.shieldAttack(attack);

		int baseHullDamage = weaponModule.getWeapon().hullDamage;

		assertEquals(shieldedAttack.hullDamage, baseHullDamage);
	}

	private MockRandomNumberGenerator getMockRNGWhichWillAlwaysRollSameDouble(double zeroToOne) {
		MockRandomNumberGenerator rng = new MockRandomNumberGenerator();
		rng.loadSingleRandZeroToOneDouble(zeroToOne);
		return rng;
	}

	private ShieldSystem basicShieldSystem() {
		return new BasicShieldsMk2();
	}

	private ShieldModule loadShieldModule(ShieldSystem shieldSystem) {
		ShieldModule shieldModule = new ShieldModule(consoleIOHandler, "TestShieldModule", 5);
		shieldModule.shields(shieldSystem);
		return shieldModule;
	}

	private WeaponModule loadWeaponModule(ShipWeapon weapon) {
		WeaponModule weaponModule = new WeaponModule(consoleIOHandler, "TestWeaponModule", 5);
		weaponModule.setWeapon(weapon);
		chargeWeapon(weaponModule);
		return weaponModule;
	}

	private void chargeWeapon(WeaponModule weaponModule) {
		while (!weaponModule.isReadyToFire()) {
			weaponModule.decrementTurnsTilWeaponReady();
		}
	}

}