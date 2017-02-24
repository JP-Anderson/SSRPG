package ship.modules;

public class CockpitModule extends MannableShipModule {

	public CockpitModule(String name, int powerRequirement) {
		super(name, ShipModuleType.COCKPIT, powerRequirement);
	}

	@Override
	public void printInformation() {
		super.printInformation();
	}
}
