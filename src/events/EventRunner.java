package events;

import ship.PlayerShip;
import characters.Crewmember;
import goods.GoodsForSale;
import goods.PurchasedGoods;

public class EventRunner {

	public static void run(Event event, PlayerShip player) {
		EventOutcome outcome = event.transpire(player);
		sleep(2);
		for (GoodsForSale g : outcome.getGoodsReward()) {
			//player.getCargoBay().addCargo(g);
			if (!player.getCargoBay().isFull()) {
				player.getCargoBay().addCargo(new PurchasedGoods(g, 1, null));
				System.out.println("Adding " + g.name + ".");
			} else System.out.println("No space for " + g.name + ".");
		}
		for (Crewmember c : outcome.getCrewReward()) {
			player.getCrew().add(c);
			System.out.println("Adding to crew.");
		}
		int newBalance = player.getMoney() + outcome.getMoneyReward();
		System.out.println("CREDS " + player.getMoney() + " --> " + newBalance);
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
		System.out.println();
		System.out.println();
	}

}
