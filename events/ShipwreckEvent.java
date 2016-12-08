package events;

import characters.Crewmember;
import characters.Skills;
import characters.classes.PilotClass;
import goods.*;
import goods.GoodsList;
import ship.Ship;
import util.RNG;
import util.ConsoleInputHandler;

import java.util.ArrayList;

public class ShipwreckEvent extends Event {

    @Override
    void initialize() {
        int moneyReward = RNG.randInt(20,1500);
        outcome = new EventOutcome(moneyReward, generateCrewMembers(0.05), generateGoods(0.25));
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
    void getUserInput() {
        //TODO: move this into a UI/console input class
        if (outcome.getCrewReward().size() > 0) {
            System.out.println("Would you like to take the crewmember? (Y/N)");
            char decision = ConsoleInputHandler.getCharFromUser("");
            if (decision != 'Y' && decision != 'y') {
                outcome.removeCrewReward();
            }
        }
        if (outcome.getGoodsReward().size() > 0) {
            Goods newGoods = outcome.getGoodsReward().get(0);
            System.out.println("Would you like to take the " + newGoods.name + "? (Y/N)");
            char decision = ConsoleInputHandler.getCharFromUser("");
            if (decision != 'Y' && decision != 'y') {
                outcome.removeGoodsReward();
            }
        }
    }

    @Override
    EventOutcome generateOutcome() {
        return outcome;
    }

}
