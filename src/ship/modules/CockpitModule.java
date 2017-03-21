package ship.modules;

import arch.view.View;

public class CockpitModule extends MannableShipModule {

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
}
