package events;

import arch.session.ShipBattleSession;
import goods.Goods;
import ship.EnemyShip;
import util.ConsoleInputHandler;
import util.RNG;

/**
 * Created by Jp on 08/12/2016.
 */
public class BanditEvent extends Event {

    @Override
    void initialize() {
        int moneyReward = RNG.randInt(200,4400);
        outcome = new EventOutcome(moneyReward, generateCrewMembers(0.025), generateGoods(0.45));
    }

    @Override
    void displayEvent() {
        System.out.println("You encounter a Bandit!");
        System.out.println("The Bandit primes its weapons and moves in to attack!");

        EnemyShip s2 = new EnemyShip("2");
        ShipBattleSession sbs = new ShipBattleSession(playerShip,s2);
        sbs.run();


        System.out.println("There is " + outcome.getMoneyReward() + " CREDS.");
        if (outcome.getCrewReward().size() > 0) {
            System.out.println("There is a survivor!");
        }
        if (outcome.getGoodsReward().size() > 0) {
            Goods newGoods = outcome.getGoodsReward().get(0);
            System.out.println("There is one unit of " + newGoods.name + " salvageable in the hold.");
        }
    }

    @Override
    void getUserInput() {
        //TODO: move this into a UI/console input class
        if (outcome.getCrewReward().size() > 0) {
            System.out.println("The Bandits were holding a prisoner who offers to join your crew. Accept? (Y/N)");
            char decision = ConsoleInputHandler.getCharFromUser("");
            if (decision != 'Y' && decision != 'y') {
                outcome.removeCrewReward();
            }
        }
        if (outcome.getGoodsReward().size() > 0) {
            Goods newGoods = outcome.getGoodsReward().get(0);
            System.out.println("You find some " + newGoods.name + " in the Bandit hold, would you like to take the " + newGoods.name + "? (Y/N)");
            char decision = ConsoleInputHandler.getCharFromUser("");
            if (decision != 'Y' && decision != 'y') {
                outcome.removeGoodsReward();
            }
        }

    }

    @Override
    EventOutcome generateOutcome() { return outcome; }
}
