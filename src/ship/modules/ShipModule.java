package ship.modules;

public abstract class ShipModule {

	public enum ShipModuleType {
		COCKPIT, ENGINE, CARGO, SHIELD, WEAPON
	}

	protected final String _name;
	protected final ShipModuleType _moduleType;
	protected final int _modulePower;

	public ShipModule(String name, ShipModuleType moduleType, int modulePower) {
		_name = name;
		_moduleType = moduleType;
		_modulePower = modulePower;
	}

	abstract void printInformation();

	public boolean isMannable() {
		return false;
	}

	public String getName() {
		return _name;
	}

	public ShipModuleType getModuleType() {
		return _moduleType;
	}

	public int getModulePower() {
		return _modulePower;
	}
}
