package events;

import characters.Crewmember;
import characters.Skills;
import goods.Goods;
import goods.GoodsList;
import util.RNG;
import util.ConsoleInputHandler;

import java.util.ArrayList;

public class ShipwreckEvent extends Event {

    @Override
    void initialize() {
        int moneyReward = RNG.randInt(20,150);
        outcome = new EventOutcome(moneyReward, generateCrewMembers(), generateGoods());
    }

    @Override
    void displayEvent() {
        //TODO: move this into a UI/console output class
        System.out.println("You encounter a shipwreck.");
        System.out.println("There is " + outcome.getMoneyReward() + " money.");
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

    private ArrayList<Crewmember> generateCrewMembers() {
        ArrayList<Crewmember> survivors = new ArrayList<Crewmember>();
        if (RNG.randZeroToOne() <= 0.05) {
            Crewmember survivor = new Crewmember("Survivor", new Skills());
            survivors.add(survivor);
            System.out.println("There is still a survivor aboard!");
        }
        return survivors;
    }

    private ArrayList<Goods> generateGoods() {
        ArrayList<Goods> goods = new ArrayList<Goods>();
        if (RNG.randZeroToOne() <= 0.25) {
            int possibleGoods = GoodsList.GOODS.length;
            Goods survivingGoods = GoodsList.GOODS[RNG.randInt(0,possibleGoods)];
            goods.add(survivingGoods);
            System.out.println("There one unit of " + survivingGoods.name);
        }
        return goods;
    }
}
