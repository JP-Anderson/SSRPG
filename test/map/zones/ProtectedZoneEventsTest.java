package map.zones;

import events.events.CargoCheckEvent;
import events.events.ShipwreckEvent;
import org.junit.Test;

import base.SsrpgTest;
import util.rng.MockRandomNumberGenerator;

import static org.junit.Assert.*;

public class ProtectedZoneEventsTest extends SsrpgTest {

	@Test
	public void rollsOfZeroToFourReturnCargoCheckEvent() {
		ProtectedZoneEvents pze = new ProtectedZoneEvents();
		MockRandomNumberGenerator mockRNG = new MockRandomNumberGenerator();
		for (int i = 0; i <= 4; i++) {
			mockRNG.loadSingleInteger(i);
		}

		for (int i = 0; i <= 4; i++) {
			assertTrue(pze.getRandomEvent(mockRNG) instanceof CargoCheckEvent);
		}
	}

	@Test
	public void rollOfFiveReturnsShipwreckEvent() {
		ProtectedZoneEvents pze = new ProtectedZoneEvents();
		MockRandomNumberGenerator mockRNG = new MockRandomNumberGenerator(5);

		assertTrue(pze.getRandomEvent(mockRNG) instanceof ShipwreckEvent);
	}

	@Test
	public void rollsOfSixToThirteenReturnNoEvent() {
		ProtectedZoneEvents pze = new ProtectedZoneEvents();
		MockRandomNumberGenerator mockRNG = new MockRandomNumberGenerator();
		for (int i = 6; i <= 13; i++) {
			mockRNG.loadSingleInteger(i);
		}

		for (int i = 6; i <= 13; i++) {
			assertTrue(pze.getRandomEvent(mockRNG) == null);
		}
	}

}