package map.zones;

import events.events.BanditEvent;
import events.events.ShipwreckEvent;
import org.junit.Test;

import base.SsrpgTest;
import util.rng.MockRandomNumberGenerator;

import static org.junit.Assert.*;

public class LawlessZoneEventsTest extends SsrpgTest {

	@Test
	public void rollsOfZeroToThreeReturnShipwreckEvent() {
		LawlessZoneEvents pze = new LawlessZoneEvents();
		MockRandomNumberGenerator mockRNG = new MockRandomNumberGenerator();
		for (int i = 0; i <= 3; i++) {
			mockRNG.loadSingleInteger(i);
		}

		for (int i = 0; i <= 3; i++) {
			assertTrue(pze.getRandomEvent(mockRNG) instanceof ShipwreckEvent);
		}
	}

	@Test
	public void rollsOfFourToTenReturnBanditEvent() {
		LawlessZoneEvents pze = new LawlessZoneEvents();
		MockRandomNumberGenerator mockRNG = new MockRandomNumberGenerator();
		
		for (int i = 4; i <= 10; i++) {
			mockRNG.loadSingleInteger(i);
		}

		for (int i = 4; i <= 10; i++) {
			assertTrue(pze.getRandomEvent(mockRNG) instanceof BanditEvent);
		}
	}

	@Test
	public void rollsOfElevenToEighteenReturnNoEvent() {
		LawlessZoneEvents pze = new LawlessZoneEvents();
		MockRandomNumberGenerator mockRNG = new MockRandomNumberGenerator();
		for (int i = 11; i <= 18; i++) {
			mockRNG.loadSingleInteger(i);
		}

		for (int i = 11; i <= 18; i++) {
			assertTrue(pze.getRandomEvent(mockRNG) == null);
		}
	}
	
}