package ship.modules;

import arch.view.View;
import ship.weapons.Attack;
import ship.shields.ShieldSystem;

public class ShieldModule extends MannableShipModule implements CombatSequenceModule {

	private ShieldSystem shields;

	private boolean enabledForCombat = false;

	public ShieldModule(View view, String newName, int powerRequirement) {
		super(view, newName, ShipModuleType.SHIELD, powerRequirement);
		shields = null;
	}

	@Override
	public String moduleTypeString() {
		return "ShieldModule";
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
			view.outputHandler.sendStringToView("Empty Shield Module ... PWR = [" + modulePower + "]");
			view.outputHandler.sendStringToView("No shields.");
		} else {
			view.outputHandler.sendStringToView("  + SHIELD POWER [" + modulePower + "]");
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

	@Override
	public void setEnabledStatus(boolean status) {
		enabledForCombat = status;
	}

	@Override
	public boolean moduleIsCharged() {
		return enabledForCombat;
	}

}
