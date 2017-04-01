package ship;

import ship.modules.CombatSequenceModule;
import ship.modules.ShipModule;

import java.util.ArrayList;
import java.util.List;

public class CombatSequencer {

	private List<CombatSequenceModule> modules;
	private int modulesCharged;
	private int moduleCount;

	public CombatSequencer(List<CombatSequenceModule> modulesToSequence) {
		modules = modulesToSequence;
		initialiseModules();
		modulesCharged = 0;
		moduleCount = modules.size();
	}

	private void initialiseModules() {
		for (CombatSequenceModule module : modules) {
			module.setEnabledStatus(false);
		}
	}

	public void chargeNextModule() {
		if (modulesFullyCharged() == false) {
			modulesCharged++;
			modules.get(modulesCharged-1).setEnabledStatus(true);
		}
	}

	public List<ShipModule> getModules() {
		ArrayList<ShipModule> copy = new ArrayList<>();
		for (CombatSequenceModule module : modules) {
			copy.add((ShipModule) module);
		}
		return copy;
	}

	private boolean modulesFullyCharged() {
		return getModulesLeftToCharge() == 0;
	}

	private int getModulesLeftToCharge() {
		return moduleCount - modulesCharged;
	}

}