package ship.modules;

import ship.weapons.Attack;
import ship.shields.ShieldSystem;

public class ShieldModule extends MannableShipModule {

	private ShieldSystem shields;

	public ShieldModule(String newName, int powerRequirement) {
		super(newName, ShipModuleType.SHIELD, powerRequirement);
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
			System.out.println("Empty Shield Module ... PWR = [" + _modulePower + "]");
			System.out.println("No shields.");
		} else {
			System.out.println("  + SHIELD POWER [" + _modulePower + "]");
			System.out.println("  + RMNG SHIELDS [" + shields.getRemainingShields() + "]");
			System.out.println("  + MAXM SHIELDS [" + shields.getMaxShields() + "]");

		}
	}

	public Attack shieldAttack(Attack attack) {
		return shields.attemptToShieldAttack(attack);
	}

	public void rechargeShields() {
		shields.updateShieldStatusForNewRound();
	}

}
