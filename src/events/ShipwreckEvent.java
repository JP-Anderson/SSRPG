package events;

import goods.*;
import util.RNG;

public class ShipwreckEvent extends Event {

    @Override
    void initialize() {
        int moneyReward = RNG.randInt(20,1500);
        outcome = new EventOutcome(moneyReward, generateCrewMembers(0.05), generateGoods(0.25));
        goodsPrompt = "Would you like to take the $ from the wreckage";
    }

    @Override
    void displayEvent() {
        //TODO: move this into a UI/console output class
        System.out.println("You encounter a shipwreck.");
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
    EventOutcome generateOutcome() {
        return outcome;
    }

}
