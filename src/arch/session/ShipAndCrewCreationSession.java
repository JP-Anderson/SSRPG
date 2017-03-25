package arch.session;

import arch.view.ConsoleIOHandler;
import characters.Crewmember;
import characters.classes.*;
import ship.Crew;
import ship.PlayerShip;
import ship.modules.*;
import ship.shields.BasicShieldsMk2;

import java.util.ArrayList;

public class ShipAndCrewCreationSession extends Session {

	ArrayList<Crewmember> crewList = null;
	private ArrayList<CrewmemberClass> availableClasses;
	private String newName;

	public ShipAndCrewCreationSession(ConsoleIOHandler injectedView) {
		super(injectedView, "ShipAndCrewCreationSession");
		availableClasses = new ArrayList<>();
		availableClasses.add(new PilotClass());
		availableClasses.add(new ScoundrelClass());
		availableClasses.add(new WeaponsExpertClass());
	}

	@Override
	public void run() {
		newName = view.inputHandler.getNonEmptyStringFromUser("What would you like to call your ship?");
		crewList = new ArrayList<>();
		int crewCount = 1;
		final int numberOfStartingCrewmembers = 2;
		for (int i = 0; i < numberOfStartingCrewmembers; i++) {
			view.outputHandler.sendStringToView("You have " + (numberOfStartingCrewmembers - i) + " crewmembers left to pick");
			view.outputHandler.sendStringToView("Which class do you pick for crewmember #" + crewCount + "?");
			view.outputHandler.sendStringToView("Available crewList:");
			CrewmemberClass chosenClass = view.inputHandler.getUserChoiceFromList(availableClasses, "_className");
			availableClasses.remove(chosenClass);
			String chosenName = view.inputHandler.getNonEmptyStringFromUser("What will you call this " + chosenClass._className + "?");
			Crewmember crewmember = new Crewmember(chosenName, chosenClass, 1);
			crewmember.gainExperience(10000);
			crewList.add(crewmember);
			crewCount++;
			view.outputHandler.sendStringToView("");
		}
	}

	public PlayerShip generateNewShip() {
		CockpitModule cockpitModule = new CockpitModule(view, "CockpitModule1", 1);
		EngineModule engineModule = new EngineModule(view, "EnginesModule1", 1, 5);
		ShieldModule shieldModule = new ShieldModule(view, "ShieldsModule1", 1);
		shieldModule.shields(new BasicShieldsMk2());
		CargoBayModule cargoBayModule = new CargoBayModule(view, "CargoBayModule", 0, 20);

		Crew crew = new Crew();
		crew.setCrew(crewList);

		PlayerShip p1 = new PlayerShip.PlayerShipBuilder(view, newName,10)
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
