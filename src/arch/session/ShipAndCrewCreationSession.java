package arch.session;

import characters.Crewmember;
import characters.classes.*;
import ship.PlayerShip;
import ship.modules.*;
import ship.shields.BasicShieldsMk2;
import arch.view.ConsoleInputHandler;

import java.util.ArrayList;

public class ShipAndCrewCreationSession extends Session {

	ArrayList<Crewmember> crew = null;
	private ArrayList<CrewmemberClass> availableClasses;
	private String newName;

	public ShipAndCrewCreationSession() {
		super("ShipAndCrewCreationSession");
		availableClasses = new ArrayList<>();
		availableClasses.add(new PilotClass());
		availableClasses.add(new ScoundrelClass());
		availableClasses.add(new WeaponsExpertClass());
	}

	@Override
	public void run() {
		newName = ConsoleInputHandler.getNonEmptyStringFromUser("What would you like to call your ship?");
		crew = new ArrayList<>();
		int crewCount = 1;
		final int numberOfStartingCrewmembers = 2;
		for (int i = 0; i < numberOfStartingCrewmembers; i++) {
			System.out.println("You have " + (numberOfStartingCrewmembers - i) + " crewmembers left to pick");
			System.out.println("Which class do you pick for crewmember #" + crewCount + "?");
			System.out.println("Available crew:");
			CrewmemberClass chosenClass = ConsoleInputHandler.getUserChoiceFromList(availableClasses, "_className");
			availableClasses.remove(chosenClass);
			String chosenName = ConsoleInputHandler.getNonEmptyStringFromUser("What will you call this " + chosenClass._className + "?");
			crew.add(new Crewmember(chosenName, chosenClass, 1));
			crewCount++;
			System.out.println();
		}
	}

	public PlayerShip generateNewShip() {
		CockpitModule cockpitModule = new CockpitModule("CockpitModule1", 1);
		EngineModule engineModule = new EngineModule("EnginesModule1", 1, 5);
		ShieldModule shieldModule = new ShieldModule("ShieldsModule1", 1);
		shieldModule.shields(new BasicShieldsMk2());
		CargoBayModule cargoBayModule = new CargoBayModule("CargoBayModule", 0, 20);

		PlayerShip p1 = new PlayerShip.PlayerShipBuilder(newName,10)
				.cockpitModule(cockpitModule)
				.engineModule(engineModule)
				.shieldModule(shieldModule)
				.cargoBayModule(cargoBayModule)
				.crewCapacity(4)
				.crew(crew)
				.fuelCapacity(100)
				.money(20000)
				.scannerStrength(7)
				.build();
		return p1;
	}

}
