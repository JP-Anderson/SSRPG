package ship.modules;

import arch.view.View;
import ship.weapons.Attack;
import ship.shields.ShieldSystem;

public class ShieldModule extends MannableShipModule {

	private ShieldSystem shields;

	public ShieldModule(View view, String newName, int powerRequirement) {
		super(view, newName, ShipModuleType.SHIELD, powerRequirement);
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
			view.outputHandler.sendStringToView("Empty Shield Module ... PWR = [" + _modulePower + "]");
			view.outputHandler.sendStringToView("No shields.");
		} else {
			view.outputHandler.sendStringToView("  + SHIELD POWER [" + _modulePower + "]");
			view.outputHandler.sendStringToView("  + RMNG SHIELDS [" + shields.getRemainingShields() + "]");
			view.outputHandler.sendStringToView("  + MAXM SHIELDS [" + shields.getMaxShields() + "]");

		}
	}

	public Attack shieldAttack(Attack attack) {
		return shields.attemptToShieldAttack(attack);
	}

	public void rechargeShields() {
		shields.updateShieldStatusForNewRound();
	}

}
