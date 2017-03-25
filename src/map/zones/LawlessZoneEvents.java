package map.zones;

import events.events.BanditEvent;
import events.events.ShipwreckEvent;

public class LawlessZoneEvents extends ZoneEventMap {

	@Override
	public void addEventProbabilities() {
		eventProbabilities.put(new ShipwreckEvent(null),4);
		eventProbabilities.put(new BanditEvent(null), 7);
		eventProbabilities.put(null,8);
	}

}
