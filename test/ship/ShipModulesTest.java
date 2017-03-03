package ship;

import arch.view.ConsoleIOHandler;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import ship.modules.*;

import java.util.ArrayList;
import java.util.Arrays;


class ShipModulesTest {
	
	private static ConsoleIOHandler consoleIOHandler = new ConsoleIOHandler();

	@Test
	public void exceptionThrownForMandatoryModulesExceedingPowerRequirement() {
		CockpitModule cockpitModule = cockpitModule(4);
		EngineModule engineModule = engineModule(7);
		assertThrows(IllegalStateException.class,
				() -> ShipModules.createInstance(consoleIOHandler, 10, cockpitModule, engineModule));
	}

	@Test
	public void shipModuleCreationWorksWithModulesEqualToMaxPowerRequirement() {
		CockpitModule cockpitModule = cockpitModule(4);
		EngineModule engineModule = engineModule(6);
		assertNotNull(ShipModules.createInstance(consoleIOHandler, 10, cockpitModule, engineModule));
	}

	@Test
	public void exceptionThrownForOptionalModuleExceedingMaxPowerRequirement() {
		CockpitModule cockpitModule = cockpitModule(3);
		EngineModule engineModule = engineModule(6);
		WeaponModule weaponModule = weaponModule(3);
		ArrayList<ShipModule> optionalModules = new ArrayList<>();
		optionalModules.add(weaponModule);
		assertThrows(IllegalStateException.class,
				() -> ShipModules.createInstance(consoleIOHandler, 10, cockpitModule, engineModule, optionalModules));
	}

	@Test
	public void shipModuleCreationWorksForSingleOptionalModule() {
		CockpitModule cockpitModule = cockpitModule(3);
		EngineModule engineModule = engineModule(6);
		WeaponModule weaponModule = weaponModule(3);
		ArrayList<ShipModule> optionalModules = new ArrayList<>();
		optionalModules.add(weaponModule);
		assertNotNull(ShipModules.createInstance(consoleIOHandler, 13, cockpitModule, engineModule, optionalModules));
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
				() -> ShipModules.createInstance(consoleIOHandler, 20, cockpitModule, engineModule, optionalModules));
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
		assertNotNull(ShipModules.createInstance(consoleIOHandler, 23, cockpitModule, engineModule, optionalModules));
	}

	@Test
	public void getCombinedModulePower() {
		CockpitModule cockpitModule = cockpitModule(2);
		EngineModule engineModule = engineModule(6);
		ShipModules modules = ShipModules.createInstance(consoleIOHandler, 10, cockpitModule, engineModule);
		assertEquals(8, modules.getCombinedModulePower());
	}

	@Test
	public void tryToAddNewShieldModuleWithEnoughFreePower() {
		CockpitModule cockpitModule = cockpitModule(2);
		EngineModule engineModule = engineModule(6);
		ShipModules modules = ShipModules.createInstance(consoleIOHandler, 20, cockpitModule, engineModule);
		assertNull(modules.getShipModule(ShieldModule.class));
		ShieldModule newShieldModule = shieldModule(5);
		modules.setShipModule(newShieldModule);
		assertEquals(newShieldModule, modules.getShipModule(ShieldModule.class));
		assertEquals(13, modules.getCombinedModulePower());
	}

	@Test
	public void tryToAddNewShieldModuleWhichExceedsFreePower() {
		CockpitModule cockpitModule = cockpitModule(2);
		EngineModule engineModule = engineModule(6);
		ShipModules modules = ShipModules.createInstance(consoleIOHandler, 10, cockpitModule, engineModule);
		assertNull(modules.getShipModule(ShieldModule.class));
		ShieldModule newShieldModule = shieldModule(5);
		modules.setShipModule(newShieldModule);
		assertNull(modules.getShipModule(ShieldModule.class));
		assertEquals(8, modules.getCombinedModulePower());
	}

	@Test
	public void tryToReplaceShieldModuleWithEnoughFreePower() {
		CockpitModule cockpitModule = cockpitModule(2);
		EngineModule engineModule = engineModule(6);
		ShipModules modules = ShipModules.createInstance(consoleIOHandler, 20, cockpitModule, engineModule);
		ShieldModule oldShieldModule = shieldModule(5);
		modules.setShipModule(oldShieldModule);
		assertEquals(oldShieldModule, modules.getShipModule(ShieldModule.class));
		assertEquals(13, modules.getCombinedModulePower());

		ShieldModule newShieldModule = shieldModule(7);
		modules.setShipModule(newShieldModule);
		assertEquals(newShieldModule, modules.getShipModule(ShieldModule.class));
		assertEquals(15, modules.getCombinedModulePower());
	}

	@Test
	public void tryToReplaceShieldModuleWithoutEnoughFreePower() {
		CockpitModule cockpitModule = cockpitModule(4);
		EngineModule engineModule = engineModule(7);
		ShipModules modules = ShipModules.createInstance(consoleIOHandler, 20, cockpitModule, engineModule);
		ShieldModule oldShieldModule = shieldModule(5);
		modules.setShipModule(oldShieldModule);
		assertEquals(oldShieldModule, modules.getShipModule(ShieldModule.class));
		assertEquals(16, modules.getCombinedModulePower());

		ShieldModule newShieldModule = shieldModule(10);
		modules.setShipModule(newShieldModule);

		assertEquals(oldShieldModule, modules.getShipModule(ShieldModule.class));
		assertEquals(16, modules.getCombinedModulePower());
	}

	@Test
	public void ensureGetGenericShipModuleWorks() {
		CockpitModule cockpitModule = cockpitModule(4);
		EngineModule engineModule = engineModule(7);
		ShipModules modules = ShipModules.createInstance(consoleIOHandler, 20, cockpitModule, engineModule);

		CockpitModule cockpitModuleReference = (CockpitModule) modules.getShipModule(CockpitModule.class);
		assertEquals(cockpitModule, cockpitModuleReference);
	}

	private CockpitModule cockpitModule(int powerRequirement) {
		return new CockpitModule(consoleIOHandler, "TestCockpitModule", powerRequirement);
	}

	private EngineModule engineModule(int powerRequirement) {
		return new EngineModule(consoleIOHandler, "TestEnginesModule", powerRequirement, 5);
	}

	private WeaponModule weaponModule(int powerRequirement) {
		return new WeaponModule(consoleIOHandler, "TestWeaponModule", powerRequirement);
	}

	private ShieldModule shieldModule(int powerRequirement) {
		return new ShieldModule(consoleIOHandler, "TestShield", powerRequirement);
	}

}