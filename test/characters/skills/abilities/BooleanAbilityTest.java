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
import ship.weapons.HeavyRocket;
import util.rng.MockRandomNumberGenerator;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class BooleanAbilityTest {

	private ConsoleIOHandler consoleIOHandler = new ConsoleIOHandler();

	final String AEROBATICS_EXPERT = "Aerobatics Expert";

	@Test
	public void aerobaticsExpertGivesSecondDodgeOnHit() {
		PlayerShip testShip = populateShipWithCrew(makeCrewWithPilot());

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
		testShip.sustainFire(mockEnemyHeavyRocketAttack(), mrng);

		assertTrue(originalShipHullIntegrity == testShip.getRemainingHullIntegrity());
	}

	private Attack mockEnemyHeavyRocketAttack() {
		WeaponModule enemyModule = new WeaponModule(consoleIOHandler, "EnemyMockWeapon", 5);
		enemyModule.setWeapon(new HeavyRocket());
		while (! enemyModule.isReadyToFire()) enemyModule.decrementTurnsTilWeaponReady();
		return enemyModule.attack(new MockRandomNumberGenerator(0.2));
	}

	private PlayerShip populateShipWithCrew(ArrayList<Crewmember> crewmembers) {
		return new PlayerShip.PlayerShipBuilder(consoleIOHandler, "TestShip",10)
				.crew(crewmembers)
				.cockpitModule(new CockpitModule(consoleIOHandler, "TestCockpitModule", 3))
				.build();
	}

	private ArrayList<Crewmember> makeCrewWithPilot() {
		ArrayList<Crewmember> crew = new ArrayList<>();
		crew.add(newCrewmember(new PilotClass()));
		crew.add(newCrewmember(new ScoundrelClass()));
		return crew;
	}

	private Crewmember newCrewmember(CrewmemberClass crewmemberClass) {
		 return new Crewmember("TestCrewmember", crewmemberClass, 5);
	}

}