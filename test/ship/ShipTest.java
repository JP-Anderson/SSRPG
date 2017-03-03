package ship;

import arch.view.ConsoleIOHandler;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ship.modules.MannableShipModule;

import static org.junit.jupiter.api.Assertions.*;

class ShipTest {

	private static PlayerShip testShip;

	private static ConsoleIOHandler consoleIOHandler = new ConsoleIOHandler();

	@BeforeAll
	static void setUp() {
		testShip = new PlayerShip.PlayerShipBuilder(consoleIOHandler, "TestShip",10).build();
	}

	@Test
	void ensureNoParamBuiltShipHasCockpitAndEngineModules() {
		MannableShipModule cockpit = testShip.getCockpitModule();
		MannableShipModule engineModule = testShip.getEngineModule();
		assertNotNull(cockpit);
		assertNotNull(engineModule);
	}

}