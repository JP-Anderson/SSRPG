package ship;

import arch.view.ConsoleIOHandler;
import characters.Crewmember;
import characters.classes.PilotClass;
import characters.classes.ScoundrelClass;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ship.modules.MannableShipModule;

import static org.junit.jupiter.api.Assertions.*;

class ShipTest {

	private static ConsoleIOHandler consoleIOHandler = new ConsoleIOHandler();

	private PlayerShip getTestShip() {
		return new PlayerShip.PlayerShipBuilder(consoleIOHandler, "TestShip",10).build();
	}

	@Test
	void ensureNoParamBuiltShipHasCockpitAndEngineModules() {
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
	void checkAnyCrewmemberHasAbilityReturnsTrueIfCrewmemberHasAbilityUnlocked() {
		PlayerShip testShip = getTestShip();
		Crewmember pilot = createPilotWithEvasiveManeuversAbility();
		testShip.crew.add(pilot);

		assertTrue(testShip.checkAnyCrewmemberHasAbility("Evasive Maneuvers"));
	}

	@Test
	void checkAnyCrewmemberHasAbilityReturnsFalseIfCrewmemberHasAbilityButLocked() {
		PlayerShip testShip = getTestShip();
		Crewmember pilot = createPilotWithoutEvasiveManeuversAbility();
		testShip.crew.add(pilot);

		assertFalse(testShip.checkAnyCrewmemberHasAbility("Evasive Maneuvers"));
	}

	@Test
	void checkAnyCrewmemberHasAbilityReturnsFalseIfNoCrewmemberHasAbility() {
		PlayerShip testShip = getTestShip();
		Crewmember scoundrel = createScoundrel();
		testShip.crew.add(scoundrel);

		assertFalse(testShip.checkAnyCrewmemberHasAbility("Evasive Maneuvers"));
	}

	@Test
	void getAbilityIfUnlockedReturnsAbilityIfCrewmemberHasAbilityUnlocked() {
		PlayerShip testShip = getTestShip();
		Crewmember pilot = createPilotWithEvasiveManeuversAbility();
		testShip.crew.add(pilot);

		assertNotNull(testShip.getAbilityIfUnlockedForAnyCrewmember("Evasive Maneuvers"));
	}

	@Test
	void getAbilityIfUnlockedReturnsNullIfCrewmemberHasAbilityButLocked() {
		PlayerShip testShip = getTestShip();
		Crewmember pilot = createPilotWithoutEvasiveManeuversAbility();
		testShip.crew.add(pilot);

		assertNull(testShip.getAbilityIfUnlockedForAnyCrewmember("Evasive Maneuvers"));
	}

	@Test
	void getAbilityIfUnlockedReturnsNullIfNoCrewmemberHasAbility() {
		PlayerShip testShip = getTestShip();
		Crewmember scoundrel = createScoundrel();
		testShip.crew.add(scoundrel);

		assertNull(testShip.getAbilityIfUnlockedForAnyCrewmember("Evasive Maneuvers"));
	}

}