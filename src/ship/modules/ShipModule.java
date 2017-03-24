package ship.modules;

import arch.view.View;

public abstract class ShipModule {

	public enum ShipModuleType {
		COCKPIT, ENGINE, CARGO, SHIELD, WEAPON
	}

	View view;

	protected final String name;
	protected final ShipModuleType moduleType;
	protected final int modulePower;


	public ShipModule(View view, String name, ShipModuleType moduleType, int modulePower) {
		this.view = view;
		this.name = name;
		this.moduleType = moduleType;
		this.modulePower = modulePower;
	}

	abstract void printInformation();

	public boolean isMannable() {
		return false;
	}

	public String getName() {
		return name;
	}

	public ShipModuleType getModuleType() {
		return moduleType;
	}

	public int getModulePower() {
		return modulePower;
	}

}
