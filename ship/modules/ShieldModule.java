package ship.modules;

public class ShieldModule extends ShipModule {

    public final int maxShieldPowerSupported;

    public ShieldModule(int maxShieldPower) {
        maxShieldPowerSupported = maxShieldPower;
    }

    @Override
    public void printInformation() {
        System.out.println(" - SHIELD MODULE ["+name+"]");
        System.out.println("  + SHIELD POWER ["+maxShieldPowerSupported+"]");
    }

}
