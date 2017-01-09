package ship.modules;

import characters.Crewmember;

public abstract class ShipModule {

    public enum ShipModuleType {
        COCKPIT, ENGINE, SHIELD, WEAPON
    }

    protected String name;
    protected final ShipModuleType moduleType;

    public ShipModule(String newName, ShipModuleType newModuleType) {
        name = newName;
        moduleType = newModuleType;
    }

    abstract void printInformation();

    public boolean isMannable() {
        return false;
    }

}
