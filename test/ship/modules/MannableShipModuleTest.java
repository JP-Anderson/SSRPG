package ship.modules;

import arch.view.ConsoleIOHandler;
import characters.Crewmember;
import characters.classes.ScoundrelClass;
import characters.classes.WeaponsExpertClass;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MannableShipModuleTest {

	private ConsoleIOHandler consoleIOHandler = new ConsoleIOHandler();

	@Test
	public void assignCrewmemberSimpleCase() {
		Crewmember crewmember = firstCrewmember();
		MannableShipModule module = firstModule();
		assertEquals(null, module.getActiveCrewmember());
		assertFalse(crewmember.isManningAModule());

		module.assignCrewmember(crewmember);
		assertEquals(crewmember, module.getActiveCrewmember());
		assertTrue(crewmember.isManningAModule());
	}

	@Test
	public void assignAndDeassignCrewmember() {
		Crewmember crewmember = firstCrewmember();
		MannableShipModule module = firstModule();
		assertEquals(null, module.getActiveCrewmember());
		assertFalse(crewmember.isManningAModule());

		module.assignCrewmember(crewmember);
		assertEquals(crewmember, module.getActiveCrewmember());
		assertTrue(crewmember.isManningAModule());

		module.removeCrewmember();
		assertEquals(null, module.getActiveCrewmember());
		assertFalse(crewmember.isManningAModule());
	}

	@Test
	public void attemptingToAssignAnAssignedCrewmemberFails() {
		Crewmember crewmember = firstCrewmember();
		MannableShipModule firstModule = firstModule();
		MannableShipModule secondModule = secondModule();

		assertEquals(null, firstModule.getActiveCrewmember());
		assertEquals(null, secondModule.getActiveCrewmember());
		assertFalse(crewmember.isManningAModule());

		firstModule.assignCrewmember(crewmember);

		assertEquals(crewmember, firstModule.getActiveCrewmember());
		assertEquals(null, secondModule.getActiveCrewmember());
		assertTrue(crewmember.isManningAModule());

		assertFalse(secondModule.assignCrewmember(crewmember));

		assertEquals(crewmember, firstModule.getActiveCrewmember());
		assertEquals(null, secondModule.getActiveCrewmember());
		assertTrue(crewmember.isManningAModule());
	}

	@Test
	public void assignCrewmemberThenAssignSecondCrewmember() {
		Crewmember firstCrewmember = firstCrewmember();
		Crewmember secondCrewmember = secondCrewmember();
		MannableShipModule module = firstModule();

		module.assignCrewmember(firstCrewmember);

		assertEquals(firstCrewmember, module.getActiveCrewmember());
		assertTrue(firstCrewmember.isManningAModule());
		assertFalse(secondCrewmember.isManningAModule());

		assertFalse(module.assignCrewmember(secondCrewmember));

		assertEquals(firstCrewmember, module.getActiveCrewmember());
		assertTrue(firstCrewmember.isManningAModule());
		assertFalse(secondCrewmember.isManningAModule());
	}

	@Test
	public void cannotReassignCrewmembersToEachOthersModule() {
		Crewmember firstCrewmember = firstCrewmember();
		Crewmember secondCrewmember = secondCrewmember();
		MannableShipModule firstModule = firstModule();
		MannableShipModule secondModule = firstModule();

		firstModule.assignCrewmember(firstCrewmember);
		secondModule.assignCrewmember(secondCrewmember);

		assertEquals(firstCrewmember, firstModule.getActiveCrewmember());
		assertEquals(secondCrewmember, secondModule.getActiveCrewmember());
		assertTrue(firstCrewmember.isManningAModule());
		assertTrue(secondCrewmember.isManningAModule());

		firstModule.assignCrewmember(secondCrewmember);

		assertEquals(firstCrewmember, firstModule.getActiveCrewmember());
		assertEquals(secondCrewmember, secondModule.getActiveCrewmember());
		assertTrue(firstCrewmember.isManningAModule());
		assertTrue(secondCrewmember.isManningAModule());

		secondModule.assignCrewmember(firstCrewmember);

		assertEquals(firstCrewmember, firstModule.getActiveCrewmember());
		assertEquals(secondCrewmember, secondModule.getActiveCrewmember());
		assertTrue(firstCrewmember.isManningAModule());
		assertTrue(secondCrewmember.isManningAModule());
	}

	@Test
	public void canAssignCrewmemberToModuleAfterRemovingPreviousCrewmember() {
		Crewmember firstCrewmember = firstCrewmember();
		Crewmember secondCrewmember = secondCrewmember();
		MannableShipModule module = firstModule();

		assertTrue(module.assignCrewmember(firstCrewmember));

		assertFalse(module.assignCrewmember(secondCrewmember));

		assertTrue(firstCrewmember.isManningAModule());
		assertFalse(secondCrewmember.isManningAModule());

		module.removeCrewmember();

		assertTrue(module.assignCrewmember(secondCrewmember));

		assertFalse(firstCrewmember.isManningAModule());
		assertTrue(secondCrewmember.isManningAModule());
		assertEquals(secondCrewmember, module.getActiveCrewmember());

	}

	private MannableShipModule firstModule() {
		return new CockpitModule(consoleIOHandler, "Cockpit", 4);
	}

	private MannableShipModule secondModule() {
		return new WeaponModule(consoleIOHandler, "Weapon", 3);
	}

	private Crewmember firstCrewmember() {
		return new Crewmember("Han", new ScoundrelClass(), 1);
	}

	private Crewmember secondCrewmember() {
		return new Crewmember("Chewie", new WeaponsExpertClass(), 1);
	}

}