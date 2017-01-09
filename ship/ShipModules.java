package ship;

import ship.modules.CockpitModule;
import ship.modules.EngineModule;
import ship.modules.ShieldModule;
import ship.modules.WeaponModule;

import java.util.ArrayList;

public class ShipModules {

    //MODULE POWER LEVEL
    private int currentMaxModulePowerSupported;

    //MANDATORY MODULES
    private CockpitModule cockpitModule;
    private EngineModule engineModule;

    //OPTIONAL MODULES
    private ShieldModule shieldModule;
    private ArrayList<WeaponModule> weaponModules = new ArrayList<>();
    //Communication module (Trader, Scoundrel?)

    //region Getters and Setters
    public int getCurrentMaxModulePowerSupported() {
        return currentMaxModulePowerSupported;
    }

    public void setCurrentMaxModulePowerSupported(int currentMaxModulePowerSupported) {
        this.currentMaxModulePowerSupported = currentMaxModulePowerSupported;
    }

    public CockpitModule getCockpitModule() {
        return cockpitModule;
    }

    public void setCockpitModule(CockpitModule cockpitModule) {
        this.cockpitModule = cockpitModule;
    }

    public EngineModule getEngineModule() {
        return engineModule;
    }

    public void setEngineModule(EngineModule engineModule) {
        this.engineModule = engineModule;
    }

    public ShieldModule getShieldModule() {
        return shieldModule;
    }

    public void setShieldModule(ShieldModule shieldModule) {
        this.shieldModule = shieldModule;
    }

    public ArrayList<WeaponModule> getAllWeaponModules() {
        return weaponModules;
    }

    public int getWeaponModuleCount() { return weaponModules.size(); }

    public WeaponModule getWeaponModule(int weaponModuleListIndex) {
        try {
            return weaponModules.get(weaponModuleListIndex);
        } catch(IndexOutOfBoundsException ie) {
            return null;
        }
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
