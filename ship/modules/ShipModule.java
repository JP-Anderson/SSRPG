package ship.modules;

import characters.Crewmember;

public abstract class ShipModule {

    protected String name;
    protected Crewmember activeCrewmember = null;

    public ShipModule() {
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
