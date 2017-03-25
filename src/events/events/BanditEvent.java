package events.events;

import arch.session.interaction.ComplexInteraction;
import arch.session.ShipBattleSession;
import arch.session.interaction.TextInteraction;
import arch.view.ConsoleIOHandler;
import events.Event;
import events.EventOutcome;
import goods.Goods;
import ship.AIShip;
import ship.modules.ShieldModule;
import ship.modules.WeaponModule;
import ship.shields.BasicShieldsMk2;
import ship.weapons.ShipWeapon;
import ship.weapons.ShipWeaponsHolder;
import util.rng.RNG;

import java.util.ArrayList;

public class BanditEvent extends Event {

	public BanditEvent(ConsoleIOHandler injectedView) {
		super(injectedView);
	}

	@Override
	protected String getEventIntroductionMessage() {
		return "You encounter a Bandit!";
	}

	@Override
	protected void initializeOutcome() {
		int moneyReward = rand.randInt(200, 4400);
		//Should we assign xp here, or should battles generate XP?
		//Will we ever have a battle not related to an "Event"?
		int xpReward = rand.randInt(100,150);
		outcome = new EventOutcome(moneyReward, xpReward, generateCrewMembers(0.025), generateGoods(0.45));
	}

	@Override
	protected void initializeInteractionTree() {
		TextInteraction i1_2 = TextInteraction.createAdditionalInteraction(
				rootInteraction,"The Bandit primes its weapons and moves in to attack!");
		ComplexInteraction i1_3 = ComplexInteraction.createComplexInteraction(i1_2, new BanditEventRunnable());
		TextInteraction i1_4 = TextInteraction.createAdditionalInteraction(
				i1_3,"There is " + outcome.getMoneyReward() + " CREDS.");
		TextInteraction i1_5 = null;
		if (outcome.getCrewReward().size() > 0) {
			i1_5 = TextInteraction.createAdditionalInteraction(
					i1_4, "There is a survivor who was taken prisoner by the Bandits!");
		}
		TextInteraction i1_6 = null;
		if (outcome.getGoodsReward().size() > 0) {
			Goods newGoods = outcome.getGoodsReward().get(0);
			i1_6 = TextInteraction.createAdditionalInteraction(
					i1_5 == null ? i1_4 : i1_5,
					"There is one unit of " + newGoods.name + " salvageable in the hold."
			);
		}
	}

	private class BanditEventRunnable implements Runnable {
		@Override
		public void run() {
			ShieldModule shieldModule = new ShieldModule(view, "ShieldsModule1", 1);
			shieldModule.shields(new BasicShieldsMk2());

			WeaponModule weaponModule = new WeaponModule(view, "WM1", 3);
			weaponModule.setWeapon(ShipWeaponsHolder.getWeapon("BurstLaserMk3"));

			ArrayList<WeaponModule> weaponModules = new ArrayList<>();
			weaponModules.add(weaponModule);


			AIShip s2 = new AIShip.AIShipBuilder(view, "Bandits",12)
					.shieldModule(shieldModule)
					.weaponModules(weaponModules)
					.maxHullIntegrity(100)
					.build();

			ShipBattleSession sbs = new ShipBattleSession(view, playerShip, s2);
			sbs.run();
		}
	}

	@Override
	protected EventOutcome getOutcome() {
		return outcome;
	}
}
