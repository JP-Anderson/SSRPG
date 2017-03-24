package ship.modules;

import arch.view.View;
import characters.Crewmember;

public abstract class MannableShipModule extends ShipModule {

	public abstract String moduleTypeString();

	public MannableShipModule(View view, String name, ShipModuleType moduleType, int modulePower, boolean needsToBeSequencedForCombat) {
		super(view, name, moduleType, modulePower, needsToBeSequencedForCombat);
	}

	@Override
	public void printInformation() {
		view.outputHandler.sendStringToView(" - MODULE [" + name + "]");
		if (activeCrewmember == null) {
			view.outputHandler.sendStringToView("  + Module unmanned");
		} else {
			view.outputHandler.sendStringToView("  + Manned: " + activeCrewmember.name);
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
