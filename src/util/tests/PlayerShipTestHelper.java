package util.tests;

import arch.view.ConsoleIOHandler;
import characters.Crewmember;
import characters.classes.CrewmemberClass;
import characters.classes.PilotClass;
import characters.classes.ScoundrelClass;
import ship.PlayerShip;
import ship.modules.CockpitModule;

import java.util.ArrayList;

public class PlayerShipTestHelper {

	private static ConsoleIOHandler consoleIOHandler = new ConsoleIOHandler();

	public static PlayerShip populateShipWithCockpitModule(ArrayList<Crewmember> crewmembers) {
		return new PlayerShip.PlayerShipBuilder(consoleIOHandler, "TestShip",10)
				.crew(crewmembers)
				.cockpitModule(new CockpitModule(consoleIOHandler, "TestCockpitModule", 3))
				.build();
	}

	public static ArrayList<Crewmember> makeCrewWithPilot() {
		ArrayList<Crewmember> crew = new ArrayList<>();
		crew.add(newCrewmember(new PilotClass()));
		crew.add(newCrewmember(new ScoundrelClass()));
		return crew;
	}

	private static Crewmember newCrewmember(CrewmemberClass crewmemberClass) {
		return new Crewmember("TestCrewmember", crewmemberClass, 5);
	}

}
