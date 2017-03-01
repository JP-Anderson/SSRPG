package ship.modules;

public class EngineModule extends MannableShipModule {

	public final int fuelEfficiency;

	public EngineModule(String newName, int powerRequirement, int efficiency) {
		super(newName, ShipModuleType.ENGINE, powerRequirement);
		fuelEfficiency = efficiency;
	}

	@Override
	public void printInformation() {
		super.printInformation();
		System.out.println("  + FUEL EFFICIENCY [" + fuelEfficiency + "]");
	}

}