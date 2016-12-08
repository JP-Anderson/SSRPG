package events;

import arch.session.ShipBattleSession;
import goods.Goods;
import ship.EnemyShip;
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

    }

    @Override
    EventOutcome generateOutcome() {
        return null;
    }
}
