package ship;

import arch.view.View;
import characters.Crewmember;
import ship.modules.*;
import ship.weapons.ShipWeapon;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class ShipModules {

	private View view;
	private int _maxCombinedModulePower;
	private ArrayList<ShipModule> modulesAsArrayList = new ArrayList<>();

	private ShipModules(View view,
						int maxCombinedModulePower,
						CockpitModule cockpitModule,
						EngineModule engineModule) {
		this.view = view;
		_maxCombinedModulePower = maxCombinedModulePower;
		modulesAsArrayList.add(cockpitModule);
		modulesAsArrayList.add(engineModule);
	}

	public static ShipModules createInstance(View view,
											 int maxCombinedModulePower,
											 CockpitModule cockpitModule,
											 EngineModule engineModule) {
		if (cockpitModule == null) throw new IllegalArgumentException("Need a cockpit module");
		if (engineModule == null) throw new IllegalArgumentException("Need an engine module");
		if (maxCombinedModulePower < cockpitModule.getModulePower() + engineModule.getModulePower())
			throw new IllegalStateException("Max supported module power is not high enough for selected cockpit and engine modules");
		return new ShipModules(view, maxCombinedModulePower, cockpitModule, engineModule);
	}

	public static ShipModules createInstance(View view,
											 int maxPower,
											 CockpitModule cockpitModule,
											 EngineModule engineModule,
											 List<ShipModule> optionalModules) {
		ShipModules shipModules = createInstance(view, maxPower, cockpitModule, engineModule);
		if (optionalModules != null && optionalModules.size() > 0) shipModules.setUpOptionalModules(optionalModules);
		return shipModules;
	}

	private void setUpOptionalModules(List<ShipModule> optionalModules) {
		int combinedModulePower = getShipModule(CockpitModule.class).getModulePower()
								+ getShipModule(EngineModule.class).getModulePower();
		Iterator<ShipModule> i = optionalModules.iterator();
		while (i.hasNext()) {
			ShipModule module = i.next();
			combinedModulePower = combinedModulePower + module.getModulePower();
			if (combinedModulePower > _maxCombinedModulePower)
				throw new IllegalStateException("Optional module " + module.getName() + " has exceeded max module power in Ship.");
		}
		modulesAsArrayList.addAll(optionalModules);
	}

	public int getCombinedModulePower() {
		return modulesAsArrayList.stream().mapToInt(m -> m.getModulePower()).sum();
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

	public ShipModule getShipModule(Class desiredModule) {
		return getExistingModuleOfType(desiredModule);
	}

	public void setShipModule(ShipModule newModule) {
		ShipModule existingModule = getExistingModuleOfType(newModule.getClass());
		if (existingModule != null) {
			replaceOldModule(newModule, existingModule);
		} else {
			addNewModule(newModule);
		}
	}

	private ShipModule getExistingModuleOfType(Class module) {
		return modulesAsArrayList
				.stream()
				.filter(module :: isInstance)
				.findAny()
				.orElse(null);
	}

	private void replaceOldModule(ShipModule newModule, ShipModule oldModule) {
		int powerAfterReplacement = getCombinedModulePower() - oldModule.getModulePower() + newModule.getModulePower();
		if (powerAfterReplacement <= _maxCombinedModulePower) {
			modulesAsArrayList.remove(oldModule);
			modulesAsArrayList.add(newModule);
		}
	}

	private void addNewModule(ShipModule module) {
		if (getCombinedModulePower() + module.getModulePower() <= _maxCombinedModulePower) {
			modulesAsArrayList.add(module);
		}
	}

	// This function takes any type of ShipModule, and removes any modules that exist of this type
	// If replacing a module, don't call this function, use setRelevantModule
	// TODO multiple WeaponModules can be added, until adding a new module exceeds the max supported power
	public void removeExistingModule(Class module) {
		ShipModule moduleToRemove = modulesAsArrayList
				.stream()
				.filter(module.getClass() :: isInstance)
				.findAny()
				.orElse(null);
		if (moduleToRemove != null) modulesAsArrayList.remove(moduleToRemove);
	}


	public void addWeaponModule(int weaponModulePower) {
		if (getCombinedModulePower() + weaponModulePower <= _maxCombinedModulePower) {
			modulesAsArrayList.add(new WeaponModule(view, "WeaponModule", weaponModulePower));
		}
	}

	public List<WeaponModule> getWeaponModules() {
		return modulesAsArrayList
				.stream()
				.filter(WeaponModule.class :: isInstance)
				.map(WeaponModule.class :: cast)
				.collect(Collectors.toList());
	}

	public boolean equipWeapon(ShipWeapon newWeapon) {
		for (WeaponModule m : getWeaponModules()) {
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

	public int getWeaponModuleCount() {
		return getWeaponModules().size();
	}

	public WeaponModule getWeaponModule(int weaponModuleListIndex) {
		try {
			return getWeaponModules().get(weaponModuleListIndex);
		} catch (IndexOutOfBoundsException ie) {
			return null;
		}
	}

	public MannableShipModule getModuleCrewmemberIsManning(Crewmember crewmember) {
		return modulesAsArrayList
				.stream()
				.filter(MannableShipModule.class :: isInstance)
				.map(MannableShipModule.class :: cast)
				.filter(m -> m.getActiveCrewmember() == crewmember)
				.findFirst()
				.orElse(null);
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
