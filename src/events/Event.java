package events;

import arch.session.interaction.Interaction;
import arch.session.interaction.TextInteraction;
import arch.view.ConsoleIOHandler;
import arch.view.InputHandler;
import characters.Crewmember;
import characters.classes.PilotClass;
import goods.Goods;
import goods.GoodsForSale;
import goods.GoodsList;
import ship.PlayerShip;
import util.RNG;

import java.util.ArrayList;

public abstract class Event {

	protected ConsoleIOHandler view;
	protected Interaction rootInteraction;

	protected Event(ConsoleIOHandler injectedView) {
		view = injectedView;
		rootInteraction = TextInteraction.createStartingInteraction(injectedView, getEventIntroductionMessage());
	}

	protected abstract String getEventIntroductionMessage();

	//region Template Methods
	abstract void initializeOutcome();

	abstract void initializeInteractionTree();

	abstract void displayEvent();

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
			System.out.println(crewPrompt + "? (Y/N)");
			char decision = view.inputHandler.getCharFromUser("");
			if (decision != 'Y' && decision != 'y') {
				outcome.removeCrewReward();
			}
		}
		if (outcome.getGoodsReward().size() > 0) {
			Goods newGoods = outcome.getGoodsReward().get(0);
			System.out.println(goodsPrompt.replace("$", newGoods.name) + "? (Y/N)");
			char decision = view.inputHandler.getCharFromUser("");
			if (decision != 'Y' && decision != 'y') {
				outcome.removeGoodsReward();
			}
		}

	}

	protected final ArrayList<Crewmember> generateCrewMembers(double chanceOfCrewmember) {
		ArrayList<Crewmember> survivors = new ArrayList<>();
		if (RNG.randZeroToOne() <= 0.05) {
			// TODO need to randomize the classes
			Crewmember survivor = new Crewmember("Survivor", new PilotClass(), 1);
			survivors.add(survivor);
		}
		return survivors;
	}

	protected final ArrayList<GoodsForSale> generateGoods(double chanceOfGoods) {
		ArrayList<GoodsForSale> goods = new ArrayList<>();
		if (RNG.randZeroToOne() <= chanceOfGoods) {
			int possibleGoods = GoodsList.GOODS.length;
			GoodsForSale survivingGoods = GoodsList.GOODS[RNG.randInt(0, possibleGoods)];
			goods.add(survivingGoods);
		}
		return goods;
	}

	private void setPlayerPlayerShip(PlayerShip playerShip) {
		this.playerShip = playerShip;
	}

}
