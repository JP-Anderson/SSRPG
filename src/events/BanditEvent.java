package events;

import arch.session.ShipBattleSession;
import goods.Goods;
import ship.AIShip;
import ship.ShipModules;
import ship.modules.CockpitModule;
import ship.modules.EngineModule;
import ship.modules.ShieldModule;
import ship.modules.ShipModule;
import ship.shields.BasicShieldsMk2;
import util.RNG;

import java.util.ArrayList;

/**
 * Created by Jp on 08/12/2016.
 */
public class BanditEvent extends Event {

	@Override
	void initialize() {
		int moneyReward = RNG.randInt(200, 4400);
		outcome = new EventOutcome(moneyReward, generateCrewMembers(0.025), generateGoods(0.45));
	}

	@Override
	void displayEvent() {
		System.out.println("You encounter a Bandit!");
		System.out.println("The Bandit primes its weapons and moves in to attack!");
		ShieldModule shieldModule = new ShieldModule("ShieldsModule1", 1);
		shieldModule.shields(new BasicShieldsMk2());

		AIShip s2 = new AIShip.AIShipBuilder("Bandits",12)
				.shieldModule(shieldModule)
				.maxHullIntegrity(100)
				.build();

		ShipBattleSession sbs = new ShipBattleSession(_playerShip, s2);
		sbs.run();

		System.out.println("There is " + outcome.getMoneyReward() + " CREDS.");
		if (outcome.getCrewReward().size() > 0) {
			System.out.println("There is a survivor who was taken prisoner by the Bandits!");
		}
		if (outcome.getGoodsReward().size() > 0) {
			Goods newGoods = outcome.getGoodsReward().get(0);
			System.out.println("There is one unit of " + newGoods.name + " salvageable in the hold.");
		}
	}

	@Override
	EventOutcome generateOutcome() {
		return outcome;
	}
}
