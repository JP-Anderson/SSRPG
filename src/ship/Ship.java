package ship;

import arch.view.View;
import characters.Crewmember;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import characters.classes.PilotClass;
import characters.skills.abilities.Ability;
import characters.skills.abilities.IntAbility;
import ship.modules.*;
import ship.weapons.ShipWeapon;
import ship.weapons.Attack;
import util.rng.RandomNumberGenerator;

public abstract class Ship {

	protected static View view;
	
	public final String name;
	ShipModules modules;
	int maxHullIntegrity;
	int remainingHullIntegrity;
	Crew crew;

	private boolean isDestroyed;

	protected Ship(View view, GenericShipBuilder builder) {
		Ship.view = view;
		name = builder.name;
		modules = builder.generateModules();
		maxHullIntegrity = builder.maxHullIntegrity;
		remainingHullIntegrity = maxHullIntegrity;
		crew = builder.crew;
		crew.setCrewCapacity(builder.crewCapacity);
	}

	public Crew crew() {
		return crew;
	}

	public void sustainFire(Attack attack, RandomNumberGenerator randomNumberGenerator) {
		if (getCockpitModule() != null && getCockpitModule().canDodgeAttack(randomNumberGenerator)) return;

		ShieldModule shieldModule = (ShieldModule) modules.getShipModule(ShieldModule.class);
		if (shieldModule == null) {
			takeHullDamage(attack);
			return;
		}
		int originalShields = shieldModule.shields().getRemainingShields();
		int originalHull = remainingHullIntegrity;

		Attack shieldedAttack = shieldModule.shieldAttack(attack);
		takeHullDamage(shieldedAttack);

		view.outputHandler.sendStringToView("Attack: sD " + attack.shieldDamage + " hD " + attack.hullDamage);
		view.outputHandler.sendStringToView("Shielded Attack: sD " + shieldedAttack.shieldDamage + " hD " + shieldedAttack.hullDamage);
		view.outputHandler.sendStringToView("Shields: " + originalShields + " => " + shieldModule.shields().getRemainingShields());
		view.outputHandler.sendStringToView("Hull: " + originalHull + " => " + remainingHullIntegrity);
	}

	private void takeHullDamage(Attack shieldedAttack) {
		remainingHullIntegrity = remainingHullIntegrity - shieldedAttack.hullDamage;
		if (remainingHullIntegrity < 0) {
			isDestroyed = true;
		}
	}

	public int getScoreForFirstTurnChance() {
		Crewmember crewmemberManningCockpit = getCockpitModule().getActiveCrewmember();
		if (crewmemberManningCockpit != null) {
			if (crewmemberManningCockpit.crewmemberClass instanceof PilotClass) {
				IntAbility initiative = (IntAbility) crewmemberManningCockpit.tryAndGetAbility("Initiative");
				if (initiative != null) return 3 + initiative.getAbilityLevel();
			}
			return 3;
		}
		return 1;
	}

	public MannableShipModule getModuleMannedBy(Crewmember crewmember) {
		return modules.getModuleCrewmemberIsManning(crewmember);
	}

	public ArrayList<ShipModule> getModulesInList() {
		return modules.getModulesAsArrayList();
	}

	public int getNumberOfModules() {
		return modules.getModulesAsArrayList().size();
	}

	public List<Crewmember> getCrewmembersWithAbilityInSpecificModule(String ability, String desiredModule) {
		Predicate<Crewmember> matchAbilityAndInModule =
				c ->
						c.tryAndGetAbility(ability) != null
								&& this.getModuleMannedBy(c) != null
								&& this.getModuleMannedBy(c).moduleTypeString() == desiredModule;
		return crew.getCrewmembersMatchingPredicate(matchAbilityAndInModule);
	}

	public void placeCrewmemberInModule(Crewmember crewmember, int shipModuleIndex) {
		modules.placeCrewmemberInModule(crewmember, shipModuleIndex);
	}

