package map.zones;

import events.events.CargoCheckEvent;
import events.events.ShipwreckEvent;

public class ProtectedZoneEvents extends ZoneEventMap {

	@Override
	public void addEventProbabilities() {
		eventProbabilities.put(new CargoCheckEvent(null),5);
		eventProbabilities.put(new ShipwreckEvent(null),1);
		eventProbabilities.put(null,8);
	}

}
