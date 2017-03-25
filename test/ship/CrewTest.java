package ship;

import characters.Crewmember;
import characters.classes.PilotClass;
import characters.classes.ScoundrelClass;
import map.GridMap;
import map.GridPoint;
import org.junit.jupiter.api.Test;
import util.tests.PlayerShipTestHelper;

import static org.junit.jupiter.api.Assertions.*;

class CrewTest {

	@Test
	public void hasSpaceInCrewReturnsFalseWithNoSpace() {
		PlayerShip testShip = PlayerShipTestHelper.getPlayerShipWithScannerAndMap();

		assertEquals(2, testShip.crew().size());
		assertEquals(3, testShip.crew().getCrewCapacity());
		assertTrue(testShip.hasSpaceInCrew());

		Crewmember newCrewmember = PlayerShipTestHelper.newCrewmember(new ScoundrelClass());
		testShip.crew.addCrewmember(newCrewmember);

		assertEquals(3, testShip.crew().size());
		assertFalse(testShip.hasSpaceInCrew());
	}

	@Test
	public void cannotAddCrewmembersWhenCapacityIsMet() {
		PlayerShip testShip = PlayerShipTestHelper.getPlayerShipWithScannerAndMap();
		Crewmember newCrewmember = PlayerShipTestHelper.newCrewmember(new ScoundrelClass());
		testShip.crew.addCrewmember(newCrewmember);

		assertEquals(testShip.crew().getCrewCapacity(), testShip.crew().size());

		Crewmember secondNewCrewmember = PlayerShipTestHelper.newCrewmember(new PilotClass());
		testShip.crew.addCrewmember(secondNewCrewmember);
		assertFalse(testShip.crew().addCrewmember(secondNewCrewmember));
		assertEquals(testShip.crew().getCrewCapacity(), testShip.crew().size());
	}

}