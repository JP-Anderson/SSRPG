package ship.modules;

import characters.Crewmember;

public abstract class MannableShipModule extends ShipModule {

	public MannableShipModule(String name, ShipModuleType moduleType, int modulePower) {
		super(name, moduleType, modulePower);
	}

	@Override
	public void printInformation() {
		System.out.println(" - MODULE [" + _name + "]");
		if (activeCrewmember == null) {
			System.out.println("  + Module unmanned");
		} else {
			System.out.println("  + Manned: " + activeCrewmember.name);
		}
	}

	@Override
	public boolean isMannable() {
		return true;
	}

	protected Crewmember activeCrewmember = null;

	public void assignCrewmember(Crewmember crewmember) {
		if (activeCrewmember != null) {
			activeCrewmember.removeFromCurrentMannedModule();
		}
		activeCrewmember = crewmember;
		crewmember.setMannedModule(this);
	}

	public Crewmember getActiveCrewmember() {
		return activeCrewmember;
	}

	public void setActiveCrewmember(Crewmember crewmember) {
		activeCrewmember = crewmember;
	}

	public void removeCrewmember() {
		if (activeCrewmember != null) {
			activeCrewmember.removeFromCurrentMannedModule();
			activeCrewmember = null;
		}
	}
}
