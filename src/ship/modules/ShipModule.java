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
	protected final boolean needsToBeSequencedForCombat;


	public ShipModule(View view, String name, ShipModuleType moduleType, int modulePower, boolean needsToBeSequencedForCombat) {
		this.view = view;
		this.name = name;
		this.moduleType = moduleType;
		this.modulePower = modulePower;
		this.needsToBeSequencedForCombat = needsToBeSequencedForCombat;
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

	public boolean needsToBeSequencedForCombat() {
		return needsToBeSequencedForCombat;
	}
}
