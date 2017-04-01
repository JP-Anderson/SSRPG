package ship;

import org.junit.jupiter.api.Test;
import ship.modules.EngineModule;
import ship.modules.WeaponModule;
import util.tests.PlayerShipTestHelper;

import static org.junit.jupiter.api.Assertions.*;

class CombatSequencerTest {

	@Test
	public void combatSequencerForBasicShipReturnsJustEngines() {
		PlayerShip testShip = getDefaultPlayerShip();
		CombatSequencer combatSequencer = testShip.modules.getModulesForCombat();

		EngineModule engineModule = testShip.getEngineModule();

		assertTrue(combatSequencer.getModules().get(0) == engineModule);
		assertEquals(1, combatSequencer.getModules().size());
	}

	@Test
	public void combatSequencerReturnsCorrectlyWithTwoWeaponModules() {
		PlayerShip testShip = getPlayerShipWithTwoWeaponModules();
		CombatSequencer combatSequencer = testShip.modules.getModulesForCombat();

		EngineModule engineModule = testShip.getEngineModule();
		WeaponModule weaponModuleOne = testShip.getWeaponModules().get(0);
		WeaponModule weaponModuleTwo = testShip.getWeaponModules().get(1);

		assertTrue(combatSequencer.getModules().get(0) == engineModule);
		assertTrue(combatSequencer.getModules().get(1) == weaponModuleOne);
		assertTrue(combatSequencer.getModules().get(2) == weaponModuleTwo);
		assertEquals(3, combatSequencer.getModules().size());
	}

	@Test
	public void combatSequencerMakesFirstModuleReadyOnFirstTurn() {
		PlayerShip testShip = getPlayerShipWithTwoWeaponModules();
		CombatSequencer combatSequencer = testShip.modules.getModulesForCombat();

		EngineModule engineModule = testShip.getEngineModule();
		WeaponModule weaponModuleOne = testShip.getWeaponModules().get(0);
		WeaponModule weaponModuleTwo = testShip.getWeaponModules().get(1);

		assertFalse(engineModule.moduleIsCharged());
		assertFalse(weaponModuleOne.moduleIsCharged());
		assertFalse(weaponModuleTwo.moduleIsCharged());


		combatSequencer.chargeNextModule();

		assertTrue(engineModule.moduleIsCharged());
		assertFalse(weaponModuleOne.moduleIsCharged());
		assertFalse(weaponModuleTwo.moduleIsCharged());
	}

	@Test
	public void combatSequencerCorrectlyChargesModulesInOrder() {
		PlayerShip testShip = getPlayerShipWithTwoWeaponModules();
		CombatSequencer combatSequencer = testShip.modules.getModulesForCombat();

		EngineModule engineModule = testShip.getEngineModule();
		WeaponModule weaponModuleOne = testShip.getWeaponModules().get(0);
		WeaponModule weaponModuleTwo = testShip.getWeaponModules().get(1);

		assertFalse(engineModule.moduleIsCharged());
		assertFalse(weaponModuleOne.moduleIsCharged());
		assertFalse(weaponModuleTwo.moduleIsCharged());

		combatSequencer.chargeNextModule();

		assertTrue(engineModule.moduleIsCharged());
		assertFalse(weaponModuleOne.moduleIsCharged());
		assertFalse(weaponModuleTwo.moduleIsCharged());

		combatSequencer.chargeNextModule();

		assertTrue(engineModule.moduleIsCharged());
		assertTrue(weaponModuleOne.moduleIsCharged());
		assertFalse(weaponModuleTwo.moduleIsCharged());

		combatSequencer.chargeNextModule();

		assertTrue(engineModule.moduleIsCharged());
		assertTrue(weaponModuleOne.moduleIsCharged());
		assertTrue(weaponModuleTwo.moduleIsCharged());
	}


	private PlayerShip getDefaultPlayerShip() {
		return PlayerShipTestHelper.populateBasicShip(PlayerShipTestHelper.makeCrewWithPilot());
	}

	private PlayerShip getPlayerShipWithTwoWeaponModules() {
		return PlayerShipTestHelper.populateShipWithTwoWeaponModules(PlayerShipTestHelper.makeCrewWithPilot());
	}

}