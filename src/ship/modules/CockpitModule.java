package ship.modules;

import arch.view.View;
import ship.cockpits.Cockpit;

public class CockpitModule extends MannableShipModule {

	private Cockpit cockpit = null;

	public CockpitModule(View view, String name, int powerRequirement) {
		super(view, name, ShipModuleType.COCKPIT, powerRequirement);
	}

	@Override
	public String moduleTypeString() {
		return "CockpitModule";
	}

	@Override
	public void printInformation() {
		super.printInformation();
	}

	public void cockpit(Cockpit cockpit) {
		this.cockpit = cockpit;
	}

	public Cockpit cockpit() {
		return cockpit;
	}

	public double cockpitDodgeChance() {
		return cockpit != null ? cockpit.baseDodgeChance : 0.0;
	}
}
