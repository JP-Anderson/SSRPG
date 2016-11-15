package ship.modules;

import characters.Crewmember;

public abstract class ShipModule {

    public final String name;
    private Crewmember activeCrewmember = null;

    public ShipModule(String moduleName) {
        name = moduleName;
    }

    abstract void printInformation();


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
