package ship.modules;

import characters.Crewmember;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ship.PlayerShip;
import ship.cockpits.Cockpit;
import util.rng.MockRandomNumberGenerator;
import util.tests.PlayerShipTestHelper;
import util.tests.ShipWeaponTestHelper;

import static org.junit.jupiter.api.Assertions.*;

class CockpitModuleTest {

	@Test
	public void canDodgeAttackFailsWithoutCrewmemberInCockpit() {
		PlayerShip playerShip = PlayerShipTestHelper.populateShipWithCockpitModule(
				PlayerShipTestHelper.makeCrewWithPilot()
		);

		CockpitModule mod = playerShip.getCockpitModule();
		mod.cockpit(new Cockpit(",",3,900,0.4));

		MockRandomNumberGenerator rng = new MockRandomNumberGenerator();
		rng.loadSingleRandZeroToOneDouble(0.2);

		playerShip.sustainFire(ShipWeaponTestHelper.mockEnemyHeavyRocketAttack(), rng);

		assertFalse(playerShip.getRemainingHullIntegrity() == playerShip.getMaxHullIntegrity());
	}

	@Test 
	public void canDodgeAttackFailsWithoutCockpit() {
		PlayerShip playerShip = PlayerShipTestHelper.populateShipWithCockpitModule(
				PlayerShipTestHelper.makeCrewWithPilot()
		);

		MockRandomNumberGenerator rng = new MockRandomNumberGenerator();
		rng.loadSingleRandZeroToOneDouble(0.2);

		playerShip.sustainFire(ShipWeaponTestHelper.mockEnemyHeavyRocketAttack(), rng);

		assertFalse(playerShip.getRemainingHullIntegrity() == playerShip.getMaxHullIntegrity());
	}

	@Test
	public void canDodgeAttack() {
		PlayerShip playerShip = PlayerShipTestHelper.populateShipWithCockpitModule(
				PlayerShipTestHelper.makeCrewWithPilot()
		);

		CockpitModule mod = playerShip.getCockpitModule();
		mod.cockpit(new Cockpit(",",3,900,0.4));
		Crewmember pilot = playerShip.crew().getCrewmembersOfClass("Pilot").get(0);
		mod.assignCrewmember(pilot);

		MockRandomNumberGenerator rng = new MockRandomNumberGenerator();
		rng.loadSingleRandZeroToOneDouble(0.2);

		playerShip.sustainFire(ShipWeaponTestHelper.mockEnemyHeavyRocketAttack(), rng);

		assertTrue(playerShip.getRemainingHullIntegrity() == playerShip.getMaxHullIntegrity());
	}


	@Test
	public void canDodgeAttackFailsIfRollIsAboveDodgeChance() {
		PlayerShip playerShip = PlayerShipTestHelper.populateShipWithCockpitModule(
				PlayerShipTestHelper.makeCrewWithPilot()
		);

		CockpitModule mod = playerShip.getCockpitModule();
		mod.cockpit(new Cockpit(",",3,900,0.4));
		Crewmember pilot = playerShip.crew().getCrewmembersOfClass("Pilot").get(0);
		mod.assignCrewmember(pilot);

		MockRandomNumberGenerator rng = new MockRandomNumberGenerator();
		rng.loadSingleRandZeroToOneDouble(0.8);

		playerShip.sustainFire(ShipWeaponTestHelper.mockEnemyHeavyRocketAttack(), rng);

		assertFalse(playerShip.getRemainingHullIntegrity() == playerShip.getMaxHullIntegrity());
	}

}