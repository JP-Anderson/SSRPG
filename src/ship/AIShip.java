package ship;

import ship.modules.CockpitModule;
import ship.modules.EngineModule;

public class AIShip extends Ship {

	public AIShip(String name, ShipModules modules) {
		super(name, modules);
		maxHullIntegrity = 100;
		remainingHullIntegrity = 100;
	}

	public AIShip(AIShipBuilder builder) {
		super(builder);
	}

	public static class AIShipBuilder extends Ship.GenericShipBuilder<AIShipBuilder> {

		public AIShipBuilder(String name, int maxCombinedModulePower) {
			super(name, maxCombinedModulePower);
		}

		public AIShip build() {
			return new AIShip(this);
		}

	}

}
