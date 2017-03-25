package events.events;

import arch.view.ConsoleIOHandler;
import events.Event;
import events.EventOutcome;
import goods.*;
import util.rng.RNG;

public class ShipwreckEvent extends Event {

	public ShipwreckEvent(ConsoleIOHandler injectedView) {
		super(injectedView);
	}

	@Override
	protected String getEventIntroductionMessage() {
		return "You encounter a shipwreck.";
	}

	@Override
	protected void initializeOutcome() {
		int moneyReward = rand.randInt(20, 1500);
		int xpReward = rand.randInt(1,8);
		outcome = new EventOutcome(moneyReward, xpReward, generateCrewMembers(0.05), generateGoods(0.25));
		goodsPrompt = "Would you like to take the $ from the wreckage";
	}

	@Override
	protected void initializeInteractionTree() {
		//todo
	}

	@Override
	protected EventOutcome getOutcome() {
		return outcome;
	}

}
