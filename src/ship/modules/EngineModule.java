package ship.modules;

import arch.view.View;

public class EngineModule extends MannableShipModule implements CombatSequenceModule {

	public final int fuelEfficiency;

	private boolean enabledForCombat = false;

	public EngineModule(View view, String newName, int powerRequirement, int efficiency) {
		super(view, newName, ShipModuleType.ENGINE, powerRequirement);
		fuelEfficiency = efficiency;
	}

	@Override
	public String moduleTypeString() {
		return "EngineModule";
	}

	@Override
	public void printInformation() {
		super.printInformation();
		view.outputHandler.sendStringToView("  + FUEL EFFICIENCY [" + fuelEfficiency + "]");
	}

	@Override
	public void setEnabledStatus(boolean status) {
		enabledForCombat = status;
	}

	@Override
	public boolean moduleIsCharged() {
		return enabledForCombat;
	}

}
