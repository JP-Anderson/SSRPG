package ship;

import arch.view.ConsoleIOHandler;
import characters.Crewmember;
import characters.classes.PilotClass;
import characters.classes.ScoundrelClass;
import org.junit.jupiter.api.Test;
import ship.modules.*;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ShipTest {

	private static ConsoleIOHandler consoleIOHandler = new ConsoleIOHandler();

	private PlayerShip getTestShip() {
		return new PlayerShip.PlayerShipBuilder(consoleIOHandler, "TestShip",10).build();
	}

	@Test
	public void ensureNoParamBuiltShipHasCockpitAndEngineModules() {
		PlayerShip testShip = getTestShip();
		MannableShipModule cockpit = testShip.getCockpitModule();
		MannableShipModule engineModule = testShip.getEngineModule();
		assertNotNull(cockpit);
		assertNotNull(engineModule);
	}

	private Crewmember createPilotWithEvasiveManeuversAbility() {
		Crewmember pilot = createPilotWithoutEvasiveManeuversAbility();
		pilot.tryToLevelUpAbility(0);
		return pilot;
	}

	private Crewmember createPilotWithoutEvasiveManeuversAbility() {
		Crewmember pilot = new Crewmember("TestPilot", new PilotClass(), 3);
		return pilot;
	}

	private Crewmember createScoundrel() {
		Crewmember scoundrel = new Crewmember("TestScoundrel", new ScoundrelClass(), 3);
		return scoundrel;
	}

	@Test
	public void checkAnyCrewmemberHasAbilityReturnsTrueIfCrewmemberHasAbilityUnlocked() {
		PlayerShip testShip = getTestShip();
		Crewmember pilot = createPilotWithEvasiveManeuversAbility();
		testShip.crew.add(pilot);

		assertTrue(testShip.checkAnyCrewmemberHasAbility("Evasive Maneuvers"));
	}

	@Test
	public void checkAnyCrewmemberHasAbilityReturnsFalseIfCrewmemberHasAbilityButLocked() {
		PlayerShip testShip = getTestShip();
		Crewmember pilot = createPilotWithoutEvasiveManeuversAbility();
		testShip.crew.add(pilot);

		assertFalse(testShip.checkAnyCrewmemberHasAbility("Evasive Maneuvers"));
	}

	@Test
	public void checkAnyCrewmemberHasAbilityReturnsFalseIfNoCrewmemberHasAbility() {
		PlayerShip testShip = getTestShip();
		Crewmember scoundrel = createScoundrel();
		testShip.crew.add(scoundrel);

		assertFalse(testShip.checkAnyCrewmemberHasAbility("Evasive Maneuvers"));
	}

	@Test
	public void getAbilityIfUnlockedReturnsAbilityIfCrewmemberHasAbilityUnlocked() {
		PlayerShip testShip = getTestShip();
		Crewmember pilot = createPilotWithEvasiveManeuversAbility();
		testShip.crew.add(pilot);

		assertNotNull(testShip.getAbilityIfUnlockedForAnyCrewmember("Evasive Maneuvers"));
	}

	@Test
	public void getAbilityIfUnlockedReturnsNullIfCrewmemberHasAbilityButLocked() {
		PlayerShip testShip = getTestShip();
		Crewmember pilot = createPilotWithoutEvasiveManeuversAbility();
		testShip.crew.add(pilot);

		assertNull(testShip.getAbilityIfUnlockedForAnyCrewmember("Evasive Maneuvers"));
	}

	@Test
	public void getAbilityIfUnlockedReturnsEmptyListIfNoCrewmemberHasAbility() {
		PlayerShip testShip = getTestShip();
		Crewmember scoundrel = createScoundrel();
		testShip.crew.add(scoundrel);

		assertNull(testShip.getAbilityIfUnlockedForAnyCrewmember("Evasive Maneuvers"));
	}


	@Test
	public void getCrewmembersWithAbilityInSpecificModuleFailsWhenCrewmemberHasntUnlockedAbility() {
		PlayerShip testShip = getTestShipWithCargoBay();
		Crewmember scoundrel = createScoundrel();
		testShip.crew.add(scoundrel);

		CargoBayModule cargoBayModule = testShip.getCargoBay();
		cargoBayModule.assignCrewmember(scoundrel);

		String desiredSkillName = "Connected";
		assertTrue(testShip.getCrewmembersWithAbilityInSpecificModule(desiredSkillName, "CargoBayModule").isEmpty());
	}

	@Test
	public void getCrewmembersWithAbilityInSpecificModuleFailsWhenCrewmemberIsntInModule() {
		PlayerShip testShip = getTestShipWithCargoBay();
		Crewmember scoundrel = createScoundrel();
		testShip.crew.add(scoundrel);

		String desiredAbilityName = "Connected";

		scoundrel.tryToLevelUpAbility(0);

		assertTrue(scoundrel.tryAndGetAbility(desiredAbilityName).isUnlocked());
		assertTrue(testShip.getCrewmembersWithAbilityInSpecificModule(desiredAbilityName, "CargoBayModule").isEmpty());
	}


	@Test
	public void getCrewmembersWithAbilityInSpecificModulePassesWhenConditionsMet() {
		PlayerShip testShip = getTestShipWithCargoBay();
		Crewmember scoundrel = createScoundrel();
		testShip.crew.add(scoundrel);


		String desiredAbilityName = "Connected";
		CargoBayModule cargoBayModule = testShip.getCargoBay();
		cargoBayModule.assignCrewmember(scoundrel);

		scoundrel.tryToLevelUpAbility(0);

		assertTrue(scoundrel.tryAndGetAbility(desiredAbilityName).isUnlocked());
		assertTrue(testShip.getCrewmembersWithAbilityInSpecificModule(desiredAbilityName, "CargoBayModule").size()>0);
	}

	@Test
	public void getModulesToBeSequencedInCombatGetsCorrectWeaponShieldCockpitCargoAndEngineModules() {
		PlayerShip testShip = getTestShipWithWeaponShieldCockpitAndCargoModule();

		ArrayList<ShipModule> modules = (ArrayList<ShipModule>) testShip.getModulesToBeSequencedInCombat();
		assertEquals(3, modules.size());
	}

	@Test
	public void getModulesToBeSequencedInCombatGetsCorrectMultipleWeaponCockpitCargoAndEngineModules() {
		PlayerShip testShip = getTestShipWithTwoWeaponsCockpitAndCargoModule();

		ArrayList<ShipModule> modules = (ArrayList<ShipModule>) testShip.getModulesToBeSequencedInCombat();
		assertEquals(3, modules.size());
	}

	@Test
	public void getModulesToBeSequencedInCombatGetsCorrectEngineModule() {
		PlayerShip testShip = getTestShipWithCockpitCargoAndEngineModule();

		ArrayList<ShipModule> modules = (ArrayList<ShipModule>) testShip.getModulesToBeSequencedInCombat();
		assertEquals(1, modules.size());
	}

	private PlayerShip getTestShipWithWeaponShieldCockpitAndCargoModule() {
		ArrayList<WeaponModule> weaponModules = new ArrayList<>();
		weaponModules.add(new WeaponModule(consoleIOHandler, "TestWeaponModule",3));
		return new PlayerShip.PlayerShipBuilder(consoleIOHandler, "TestShip",20)
				.weaponModules(weaponModules)
				.shieldModule(new ShieldModule(consoleIOHandler, "TestShieldModule", 7))
				.cockpitModule(new CockpitModule(consoleIOHandler, "TestCockpitModule",3))
				.cargoBayModule(new CargoBayModule(consoleIOHandler, "TestCargo", 5, 20))
				.build();
	}

	private PlayerShip getTestShipWithTwoWeaponsCockpitAndCargoModule() {
		ArrayList<WeaponModule> weaponModules = new ArrayList<>();
		weaponModules.add(new WeaponModule(consoleIOHandler, "TestWeaponModule",3));
		weaponModules.add(new WeaponModule(consoleIOHandler, "TestWeaponModule2", 4));
		return new PlayerShip.PlayerShipBuilder(consoleIOHandler, "TestShip",25)
				.weaponModules(weaponModules)
				.cockpitModule(new CockpitModule(consoleIOHandler, "TestCockpitModule",3))
				.cargoBayModule(new CargoBayModule(consoleIOHandler, "TestCargo", 5, 20))
				.build();
	}

	private PlayerShip getTestShipWithCockpitCargoAndEngineModule() {
		return new PlayerShip.PlayerShipBuilder(consoleIOHandler, "TestShip",25)
				.cockpitModule(new CockpitModule(consoleIOHandler, "TestCockpitModule",3))
				.cargoBayModule(new CargoBayModule(consoleIOHandler, "TestCargo", 5, 20))
				.build();
	}

	private PlayerShip getTestShipWithCargoBay() {
		return new PlayerShip.PlayerShipBuilder(consoleIOHandler, "TestShip",10)
				.cargoBayModule(new CargoBayModule(consoleIOHandler, "TestCargo", 5, 20))
				.build();
	}

}