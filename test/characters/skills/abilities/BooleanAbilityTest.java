package characters.skills.abilities;

import arch.view.ConsoleIOHandler;
import characters.Crewmember;
import characters.classes.CrewmemberClass;
import characters.classes.PilotClass;
import characters.classes.ScoundrelClass;
import org.junit.jupiter.api.Test;
import ship.PlayerShip;
import ship.cockpits.Cockpit;
import ship.modules.CockpitModule;
import ship.modules.WeaponModule;
import ship.weapons.Attack;
import util.rng.MockRandomNumberGenerator;
import util.tests.PlayerShipTestHelper;
import util.tests.ShipWeaponTestHelper;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class BooleanAbilityTest {

	private ConsoleIOHandler consoleIOHandler = new ConsoleIOHandler();

	final String AEROBATICS_EXPERT = "Aerobatics Expert";

	@Test
	public void aerobaticsExpertGivesSecondDodgeOnHit() {
		PlayerShip testShip = PlayerShipTestHelper.populateShipWithCockpitModule(
				PlayerShipTestHelper.makeCrewWithPilot()
		);

		Crewmember pilot = testShip.getCrewmembersOfClass("Pilot").get(0);
		pilot.tryToLevelUpAbility(0);
		pilot.tryToLevelUpAbility(1);
		pilot.tryToLevelUpAbility(2);


		assertTrue(pilot.hasAbility(AEROBATICS_EXPERT));

		CockpitModule mod = testShip.getCockpitModule();
		mod.cockpit(new Cockpit(",",3,900,0.4));
		mod.assignCrewmember(pilot);

		MockRandomNumberGenerator mrng = new MockRandomNumberGenerator();
		// This attack will be a hit
		mrng.loadSingleRandZeroToOneDouble(0.5);
		// The ship will get a second dodge attempt, this will be a success
		mrng.loadSingleRandZeroToOneDouble(0.1);

		int originalShipHullIntegrity = testShip.getRemainingHullIntegrity();
		testShip.sustainFire(ShipWeaponTestHelper.mockEnemyHeavyRocketAttack(), mrng);

		assertTrue(originalShipHullIntegrity == testShip.getRemainingHullIntegrity());
	}

}