package ship.modules;

import arch.view.ConsoleIOHandler;
import org.junit.jupiter.api.Test;
import ship.weapons.Attack;
import ship.weapons.ShipWeapon;
import ship.weapons.ShipWeaponsHolder;
import util.rng.MockRandomNumberGenerator;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class WeaponModuleTest {

	private ConsoleIOHandler consoleIOHandler = new ConsoleIOHandler();

	@Test
	public void cannotEquipWeaponExceedingModulePower() {
		WeaponModule module = new WeaponModule(consoleIOHandler, "TestModule", 2);

		ShipWeapon railgun = ShipWeaponsHolder.getWeapon("Rail Gun");

		assertTrue(railgun.requiredWeaponModulePower > module.getModulePower());
		assertFalse(module.setWeapon(railgun));
		assertNull(module.getWeapon());
	}

	@Test
	public void canReplaceWeapon() {
		WeaponModule module = new WeaponModule(consoleIOHandler, "TestModule", 3);
		ShipWeapon railgun = ShipWeaponsHolder.getWeapon("Rail Gun");
		ShipWeapon laser = ShipWeaponsHolder.getWeapon("BurstLaserMk3");

		assertTrue(module.setWeapon(railgun));
		assertEquals(railgun, module.getWeapon());

		assertTrue(module.setWeapon(laser));
		assertEquals(laser, module.getWeapon());
	}

	@Test
	public void removeWeapon() {
		WeaponModule module = new WeaponModule(consoleIOHandler, "TestModule", 3);
		ShipWeapon laser = ShipWeaponsHolder.getWeapon("BurstLaserMk3");

		assertTrue(module.setWeapon(laser));
		assertEquals(laser, module.getWeapon());

		module.removeWeapon();
		assertNull(module.getWeapon());
	}


	@Test
	public void canAttackWithWeaponAfterCooldownPeriod() {
		ShipWeapon burstLaserMk3 = ShipWeaponsHolder.getWeapon("BurstLaserMk3");
		WeaponModule module = loadAndChargeWeapon(burstLaserMk3);

		MockRandomNumberGenerator mockRNG = new MockRandomNumberGenerator();
		mockRNG.loadSequenceOfRandZeroToOneDoubles(new ArrayList<>(
				Arrays.asList(0.1)
				)
		);

		Attack attack = module.attack(mockRNG);
		assertEquals(burstLaserMk3.hullDamage, attack.hullDamage);
		assertEquals(burstLaserMk3.shieldDamage, attack.shieldDamage);
		assertEquals(burstLaserMk3.baseHitChance, attack.accuracy);
		assertEquals(burstLaserMk3.weaponType, attack.weaponType);
	}

	@Test
	public void attackWithRNGLessThanWeaponAccuracyIsAMiss() {
		ShipWeapon laser = ShipWeaponsHolder.getWeapon("BurstLaserMk3");
		WeaponModule module = loadAndChargeWeapon(laser);

		// Base hit chance is a double between 0 and 1
		// 0.9 is a high chance of 90% shots on target (excluding dodge)
		double weaponAccuracy = laser.baseHitChance;

		MockRandomNumberGenerator mockRNG = new MockRandomNumberGenerator();
		mockRNG.loadSequenceOfRandZeroToOneDoubles(new ArrayList<>(
						Arrays.asList(weaponAccuracy-0.0001)
				)
		);

		Attack attemptedAttack = module.attack(mockRNG);
		assertNotNull(attemptedAttack);
	}

	@Test
	public void attackWithRNGOverWeaponAccuracyIsAMiss() {
		ShipWeapon laser = ShipWeaponsHolder.getWeapon("BurstLaserMk3");
		WeaponModule module = loadAndChargeWeapon(laser);

		// Base hit chance is a double between 0 and 1
		// 0.9 is a high chance of 90% shots on target (excluding dodge)
		double weaponAccuracy = laser.baseHitChance;

		MockRandomNumberGenerator mockRNG = new MockRandomNumberGenerator();
		mockRNG.loadSequenceOfRandZeroToOneDoubles(new ArrayList<>(
						Arrays.asList(weaponAccuracy+0.01)
				)
		);

		Attack attemptedAttack = module.attack(mockRNG);
		assertNull(attemptedAttack);
	}

	private WeaponModule loadAndChargeWeapon(ShipWeapon weaponToEquip) {
		int powerReq = weaponToEquip.requiredWeaponModulePower;
		WeaponModule module = new WeaponModule(consoleIOHandler, "TestModule", powerReq);
		module.setWeapon(weaponToEquip);
		int turnsToCharge = weaponToEquip.cooldown;
		for (int turns = 0; turns < turnsToCharge; turns++ ) {
			module.decrementTurnsTilWeaponReady();
		}
		return module;
	}

}