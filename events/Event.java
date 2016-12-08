package events;

import characters.Crewmember;
import characters.Skills;
import characters.classes.PilotClass;
import goods.Goods;
import goods.GoodsForSale;
import goods.GoodsList;
import ship.Ship;
import util.ConsoleInputHandler;
import util.RNG;

import java.util.ArrayList;

public abstract class Event {

    abstract void initialize();
    abstract void displayEvent();
    abstract EventOutcome generateOutcome();

    protected Ship playerShip;
    protected EventOutcome outcome;

    protected String crewPrompt = "Would you like to take the survivor";
    protected String goodsPrompt = "Would you like to take the $";


    public final EventOutcome transpire(Ship playerShip){
            setPlayerShip(playerShip);
            initialize();
            displayEvent();
            getUserInput();
            return generateOutcome();
    }

     protected void getUserInput() {
        //TODO: move this into a UI/console input class
        if (outcome.getCrewReward().size() > 0) {
            System.out.println(crewPrompt + "? (Y/N)");
            char decision = ConsoleInputHandler.getCharFromUser("");
            if (decision != 'Y' && decision != 'y') {
                outcome.removeCrewReward();
            }
        }
        if (outcome.getGoodsReward().size() > 0) {
            Goods newGoods = outcome.getGoodsReward().get(0);
            System.out.println(goodsPrompt.replace("$",newGoods.name) + "? (Y/N)");
            char decision = ConsoleInputHandler.getCharFromUser("");
            if (decision != 'Y' && decision != 'y') {
                outcome.removeGoodsReward();
            }
        }

    }

    protected final ArrayList<Crewmember> generateCrewMembers(double chanceOfCrewmember) {
        ArrayList<Crewmember> survivors = new ArrayList<>();
        if (RNG.randZeroToOne() <= 0.05) {
            // need to randomize the classes
            Crewmember survivor = new Crewmember("Survivor", new Skills(), new PilotClass());
            survivors.add(survivor);
        }
        return survivors;
    }

    protected final ArrayList<GoodsForSale> generateGoods(double chanceOfGoods) {
        ArrayList<GoodsForSale> goods = new ArrayList<>();
        if (RNG.randZeroToOne() <= chanceOfGoods) {
            int possibleGoods = GoodsList.GOODS.length;
            GoodsForSale survivingGoods = GoodsList.GOODS[RNG.randInt(0,possibleGoods)];
            goods.add(survivingGoods);
        }
        return goods;
    }

    private void setPlayerShip(Ship _playerShip) {
        playerShip = _playerShip;
    }

}
