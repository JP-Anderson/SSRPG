package ship;

import arch.view.ConsoleIOHandler;
import org.junit.jupiter.api.Test;
import ship.modules.*;
import ship.shields.BasicShieldsMk2;
import ship.weapons.ShipWeaponsHolder;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class AIShipTest {

	@Test
	public void defaultAiShipHasCompulsoryModules() {
		AIShip testShip = new AIShip.AIShipBuilder(view, "TestAIShip",12).build();

		assertNotNull(testShip.modules.getShipModule(CockpitModule.class));
		assertNotNull(testShip.modules.getShipModule(EngineModule.class));

		assertNull(testShip.modules.getShipModule(WeaponModule.class));
		assertNull(testShip.modules.getShipModule(CargoBayModule.class));
		assertNull(testShip.modules.getShipModule(ShieldModule.class));
	}

	@Test
	public void aiShipWithWeaponAndShieldModulesHasCorrectModules() {
		AIShip testShip = buildAIShipWithWeaponAndShields();

		assertNotNull(testShip.modules.getShipModule(CockpitModule.class));
		assertNotNull(testShip.modules.getShipModule(EngineModule.class));
		assertNotNull(testShip.modules.getShipModule(ShieldModule.class));
		assertNotNull(testShip.modules.getShipModule(WeaponModule.class));

		assertNull(testShip.modules.getShipModule(CargoBayModule.class));

	}


	private ConsoleIOHandler view = new ConsoleIOHandler();

	private AIShip buildAIShipWithWeaponAndShields() {
		ShieldModule shieldModule = new ShieldModule(view, "ShieldsModule1", 1);
		shieldModule.shields(new BasicShieldsMk2());

		WeaponModule weaponModule = new WeaponModule(view, "WM1", 3);
		weaponModule.setWeapon(ShipWeaponsHolder.getWeapon("BurstLaserMk3"));

		ArrayList<WeaponModule> weaponModules = new ArrayList<>();
		weaponModules.add(weaponModule);


		return new AIShip.AIShipBuilder(view, "TestAIShip",12)
				.shieldModule(shieldModule)
				.weaponModules(weaponModules)
				.maxHullIntegrity(100)
				.build();
	}

}