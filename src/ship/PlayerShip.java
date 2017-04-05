package ship;

import arch.view.View;
import map.GridMap;
import map.GridPoint;
import characters.Crewmember;
import ship.modules.*;

import java.util.ArrayList;

public class PlayerShip extends Ship {

	private int fuelCapacity;
	private int remainingFuel;
	private int scannerStrength;
	private Scanner scanner;
	private GridPoint location;
	private int money;

	public void setMoney(int m) {
		money = m;
	}

	public int getMoney() {
		return money;
	}

	private PlayerShip(View view, PlayerShipBuilder builder) {
		super(view, builder);
		fuelCapacity = builder.fuelCapacity;
		remainingFuel = fuelCapacity;
		scannerStrength = builder.scannerStrength;
		money = builder.money;
	}

	public void initialiseCrew(ArrayList<Crewmember> newCrew) {
		crew.setCrew(newCrew);
	}

	public void initialiseMap(GridPoint startLocation, GridMap map) {
		location = startLocation;
		scanner = Scanner.getScanner(scannerStrength, map, startLocation);
	}

	public GridPoint getLocation() {
		return location;
	}

	public void setLocation(GridPoint gridPoint) {
		location = gridPoint;
		scanner.setShipLocation(gridPoint);
	}

	public void shipStatus() {
		CargoBayModule cargo = (CargoBayModule) modules.getShipModule(CargoBayModule.class);
		view.outputHandler.sendStringToView("PlayerShip status:");
		view.outputHandler.sendStringToView(crew.size() + "/" + crew().getCrewCapacity() + " crew");
		view.outputHandler.sendStringToView(" CREDS total: " + money);
		view.outputHandler.sendStringToView(" Remaining Fuel: " + remainingFuel + "/" + fuelCapacity);
		// TODO: cargo bay is now a module, so need to put this in the module print information function
		view.outputHandler.sendStringToView(" Cargo Bay: " + cargo.getFilledCapacity() + " units out of " + cargo.getMaxCapacity());
		view.outputHandler.sendStringToView(" Modules: ");
		((EngineModule) modules.getShipModule(EngineModule.class)).printInformation();
		((ShieldModule) modules.getShipModule(ShieldModule.class)).printInformation();
	}

	public boolean travel(GridPoint gridPoint, int distance) {
		location = gridPoint;
		scanner.setShipLocation(gridPoint);
		int fuelCost = distance * ((EngineModule) modules.getShipModule(EngineModule.class)).fuelEfficiency;
		if (fuelCost <= remainingFuel) {
			view.outputHandler.sendStringToView("Used " + fuelCost + " fuel.");
			remainingFuel = remainingFuel - fuelCost;
			return true;
		}
		return false;
	}

	public void scan() {
		scanner.scan();
	}
	
	public Scanner getScanner() {
		return scanner;
	}

	public int getFuelCapacity() {
		return fuelCapacity;
	}

	public int getRemainingFuel() {
		return remainingFuel;
	}

	public void setRemainingFuel(int newFuel) {
		remainingFuel = newFuel;
	}

	public ArrayList<MannableShipModule> getMannableShipModulesAsList() {
		return modules.getMannableModulesAsList();
	}

	public void giveExperienceToAllCrewmembers(int xpReward) {
		for (Crewmember crewmember : crew.getCrew()) {
			crewmember.gainExperience(xpReward);
		}
	}

	public boolean hasSpaceInCrew() {
		return crew.size() < crew().getCrewCapacity();
	}

	public double getContrabandCargoRatio() {
		CargoBayModule cargoBayModule = (CargoBayModule) modules.getShipModule(CargoBayModule.class);
		if (cargoBayModule != null) return cargoBayModule.contrabandCargoRatio();
		// TODO : need to handle the event of no cargo bay module, probably just skip the event
		return 0;
	}

	public static class PlayerShipBuilder extends Ship.GenericShipBuilder<PlayerShipBuilder> {

		public PlayerShipBuilder(View view, String name, int maxCombinedModulePower) {
			super(view, name, maxCombinedModulePower);
		}

		protected int fuelCapacity = 100;
		protected int scannerStrength = 7;
		protected int money = 20000;

		public PlayerShipBuilder fuelCapacity(int maxFuelCapacity) {
			this.fuelCapacity = maxFuelCapacity;
			return this;
		}

		public PlayerShipBuilder scannerStrength(int scannerStrength) {
			this.scannerStrength = scannerStrength;
			return this;
		}

		public PlayerShipBuilder money(int money) {
			this.money = money;
			return this;
		}

		public PlayerShip build() {
			return new PlayerShip(view, this);
		}

	}

}
