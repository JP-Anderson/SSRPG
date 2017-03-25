package util.tests;

import arch.view.ConsoleIOHandler;
import characters.Crewmember;
import characters.classes.CrewmemberClass;
import characters.classes.PilotClass;
import characters.classes.ScoundrelClass;
import map.GridMap;
import map.GridPoint;
import ship.Crew;
import ship.PlayerShip;
import ship.modules.CockpitModule;

import java.util.ArrayList;

public class PlayerShipTestHelper extends TestHelper {

	public static PlayerShip populateShipWithCockpitModule(ArrayList<Crewmember> crewmembers) {
		Crew crew = new Crew();
		crew.setCrew(crewmembers);
		return new PlayerShip.PlayerShipBuilder(consoleIOHandler, "TestShip",10)
				.crew(crew)
				.cockpitModule(new CockpitModule(consoleIOHandler, "TestCockpitModule", 3))
				.build();
	}

	public static ArrayList<Crewmember> makeCrewWithPilot() {
		ArrayList<Crewmember> crew = new ArrayList<>();
		crew.add(newCrewmember(new PilotClass()));
		crew.add(newCrewmember(new ScoundrelClass()));
		return crew;
	}

	public static Crewmember newCrewmember(CrewmemberClass crewmemberClass) {
		return new Crewmember("TestCrewmember", crewmemberClass, 5);
	}

	public static PlayerShip getPlayerShipWithScannerAndMap() {
		PlayerShip initialisedShip = generatePlayerShip();
		initialisedShip.initialiseMap(
				new GridPoint(3,1),
				generateMap(5,6)
		);
		return initialisedShip;
	}

	private static PlayerShip generatePlayerShip() {
		return PlayerShipTestHelper.populateShipWithCockpitModule(
				PlayerShipTestHelper.makeCrewWithPilot());
	}

	private static GridMap generateMap(int width, int height) {
		return GridMap.generateGridMap(width, height);
	}

}
