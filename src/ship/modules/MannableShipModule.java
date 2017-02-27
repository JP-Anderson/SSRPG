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

	public boolean assignCrewmember(Crewmember crewmember) {
		if (crewmember.isManningAModule() || activeCrewmember != null) {
			return false;
		}
		activeCrewmember = crewmember;
		activeCrewmember.manningModule();
		return true;
	}

	public Crewmember getActiveCrewmember() {
		return activeCrewmember;
	}

	public void removeCrewmember() {
		if (activeCrewmember != null) {
			activeCrewmember.notManningModule();
			activeCrewmember = null;
		}
	}
}
