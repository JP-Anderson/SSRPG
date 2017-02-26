package ship;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ship.modules.MannableShipModule;

import static org.junit.jupiter.api.Assertions.*;

class ShipTest {
	
	private static PlayerShip testShip;

	@BeforeAll
	static void setUp() {
		testShip = new PlayerShip.PlayerShipBuilder("TestShip",10).build();
	}

	@Test
	void ensureNoParamBuiltShipHasCockpitAndEngineModules() {
		MannableShipModule cockpit = testShip.getCockpitModule();
		MannableShipModule engineModule = testShip.getEngineModule();
		assertNotNull(cockpit);
		assertNotNull(engineModule);
	}

}