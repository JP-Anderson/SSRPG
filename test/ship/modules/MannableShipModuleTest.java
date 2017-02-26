package ship.modules;

import characters.Crewmember;
import characters.classes.PilotClass;
import characters.classes.ScoundrelClass;
import characters.classes.WeaponsExpertClass;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MannableShipModuleTest {

	@Test
	public void assignCrewmemberSimpleCase() {
		Crewmember crewmember = firstCrewmember();
		MannableShipModule module = firstModule();
		assertEquals(null, module.getActiveCrewmember());
		assertEquals(null, crewmember.getMannedModule());

		module.assignCrewmember(crewmember);
		assertEquals(crewmember, module.getActiveCrewmember());
		assertEquals(module, crewmember.getMannedModule());
	}

	@Test
	public void assignAndDeassignCrewmember() {
		Crewmember crewmember = firstCrewmember();
		MannableShipModule module = firstModule();
		assertEquals(null, module.getActiveCrewmember());
		assertEquals(null, crewmember.getMannedModule());

		module.assignCrewmember(crewmember);
		assertEquals(crewmember, module.getActiveCrewmember());
		assertEquals(module, crewmember.getMannedModule());

		module.removeCrewmember();
		assertEquals(null, module.getActiveCrewmember());
		assertEquals(null, crewmember.getMannedModule());
	}

	@Test
	public void assignAndReassignCrewmember() {
		Crewmember crewmember = firstCrewmember();
		MannableShipModule firstModule = firstModule();
		MannableShipModule secondModule = secondModule();

		assertEquals(null, firstModule.getActiveCrewmember());
		assertEquals(null, secondModule.getActiveCrewmember());
		assertEquals(null, crewmember.getMannedModule());

		firstModule.assignCrewmember(crewmember);

		assertEquals(crewmember, firstModule.getActiveCrewmember());
		assertEquals(null, secondModule.getActiveCrewmember());
		assertEquals(firstModule, crewmember.getMannedModule());

		secondModule.assignCrewmember(crewmember);

		assertEquals(null, firstModule.getActiveCrewmember());
		assertEquals(crewmember, secondModule.getActiveCrewmember());
		assertEquals(secondModule, crewmember.getMannedModule());
	}

	@Test
	public void assignCrewmemberThenAssignSecondCrewmember() {
		Crewmember firstCrewmember = firstCrewmember();
		Crewmember secondCrewmember = secondCrewmember();
		MannableShipModule module = firstModule();

		module.assignCrewmember(firstCrewmember);

		assertEquals(firstCrewmember, module.getActiveCrewmember());
		assertEquals(module, firstCrewmember.getMannedModule());
		assertEquals(null, secondCrewmember.getMannedModule());

		module.assignCrewmember(secondCrewmember);

		assertEquals(secondCrewmember, module.getActiveCrewmember());
		assertEquals(null, firstCrewmember.getMannedModule());
		assertEquals(module, secondCrewmember.getMannedModule());
	}

	@Test
	public void reassignTwoCrewmembersToEachothersModule() {
		Crewmember firstCrewmember = firstCrewmember();
		Crewmember secondCrewmember = secondCrewmember();
		MannableShipModule firstModule = firstModule();
		MannableShipModule secondModule = firstModule();

		firstModule.assignCrewmember(firstCrewmember);
		secondModule.assignCrewmember(secondCrewmember);

		assertEquals(firstCrewmember, firstModule.getActiveCrewmember());
		assertEquals(secondCrewmember, secondModule.getActiveCrewmember());
		assertEquals(firstModule, firstCrewmember.getMannedModule());
		assertEquals(secondModule, secondCrewmember.getMannedModule());

		firstModule.assignCrewmember(secondCrewmember);
		secondModule.assignCrewmember(firstCrewmember);

		assertEquals(secondCrewmember, firstModule.getActiveCrewmember());
		assertEquals(firstCrewmember, secondModule.getActiveCrewmember());
		assertEquals(secondModule, firstCrewmember.getMannedModule());
		assertEquals(firstModule, secondCrewmember.getMannedModule());
	}

	//TODO IMPLEMENT FUNCTIONALITY TO PASS THIS TEST!!
	@Test
	public void assignAndReassignCrewmemberWithSetCrewmemberDoesntBreak() {
		Crewmember crewmember = firstCrewmember();
		MannableShipModule firstModule = firstModule();
		MannableShipModule secondModule = secondModule();

		assertEquals(null, firstModule.getActiveCrewmember());
		assertEquals(null, secondModule.getActiveCrewmember());
		assertEquals(null, crewmember.getMannedModule());

		firstModule.assignCrewmember(crewmember);

		assertEquals(crewmember, firstModule.getActiveCrewmember());
		assertEquals(null, secondModule.getActiveCrewmember());
		assertEquals(firstModule, crewmember.getMannedModule());

		secondModule.setActiveCrewmember(crewmember);

		// Fails here, as setActiveCrewmember doesn't reset the first module state
		assertEquals(null, firstModule.getActiveCrewmember());
		assertEquals(crewmember, secondModule.getActiveCrewmember());
		assertEquals(secondModule, crewmember.getMannedModule());
	}

	private MannableShipModule firstModule() {
		return new CockpitModule("Cockpit", 4);
	}

	private MannableShipModule secondModule() {
		return new WeaponModule("Weapon", 3);
	}

	private Crewmember firstCrewmember() {
		return new Crewmember("Han", new ScoundrelClass());
	}

	private Crewmember secondCrewmember() {
		return new Crewmember("Chewie", new WeaponsExpertClass());
	}

}