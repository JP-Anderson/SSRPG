package ship.modules;

public class EngineModule extends ShipModule {

    public final int fuelEfficiency;

    public EngineModule(int efficiency) {
        fuelEfficiency = efficiency;
    }

    @Override
    public void printInformation() {
        System.out.println(" - ENGINE MODULE ["+name+"]");
        System.out.println("  + FUEL EFFICIENCY ["+fuelEfficiency+"]");
    }

}
