package ship;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ship.modules.*;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class ShipModulesTest {

	@Test
	public void exceptionThrownForMandatoryModulesExceedingPowerRequirement() {
		CockpitModule cockpitModule = cockpitModule(4);
		EngineModule engineModule = engineModule(7);
		assertThrows(IllegalStateException.class,
				() -> ShipModules.createInstance(10, cockpitModule, engineModule));
	}

	@Test
	public void shipModuleCreationWorksWithModulesEqualToMaxPowerRequirement() {
		CockpitModule cockpitModule = cockpitModule(4);
		EngineModule engineModule = engineModule(6);
		assertNotNull(ShipModules.createInstance(10, cockpitModule, engineModule));
	}

	@Test
	public void exceptionThrownForOptionalModuleExceedingMaxPowerRequirement() {
		CockpitModule cockpitModule = cockpitModule(3);
		EngineModule engineModule = engineModule(6);
		WeaponModule weaponModule = weaponModule(3);
		ArrayList<ShipModule> optionalModules = new ArrayList<>();
		optionalModules.add(weaponModule);
		assertThrows(IllegalStateException.class,
				() -> ShipModules.createInstance(10, cockpitModule, engineModule, optionalModules));
	}

	@Test
	public void shipModuleCreationWorksForSingleOptionalModule() {
		CockpitModule cockpitModule = cockpitModule(3);
		EngineModule engineModule = engineModule(6);
		WeaponModule weaponModule = weaponModule(3);
		ArrayList<ShipModule> optionalModules = new ArrayList<>();
		optionalModules.add(weaponModule);
		assertNotNull(ShipModules.createInstance(13, cockpitModule, engineModule, optionalModules));
	}

	@Test
	public void exceptionThrownForMultipleOptionalModulesExceedingMaxPower() {
		CockpitModule cockpitModule = cockpitModule(3);
		EngineModule engineModule = engineModule(6);
		WeaponModule weaponModule = weaponModule(3);
		WeaponModule weaponModule2 = weaponModule(5);
		ShieldModule shieldModule = shieldModule(6);
		ArrayList<ShipModule> optionalModules = new ArrayList<>();
		optionalModules.add(weaponModule);
		optionalModules.add(weaponModule2);
		optionalModules.add(shieldModule);
		assertThrows(IllegalStateException.class,
				() -> ShipModules.createInstance(20, cockpitModule, engineModule, optionalModules));
	}

	@Test
	public void shipModuleCreationWorksForMultipleOptionalModulesBelowMaxPower() {
		CockpitModule cockpitModule = cockpitModule(3);
		EngineModule engineModule = engineModule(6);
		WeaponModule weaponModule = weaponModule(3);
		WeaponModule weaponModule2 = weaponModule(5);
		ShieldModule shieldModule = shieldModule(6);
		ArrayList<ShipModule> optionalModules = new ArrayList<>();
		optionalModules.add(weaponModule);
		optionalModules.add(weaponModule2);
		optionalModules.add(shieldModule);
		assertNotNull(ShipModules.createInstance(23, cockpitModule, engineModule, optionalModules));
	}

	@Test
	public void getCombinedModulePower() {
		CockpitModule cockpitModule = cockpitModule(2);
		EngineModule engineModule = engineModule(6);
		ShipModules modules = ShipModules.createInstance(10, cockpitModule, engineModule);
		assertEquals(6, modules.getCombinedModulePower());
	}

	@Test
	public void addShieldModule() {
		CockpitModule cockpitModule = cockpitModule(2);
		EngineModule engineModule = engineModule(6);
		ShipModules modules = ShipModules.createInstance(20, cockpitModule, engineModule);
		assertNull(modules.getShieldModule());
		ShieldModule newShieldModule = shieldModule(5);
		modules.setShieldModule(newShieldModule);
		assertNotNull(modules.getShieldModule());
		assertEquals(13, modules.getCombinedModulePower());
	}

	private CockpitModule cockpitModule(int powerRequirement) {
		return new CockpitModule("TestCockpitModule", powerRequirement);
	}

	private EngineModule engineModule(int powerRequirement) {
		return new EngineModule("TestEnginesModule", powerRequirement, 5);
	}

	private WeaponModule weaponModule(int powerRequirement) {
		return new WeaponModule("TestWeaponModule", powerRequirement);
	}

	private ShieldModule shieldModule(int powerRequirement) {
		return new ShieldModule("TestShield", powerRequirement);
	}



}