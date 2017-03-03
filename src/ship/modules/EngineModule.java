package ship.modules;

import arch.view.View;

public class EngineModule extends MannableShipModule {

	public final int fuelEfficiency;

	public EngineModule(View view, String newName, int powerRequirement, int efficiency) {
		super(view, newName, ShipModuleType.ENGINE, powerRequirement);
		fuelEfficiency = efficiency;
	}

	@Override
	public void printInformation() {
		super.printInformation();
		view.outputHandler.sendStringToView("  + FUEL EFFICIENCY [" + fuelEfficiency + "]");
	}

}
