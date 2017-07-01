package ship;

import characters.Crewmember;
import characters.classes.PilotClass;
import characters.classes.ScoundrelClass;
import map.GridMap;
import map.GridPoint;
import map.gridsquares.EmptyGridSquare;

import org.junit.Test;

import base.SsrpgTest;
import util.tests.PlayerShipTestHelper;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class PlayerShipTest extends SsrpgTest {

	@Test
	public void initialiseMap() {
		PlayerShip testShip = PlayerShipTestHelper.getPlayerShipWithScannerAndMap();

		GridPoint startLocation = new GridPoint(3,1);
		assertTrue(testShip.getLocationGridPoint().comparePoints(startLocation) == 0);
	}

	@Test
	public void setLocationCorrectlyUpdatesLocation() {
		PlayerShip testShip = PlayerShipTestHelper.getPlayerShipWithScannerAndMap();

		GridPoint startLocation = new GridPoint(3,1);
		assertTrue(testShip.getLocationGridPoint().comparePoints(startLocation) == 0);

		GridPoint newLocation = new GridPoint(2,4);
		EmptyGridSquare newSquare = new EmptyGridSquare(newLocation);
		
		testShip.setLocation(newSquare);

		assertTrue(testShip.getLocationGridPoint().comparePoints(newLocation) == 0);
		assertFalse(testShip.getLocationGridPoint().comparePoints(startLocation) == 0);
	}

	@Test
	public void travelSuccessfullyUpdatesLocation() {
		PlayerShip testShip = PlayerShipTestHelper.getPlayerShipWithScannerAndMap();

		GridPoint startLocation = new GridPoint(3,1);
		assertTrue(testShip.getLocationGridPoint().comparePoints(startLocation) == 0);

		GridPoint destination = new GridPoint(4,5);
		EmptyGridSquare destinationSquare = new EmptyGridSquare(destination);

		testShip.travel(destinationSquare, startLocation.comparePoints(destination));

		assertTrue(testShip.getLocationGridPoint().comparePoints(destination) == 0);
	}

	@Test
	public void remainingFuelIsReducedAfterTravel() {
		PlayerShip testShip = PlayerShipTestHelper.getPlayerShipWithScannerAndMap();
		GridPoint startLocation = new GridPoint(3,1);

		assertTrue(testShip.getRemainingFuel() == testShip.getFuelCapacity());

		GridPoint destination = new GridPoint(4,5);
		EmptyGridSquare destinationSquare = new EmptyGridSquare(destination);

		testShip.travel(destinationSquare, startLocation.comparePoints(destination));

		assertTrue(testShip.getRemainingFuel() == testShip.getFuelCapacity() - 20);
	}

	@Test
	public void giveExperienceToAllCrewmembers() {
		PlayerShip testShip = PlayerShipTestHelper.getPlayerShipWithScannerAndMap();
		Crewmember newCrewmember = PlayerShipTestHelper.newCrewmember(new ScoundrelClass());

		testShip.crew.addCrewmember(newCrewmember);

		ArrayList<Crewmember> crewmembers = testShip.crew.getCrew();

		for (Crewmember crewmember : crewmembers)
			assertTrue(crewmember.getLevel().getXp() == 0);

		testShip.giveExperienceToAllCrewmembers(200);

		for (Crewmember crewmember : crewmembers)
			assertTrue(crewmember.getLevel().getXp() == 200);
	}

}