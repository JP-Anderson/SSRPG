package events;

import characters.Crewmember;
import characters.Skills;
import characters.classes.PilotClass;
import goods.GoodsForSale;
import goods.GoodsList;
import ship.Ship;
import util.RNG;

import java.util.ArrayList;

public abstract class Event {

    abstract void initialize();
    abstract void displayEvent();
    abstract void getUserInput();
    abstract EventOutcome generateOutcome();

    protected Ship playerShip;
    protected EventOutcome outcome;

    public final EventOutcome transpire(Ship playerShip){
            setPlayerShip(playerShip);
            initialize();
            displayEvent();
            getUserInput();
            return generateOutcome();
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
