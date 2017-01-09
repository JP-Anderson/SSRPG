package ship.modules;

import characters.Crewmember;

public abstract class MannableShipModule extends ShipModule {

    public MannableShipModule(String newName, ShipModuleType newModuleType) {
        super(newName, newModuleType);
    }

    @Override
    public void printInformation() {
        System.out.println(" - MODULE ["+name+"]");
        if (activeCrewmember == null) {
            System.out.println("  + Module unmanned");
        } else {
            System.out.println("Manned: " + activeCrewmember.name);
        }
    }

    @Override
    public boolean isMannable() {
        return true;
    }

    protected Crewmember activeCrewmember = null;

    void assignCrewmember(Crewmember crewmember) {
        activeCrewmember = crewmember;
    }

    Crewmember getActiveCrewmember() {
        return activeCrewmember;
    }

    void removeCrewmember() {
        activeCrewmember = null;
    }

}
