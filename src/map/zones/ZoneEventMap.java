package map.zones;

import events.events.BanditEvent;
import events.Event;
import events.events.ShipwreckEvent;
import util.rng.RandomNumberGenerator;

import java.util.*;

public abstract class ZoneEventMap {

	public final Map<Event, Integer> eventProbabilities;

	protected ZoneEventMap() {
		eventProbabilities = new LinkedHashMap<>();
		addEventProbabilities();
	}

	public abstract void addEventProbabilities();

	public Event getRandomEvent(RandomNumberGenerator rng) {
		int random = rng.randInt(0, getEventProbabilitySum());
		return getEvent(random);
	}

	private int getEventProbabilitySum() {
		int probabilityTally = 0;
		Set<Map.Entry<Event, Integer>> eventProbabilitySet = eventProbabilities.entrySet();
		for (Map.Entry<Event,Integer> eventProbability : eventProbabilitySet)
			probabilityTally = probabilityTally + eventProbability.getValue();
		return probabilityTally;
	}

	private Event getEvent(int randomRoll) {
		// Start from -1 so the event count is zero indexed
		int probTally = -1;
		Set<Map.Entry<Event, Integer>> eventProbabilitiesSet = this.eventProbabilities.entrySet();
		for (Map.Entry<Event,Integer> eventProbability : eventProbabilitiesSet) {
			probTally = probTally + eventProbability.getValue();
			if (randomRoll <= probTally) return eventProbability.getKey();
		}
		Map.Entry<Event,Integer> e = (Map.Entry<Event, Integer>) this.eventProbabilities.entrySet().toArray()[0];
		return e.getKey();
	}

}
