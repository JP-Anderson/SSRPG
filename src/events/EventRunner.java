package events;

import arch.view.ConsoleIOHandler;
import ship.PlayerShip;
import characters.Crewmember;
import goods.GoodsForSale;
import goods.PurchasedGoods;

public class EventRunner {

	private static ConsoleIOHandler view;

	public static void run(ConsoleIOHandler newView, Event event, PlayerShip player) {
		view = newView;
		EventOutcome outcome = event.transpire(player);
		sleep(2);
		for (GoodsForSale g : outcome.getGoodsReward()) {
			//player.getCargoBay().addCargo(g);
			if (!player.getCargoBay().isFull()) {
				player.getCargoBay().addCargo(new PurchasedGoods(g, 1, null));
				view.outputHandler.sendStringToView("Adding " + g.name + ".");
			} else view.outputHandler.sendStringToView("No space for " + g.name + ".");
		}
		for (Crewmember c : outcome.getCrewReward()) {
			player.getCrew().add(c);
			view.outputHandler.sendStringToView("Adding to crew.");
		}
		int newBalance = player.getMoney() + outcome.getMoneyReward();
		view.outputHandler.sendStringToView("CREDS " + player.getMoney() + " --> " + newBalance);
		sleep(1);

		player.setMoney(newBalance);
		printTwoRows();
	}

	// TODO: Refactor these to a console or view class
	private static void sleep(int seconds) {
		int milliseconds = seconds * 1000;
		try {
			Thread.sleep(milliseconds);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void printTwoRows() {
		view.outputHandler.sendStringToView("");
		view.outputHandler.sendStringToView("");
	}

}
