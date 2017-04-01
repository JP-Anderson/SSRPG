package ship;

import characters.Crewmember;
import characters.classes.PilotClass;
import characters.classes.ScoundrelClass;
import map.GridMap;
import map.GridPoint;
import org.junit.Test;

import base.SsrpgTest;
import util.tests.PlayerShipTestHelper;

import static org.junit.Assert.*;

public class CrewTest extends SsrpgTest {

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

	@Test
	public void getCrewmemberAtIndexConsistentlyReturnsCrewmembers() {
		PlayerShip testShip = PlayerShipTestHelper.getPlayerShipWithScannerAndMap();

		Crewmember crewmemberOne = testShip.crew.getCrewmemberAtIndex(0);
		Crewmember crewmemberTwo = testShip.crew.getCrewmemberAtIndex(1);

		assertFalse(testShip.crew.getCrewmemberAtIndex(0) == crewmemberTwo);
		assertFalse(testShip.crew.getCrewmemberAtIndex(1) == crewmemberOne);

		assertTrue(testShip.crew.getCrewmemberAtIndex(0) == crewmemberOne);
		assertTrue(testShip.crew.getCrewmemberAtIndex(1) == crewmemberTwo);
	}

	@Test
	public void removeCrewmemberAtIndexCorrectlyUpdatesSecondCrewmembersIndex() {
		PlayerShip testShip = PlayerShipTestHelper.getPlayerShipWithScannerAndMap();

		Crewmember crewmemberOne = testShip.crew.getCrewmemberAtIndex(0);
		Crewmember crewmemberTwo = testShip.crew.getCrewmemberAtIndex(1);

		int crewmemberTwosIndex = 1;

		assertTrue(testShip.crew.getCrewmemberAtIndex(crewmemberTwosIndex) == crewmemberTwo);

		testShip.crew.removeCrewmemberAtIndex(0);

		assertTrue(testShip.crew.getCrewmemberAtIndex(0) == crewmemberTwo);
		assertTrue(testShip.crew.getCrewmemberAtIndex(1) == null);
	}


}