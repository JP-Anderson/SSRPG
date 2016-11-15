package ship.modules;

public class EngineModule extends ShipModule {

    public final int fuelEfficiency;

    public EngineModule(String engineName, int efficiency) {
        super(engineName);
        fuelEfficiency = efficiency;
    }

    @Override
    public void printInformation() {
        System.out.println(" - ENGINE MODULE ["+name+"]");
        System.out.println("  + FUEL EFFICIENCY ["+fuelEfficiency+"]");
    }

}
