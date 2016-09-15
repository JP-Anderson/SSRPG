public class EngineModule implements ShipModule {

    public final String name;
    public final int fuelEfficiency;

    public EngineModule(String engineName, int efficiency) {
        name = engineName;
        fuelEfficiency = efficiency;
    }

    @Override
    public void printInformation() {
        System.out.println("> ENGINE MODULE ["+name+"]");
        System.out.println(">> FUEL EFFICIENCY ["+fuelEfficiency+"]");
    }

}