	public void addWeaponModule(int weaponModulePower) {
		modules.addWeaponModule(weaponModulePower);
	}

	public List<WeaponModule> getWeaponModules() {
		return modules.getWeaponModules();
	}

	public boolean equipWeapon(ShipWeapon newWeapon) {
		return modules.equipWeapon(newWeapon);
	}

	public CargoBayModule getCargoBay() {
		return (CargoBayModule) modules.getShipModule(CargoBayModule.class);
	}

	public CockpitModule getCockpitModule() {
		return (CockpitModule) modules.getShipModule(CockpitModule.class);
	}

	public EngineModule getEngineModule() {
		return (EngineModule) modules.getShipModule(EngineModule.class);
	}

	public int getRemainingHullIntegrity() { return remainingHullIntegrity; }

	public int getMaxHullIntegrity() { return maxHullIntegrity; }

	public boolean isDestroyed() {
		return isDestroyed;
	}

	public void rechargeShields() {
		((ShieldModule) modules.getShipModule(ShieldModule.class)).rechargeShields();
	}

	public static abstract class GenericShipBuilder<B extends GenericShipBuilder<B>> {

		protected View view;
		protected String name;
		protected int maxCombinedModulePower;
		protected CockpitModule cockpitModule;
		protected EngineModule engineModule;
		protected ShieldModule shieldModule = null;
		protected CargoBayModule cargoBayModule = null;
		protected ArrayList<WeaponModule> weaponModules = null;
		protected int maxHullIntegrity = 100;
		protected Crew crew = null;
		protected int crewCapacity = 3;

		// ShipModules class is not passed into the builder but is constructed from the modules
		protected ShipModules modules;
		// We need to first add the modules to an ArrayList so we can construct the ShipModules class
		// The use of an ArrayList hopefully makes it easier to add modules in the future.
		private ArrayList<ShipModule> optionalModulesList = new ArrayList<>();

		GenericShipBuilder(View view, String name, int maxCombinedModulePower) {
			this.view = view;
			this.name = name;
			this.maxCombinedModulePower = maxCombinedModulePower;
			if (cockpitModule == null) addDefaultCockpitModule();
			if (engineModule == null) addDefaultEngineModule();
			if (crew == null) crew = new Crew();
		}

		public B cockpitModule(CockpitModule cockpitModule) {
			this.cockpitModule = cockpitModule;
			return (B) this;
		}

		public B engineModule(EngineModule engineModule) {
			this.engineModule = engineModule;
			return (B) this;
		}

		public B shieldModule(ShieldModule shieldModule) {
			this.shieldModule = shieldModule;
			optionalModulesList.add(shieldModule);
			return (B) this;
		}

		// TODO: think about refactoring this. AIShip might not need this module
		public B cargoBayModule(CargoBayModule cargoBayModule) {
			this.cargoBayModule = cargoBayModule;
			optionalModulesList.add(cargoBayModule);
			return (B) this;
		}

		public B weaponModules(ArrayList<WeaponModule> weaponModules) {
			this.weaponModules = weaponModules;
			optionalModulesList.addAll(weaponModules);
			return (B) this;
		}

		public B maxHullIntegrity(int maxHullIntegrity) {
			this.maxHullIntegrity = maxHullIntegrity;
			return (B) this;
		}

		public B crew(Crew crew) {
			this.crew = crew;
			return (B) this;
		}

		public B crewCapacity(int crewCapacity) {
			this.crewCapacity = crewCapacity;
			return (B) this;
		}

		protected ShipModules generateModules() {
			return ShipModules.createInstance(view, maxCombinedModulePower, cockpitModule, engineModule, optionalModulesList);
		}

		private void addDefaultCockpitModule() {
			cockpitModule = new CockpitModule(view, "CockpitModule1", 1);
		}

		private void addDefaultEngineModule() {
			engineModule = new EngineModule(view, "EnginesModule1", 1, 5);
		}

	}

}
