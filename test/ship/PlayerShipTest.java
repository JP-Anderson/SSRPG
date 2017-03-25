package ship;

import characters.Crewmember;
import characters.classes.PilotClass;
import characters.classes.ScoundrelClass;
import map.GridMap;
import map.GridPoint;
import org.junit.jupiter.api.Test;
import util.tests.PlayerShipTestHelper;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class PlayerShipTest {

	@Test
	public void initialiseMap() {

	}

	@Test
	public void setLocationCorrectlyUpdatesLocation() {
		PlayerShip testShip = PlayerShipTestHelper.getPlayerShipWithScannerAndMap();

		GridPoint startLocation = new GridPoint(3,1);
		assertTrue(testShip.getLocation().comparePoints(startLocation) == 0);

		GridPoint newLocation = new GridPoint(2,4);

		testShip.setLocation(newLocation);

		assertTrue(testShip.getLocation().comparePoints(newLocation) == 0);
		assertFalse(testShip.getLocation().comparePoints(startLocation) == 0);
	}

	@Test
	public void travelSuccessfullyUpdatesLocation() {
		PlayerShip testShip = PlayerShipTestHelper.getPlayerShipWithScannerAndMap();

		GridPoint startLocation = new GridPoint(3,1);
		assertTrue(testShip.getLocation().comparePoints(startLocation) == 0);

		GridPoint destination = new GridPoint(4,5);

		testShip.travel(destination, startLocation.comparePoints(destination));

		assertTrue(testShip.getLocation().comparePoints(destination) == 0);
	}

	@Test
	public void remainingFuelIsReducedAfterTravel() {
		PlayerShip testShip = PlayerShipTestHelper.getPlayerShipWithScannerAndMap();
		GridPoint startLocation = new GridPoint(3,1);

		assertTrue(testShip.getRemainingFuel() == testShip.getFuelCapacity());

		GridPoint destination = new GridPoint(4,5);

		testShip.travel(destination, startLocation.comparePoints(destination));

		int remainingFuel = testShip.getRemainingFuel();
		assertTrue(testShip.getRemainingFuel() == testShip.getFuelCapacity() - 20);
	}

	@Test
	public void setRemainingFuel() {

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