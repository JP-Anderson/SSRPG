package ship;

import characters.Crewmember;
import ship.modules.*;
import ship.weapons.ShipWeapon;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ShipModules {

	private int _maxCombinedModulePower;
	private int combinedModulePower = 0;

	//MANDATORY MODULES
	private CockpitModule _cockpitModule;
	private EngineModule _engineModule;

	//OPTIONAL MODULES
	private CargoBayModule _cargoBayModule;
	private ShieldModule _shieldModule;
	private ArrayList<WeaponModule> weaponModules = new ArrayList<>();
	//Communication module (Trader, Scoundrel?)

	private ArrayList<ShipModule> modulesAsArrayList = new ArrayList<>();

	private ShipModules(int maxCombinedModulePower, CockpitModule cockpitModule, EngineModule engineModule) {
		_maxCombinedModulePower = maxCombinedModulePower;
		_cockpitModule = cockpitModule;
		_engineModule = engineModule;
		modulesAsArrayList.add(_cockpitModule);
		modulesAsArrayList.add(_engineModule);
	}

	public static ShipModules createInstance(int maxCombinedModulePower, CockpitModule cockpitModule, EngineModule engineModule) {
		if (cockpitModule == null) throw new IllegalArgumentException("Need a cockpit module");
		if (engineModule == null) throw new IllegalArgumentException("Need an engine module");
		if (maxCombinedModulePower < cockpitModule.getModulePower() + engineModule.getModulePower())
			throw new IllegalStateException("Max supported module power is not high enough for selected cockpit and engine modules");
		return new ShipModules(maxCombinedModulePower, cockpitModule, engineModule);
	}

	public static ShipModules createInstance(int maxPower, CockpitModule cockpitModule, EngineModule engineModule, List<ShipModule> optionalModules) {
		ShipModules shipModules = createInstance(maxPower, cockpitModule, engineModule);
		if (optionalModules != null && optionalModules.size() > 0) shipModules.setUpOptionalModules(optionalModules);
		return shipModules;
	}

	private void setUpOptionalModules(List<ShipModule> optionalModules) {
		combinedModulePower = _cockpitModule.getModulePower() + _engineModule.getModulePower();
		Iterator<ShipModule> i = optionalModules.iterator();
		while (i.hasNext()) {
			ShipModule module = i.next();
			combinedModulePower = combinedModulePower + module.getModulePower();
			if (combinedModulePower > _maxCombinedModulePower)
				throw new IllegalStateException("Optional module " + module.getName() + " has exceeded max module power in Ship.");
			else if (module.getModuleType() == ShipModule.ShipModuleType.WEAPON)
				weaponModules.add((WeaponModule) module);
			else if (module.getModuleType() == ShipModule.ShipModuleType.CARGO)
				_cargoBayModule = (CargoBayModule) module;
			else if (module.getModuleType() == ShipModule.ShipModuleType.SHIELD)
				_shieldModule = (ShieldModule) module;
		}
		modulesAsArrayList.addAll(optionalModules);
	}

	public void placeCrewmemberInModule(Crewmember crewmember, int moduleNumber) {
		ShipModule moduleToMan = getModulesAsArrayList().get(moduleNumber);
		if (moduleToMan instanceof MannableShipModule) {
			((MannableShipModule) moduleToMan).assignCrewmember(crewmember);
		}
	}

	// TODO: will need to modify these setters to check the module type doesn't already exist, and replace it if so
	// TODO: also will need to check the new modules don't exceed the max power
	//region Getters and Setters
	public int getMaxCombinedModulePower() {
		return _maxCombinedModulePower;
	}

	public void setMaxCombinedModulePower(int maxCombinedModulePower) {
		_maxCombinedModulePower = maxCombinedModulePower;
	}

	public CockpitModule getCockpitModule() {
		return _cockpitModule;
	}

	public void setCockpitModule(CockpitModule cockpitModule) {
		_cockpitModule = cockpitModule;
	}

	public EngineModule getEngineModule() {
		return _engineModule;
	}

	public void setEngineModule(EngineModule engineModule) {
		_engineModule = engineModule;
	}

	public CargoBayModule getCargoBayModule() {
		return _cargoBayModule;
	}

	public void setCargoBayModule(CargoBayModule cargoBayModule) {
		this._cargoBayModule = _cargoBayModule;
	}

	public ShieldModule getShieldModule() {
		return _shieldModule;
	}

	public void setShieldModule(ShieldModule shieldModule) {
		_shieldModule = shieldModule;
	}

	public void addWeaponModule(int weaponModulePower) {
		if (combinedModulePower + weaponModulePower <= _maxCombinedModulePower) {
			combinedModulePower += weaponModulePower;
			weaponModules.add(new WeaponModule("WeaponModule", weaponModulePower));
		}
	}

	public ArrayList<WeaponModule> getWeaponModules() {
		return weaponModules;
	}

	public boolean equipWeapon(ShipWeapon newWeapon) {
		for (WeaponModule m : weaponModules) {
			if (m.maxWeaponPowerSupported >= newWeapon.requiredWeaponModulePower
					&& m.getWeapon() == null) {
				System.out.println("This weapon has been equipped.");
				m.setWeapon(newWeapon);
				return true;
			}
		}
		System.out.println("Cannot equip weapon.");
		return false;
	}

	public ArrayList<WeaponModule> getAllWeaponModules() {
		return weaponModules;
	}

	public int getWeaponModuleCount() {
		return weaponModules.size();
	}

	public WeaponModule getWeaponModule(int weaponModuleListIndex) {
		try {
			return weaponModules.get(weaponModuleListIndex);
		} catch (IndexOutOfBoundsException ie) {
			return null;
		}
	}

	public ArrayList<ShipModule> getModulesAsArrayList() {
		return modulesAsArrayList;
	}

	public ArrayList<MannableShipModule> getMannableModulesAsList() {
		ArrayList<MannableShipModule> mannableModules = new ArrayList<>();
		for (ShipModule module : modulesAsArrayList) {
			if (module instanceof MannableShipModule) mannableModules.add((MannableShipModule) module);
		}
		return mannableModules;
	}
	//endregion

	//addModuleFunction
	// need to check we don't already have the module installed and enough power
	// in the case of weapons just need to check we have enough power
	// adding a module is very expensive, much more expensive than switching a modules contents
//
//    Crew and crew modules
//
//    Certain crewmembers obviously man particular modules (weapons expert mans the guns, pilot mans the cockpit)
//
//    Do we need all crew members to require a module? Perhaps certain modules will support multiple crewmembers, therefore meaning we have to make
//    decisions in some cases which crewmember to use these modules on.
//
//    E.g. Trader could be placed in a generic "communication module". Could there be any other types of crew we want in this module?
//
//    some modules are mandatory, we must have it for any ship. however they do not NEED to be manned, ever, and can function in a less efficient
//    automatic mode.
//
//    Engineer - Engine Module (mandatory module)
//    Pilot - Cockpit Module (mandatory module)
//
//    Weapons expert - Weapon module (one man per module, can have multiple weapons module)
//    Shields expert - shields module
//    Trader - Communications Module
//    Scoundrel - Communications module(?)

}
