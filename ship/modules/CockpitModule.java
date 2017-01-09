package ship.modules;

public class CockpitModule extends MannableShipModule {

    public CockpitModule(String newName) {
        super(newName, ShipModuleType.COCKPIT);
    }

    @Override
    public void printInformation() {
        super.printInformation();
    }
}
