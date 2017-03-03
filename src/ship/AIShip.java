package ship;

import arch.view.View;
import ship.modules.CockpitModule;
import ship.modules.EngineModule;

public class AIShip extends Ship {

	AIShip(View view, AIShipBuilder builder) {
		super(view, builder);
	}

	public static class AIShipBuilder extends Ship.GenericShipBuilder<AIShipBuilder> {

		public AIShipBuilder(View view, String name, int maxCombinedModulePower) {
			super(view, name, maxCombinedModulePower);
		}

		public AIShip build() {
			return new AIShip(view, this);
		}

	}

}
