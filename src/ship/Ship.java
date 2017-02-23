package ship;

import characters.Crewmember;
import java.util.ArrayList;
import ship.modules.*;
import ship.weapons.ShipWeapon;
import ship.weapons.Attack;

public abstract class Ship {

    public final String name;
    ShipModules modules;
    int maxHullIntegrity;
    int remainingHullIntegrity;
    protected ArrayList<Crewmember> crew;
    int crewCapacity;

    CargoBayModule cargo;

    private boolean isDestroyed;

    protected Ship(GenericShipBuilder builder) {
        name = builder.name;
        modules = builder.generateModules();
        maxHullIntegrity = builder.maxHullIntegrity;
        remainingHullIntegrity = maxHullIntegrity;
        crew = builder.crew;
        crewCapacity = builder.crewCapacity;
    }

    //TODO remove this once AIShip builder has been created and we no longer need public constructors
    Ship(String shipName, ShipModules shipModules) {
        name = shipName;
        modules = shipModules;
        maxHullIntegrity = 500;
        remainingHullIntegrity = maxHullIntegrity;
        isDestroyed = false;
    }

    public void sustainFire(Attack attack) {
        ShieldModule shieldModule = modules.getShieldModule();
        int originalShields = shieldModule.shields().getRemainingShields();
        int originalHull = remainingHullIntegrity;

        System.out.println("Attack: sD " + attack.shieldDamage + " hD " + attack.hullDamage);
        Attack shieldedAttack = shieldModule.shieldAttack(attack);
        System.out.println("Shielded Attack: sD " + shieldedAttack.shieldDamage + " hD " + shieldedAttack.hullDamage);
        takeHullDamage(shieldedAttack);
        System.out.println("Shields: " + originalShields + " => " + shieldModule.shields().getRemainingShields());
        System.out.println("Hull: " + originalHull + " => " + remainingHullIntegrity);
    }

    private void takeHullDamage(Attack shieldedAttack) {
        remainingHullIntegrity = remainingHullIntegrity - shieldedAttack.hullDamage;
        if (remainingHullIntegrity < 0) {
            isDestroyed = true;
        }
    }

    public ArrayList<ShipModule> getModulesInList() {
		return modules.getModulesAsArrayList();
	}

    public void addWeaponModule(int weaponModulePower) {
        modules.addWeaponModule(weaponModulePower);
    }

    public ArrayList<WeaponModule> getWeaponModules() {
        return modules.getWeaponModules();
    }

    public boolean equipWeapon(ShipWeapon newWeapon) {
        return modules.equipWeapon(newWeapon);
    }

    public CargoBayModule getCargoBay() {
        return modules.getCargoBayModule();
    }

    public boolean isDestroyed() {
        return isDestroyed;
    }

    public void rechargeShields() {
        modules.getShieldModule().rechargeShields();
    }

    public static abstract class GenericShipBuilder <B extends GenericShipBuilder<B>> {

		protected String name;
		protected int maxCombinedModulePower;
		protected CockpitModule cockpitModule;
		protected EngineModule engineModule;
		protected ShieldModule shieldModule = null;
		protected CargoBayModule cargoBayModule = null;
		protected ArrayList<WeaponModule> weaponModules = null;
		protected int maxHullIntegrity = 100;
		protected ArrayList<Crewmember> crew = null;
		protected int crewCapacity = 3;

		// ShipModules class is not passed into the builder but is constructed from the modules
		protected ShipModules modules;
		// We need to first add the modules to an ArrayList so we can construct the ShipModules class
        // The use of an ArrayList hopefully makes it easier to add modules in the future.
        private ArrayList<ShipModule> optionalModulesList = new ArrayList<>();

		GenericShipBuilder(String name,
						   int maxCombinedModulePower,
						   CockpitModule cockpitModule,
						   EngineModule engineModule) {
			this.name = name;
			this.maxCombinedModulePower = maxCombinedModulePower;
			this.cockpitModule = cockpitModule;
			this.engineModule = engineModule;
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

		public B crew(ArrayList<Crewmember> crew) {
			this.crew = crew;
			return (B) this;
		}

		public B crewCapacity(int crewCapacity) {
			this.crewCapacity = crewCapacity;
			return (B) this;
		}

		protected ShipModules generateModules() {
			return ShipModules.createInstance(maxCombinedModulePower, cockpitModule, engineModule, optionalModulesList);
        }
	}
}
