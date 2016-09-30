package events;

import characters.Crewmember;
import goods.Goods;

import java.util.List;
import java.util.ArrayList;

public class EventOutcome {

    private int moneyReward;
    private List<Crewmember> crewReward;
    private List<Goods> goodsReward;

    public EventOutcome(int money, List<Crewmember> crew, List<Goods> goods) {
        moneyReward = money;
        crewReward = crew;
        goodsReward = goods;
    }

    public int getMoneyReward() {
        return moneyReward;
    }

    public List<Crewmember> getCrewReward() {
        return crewReward;
    }

    public void removeCrewReward() {
        crewReward = new ArrayList<Crewmember>();
    }

    public List<Goods> getGoodsReward() {
        return goodsReward;
    }

    public void removeGoodsReward() {
        goodsReward = new ArrayList<Goods>();
    }

}
