package ship.modules;

import arch.view.View;
import characters.Crewmember;
import ship.cockpits.Cockpit;
import util.rng.RandomNumberGenerator;

public class CockpitModule extends MannableShipModule {

	private Cockpit cockpit = null;

	public CockpitModule(View view, String name, int powerRequirement) {
		super(view, name, ShipModuleType.COCKPIT, powerRequirement, false);
	}

	@Override
	public String moduleTypeString() {
		return "CockpitModule";
	}

	@Override
	public void printInformation() {
		super.printInformation();
	}

	public void cockpit(Cockpit cockpit) {
		this.cockpit = cockpit;
	}

	public Cockpit cockpit() {
		return cockpit;
	}

	public double cockpitDodgeChance() {
		return cockpit != null ? cockpit.baseDodgeChance : 0.0;
	}

	public boolean canDodgeAttack(RandomNumberGenerator randomNumberGenerator) {
		if (cockpit == null) return false;
		Crewmember pilot = getActiveCrewmember();
		if (pilot == null) return false;
		double baseDodgeChance = cockpitDodgeChance();
		if (dodge(randomNumberGenerator, baseDodgeChance)) return true;
		else if (pilot.hasAbility("Aerobatics Expert")) {
			//Second dodge roll with Aerobatics Expert
			return dodge(randomNumberGenerator, baseDodgeChance);
		}
		return false;
	}

	private boolean dodge(RandomNumberGenerator randomNumberGenerator, double dodgeChance) {
		if (randomNumberGenerator.randZeroToOne() <= dodgeChance) return true;
		return false;
	}
}
