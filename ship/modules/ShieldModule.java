package ship.modules;

import ship.weapons.Attack;
import ship.shields.ShieldSystem;

public class ShieldModule extends MannableShipModule {

    public final int maxShieldPowerSupported;
    private ShieldSystem shields;

    public ShieldModule(String newName, int maxShieldPower) {
        super(newName, ShipModuleType.SHIELD);
        maxShieldPowerSupported = maxShieldPower;
        shields = null;
    }

    public ShieldSystem shields() {
        return shields;
    }

    public void shields(ShieldSystem newShields) {
        shields = newShields;
    }

    @Override
    public void printInformation() {
        super.printInformation();
        if (shields == null) {
            System.out.println("Empty Shield Module ... PWR = ["+maxShieldPowerSupported+"]");
            System.out.println("No shields.");
        } else {
            System.out.println("  + SHIELD POWER ["+maxShieldPowerSupported+"]");
            System.out.println("  + RMNG SHIELDS ["+shields.getRemainingShields()+"]");
            System.out.println("  + MAXM SHIELDS ["+shields.getMaxShields()+"]");

        }
    }

    public Attack shieldAttack(Attack attack) {
        return shields.attemptToShieldAttack(attack);
    }

    public void rechargeShields() {
        shields.updateShieldStatusForNewRound();
    }

}
