package events;

import arch.session.interaction.Interaction;
import arch.session.interaction.TextInteraction;
import arch.view.ConsoleIOHandler;
import characters.Crewmember;
import characters.classes.PilotClass;
import goods.Goods;
import goods.GoodsForSale;
import goods.GoodsList;
import ship.PlayerShip;
import util.rng.RNG;

import java.util.ArrayList;

public abstract class Event {

	protected ConsoleIOHandler view;
	protected Interaction rootInteraction;
	protected RNG rand;

	protected Event(ConsoleIOHandler injectedView) {
		rand = new RNG();
		view = injectedView;
		rootInteraction = TextInteraction.createStartingInteraction(injectedView, getEventIntroductionMessage());
	}

	protected abstract String getEventIntroductionMessage();

	//region Template Methods
	abstract void initializeOutcome();

	abstract void initializeInteractionTree();

	protected void displayEvent() {
		rootInteraction.run();
	};

	abstract EventOutcome getOutcome();
	//endregion

	protected PlayerShip playerShip;
	protected EventOutcome outcome;

	protected String crewPrompt = "Would you like to take the survivor";
	protected String goodsPrompt = "Would you like to take the $";

	public final EventOutcome transpire(PlayerShip playerShip) {
		setPlayerPlayerShip(playerShip);
		initializeOutcome();
		initializeInteractionTree();
		displayEvent();
		getUserInput();
		return getOutcome();
	}

	protected void getUserInput() {
		//TODO: move this into a UI/console input class
		if (outcome.getCrewReward().size() > 0) {
			if (playerShip.hasSpaceInCrew()) {
				view.outputHandler.sendStringToView(crewPrompt + "? (Y/N)");
				char decision = view.inputHandler.getCharFromUser("");
				if (decision != 'Y' && decision != 'y') {
					outcome.removeCrewReward();
				}
			} else {
				view.outputHandler.sendStringToView("There is no space for the survivor!");
			}

		}
		if (outcome.getGoodsReward().size() > 0) {
			Goods newGoods = outcome.getGoodsReward().get(0);
			view.outputHandler.sendStringToView(goodsPrompt.replace("$", newGoods.name) + "? (Y/N)");
			char decision = view.inputHandler.getCharFromUser("");
			if (decision != 'Y' && decision != 'y') {
				outcome.removeGoodsReward();
			}
		}

	}

	protected final ArrayList<Crewmember> generateCrewMembers(double chanceOfCrewmember) {
		ArrayList<Crewmember> survivors = new ArrayList<>();
		if (rand.randZeroToOne() <= chanceOfCrewmember) {
			// TODO need to randomize the classes
			Crewmember survivor = new Crewmember("Survivor", new PilotClass(), 1);
			survivors.add(survivor);
		}
		return survivors;
	}

	protected final ArrayList<GoodsForSale> generateGoods(double chanceOfGoods) {
		ArrayList<GoodsForSale> goods = new ArrayList<>();
		if (rand.randZeroToOne() <= chanceOfGoods) {
			int possibleGoods = GoodsList.GOODS.length;
			GoodsForSale survivingGoods = GoodsList.GOODS[rand.randInt(0, possibleGoods)];
			goods.add(survivingGoods);
		}
		return goods;
	}

	private void setPlayerPlayerShip(PlayerShip playerShip) {
		this.playerShip = playerShip;
	}

}
