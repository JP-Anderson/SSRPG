package map.zones;

import events.EmptyEventOutcome;
import events.Event;
import events.events.CargoCheckEvent;
import events.events.ShipwreckEvent;
import org.junit.jupiter.api.Test;
import util.rng.MockRandomNumberGenerator;

import static org.junit.jupiter.api.Assertions.*;

class ProtectedZoneEventsTest {

	@Test
	public void rollsOfZeroToFiveReturnCargoCheckEvent() {
		ProtectedZoneEvents pze = new ProtectedZoneEvents();
		MockRandomNumberGenerator mockRNG = new MockRandomNumberGenerator();
		for (int i = 0; i <= 5; i++) {
			mockRNG.loadSingleInteger(i);
		}

		for (int i = 0; i <= 5; i++) {
			assertTrue(pze.getRandomEvent(mockRNG) instanceof CargoCheckEvent);
		}
	}

	@Test
	public void rollOfSixReturnsShipwreckEvent() {
		ProtectedZoneEvents pze = new ProtectedZoneEvents();
		MockRandomNumberGenerator mockRNG = new MockRandomNumberGenerator(6);

		assertTrue(pze.getRandomEvent(mockRNG) instanceof ShipwreckEvent);
	}

	@Test
	public void rollsOfSevenToFifteenReturnNoEvent() {
		ProtectedZoneEvents pze = new ProtectedZoneEvents();
		MockRandomNumberGenerator mockRNG = new MockRandomNumberGenerator();
		for (int i = 7; i < 15; i++) {
			mockRNG.loadSingleInteger(i);
		}

		for (int i = 7; i < 15; i++) {
			assertTrue(pze.getRandomEvent(mockRNG) == null);
		}
	}

}