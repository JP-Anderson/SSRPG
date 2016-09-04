import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.util.stream.*;

public class Market {

  public final int marketSize;
  public ArrayList<Goods> availableGoods;

  public static Market getMarket(int marketSize) {
    return new Market(marketSize);
  }

  private Market(int pMarketSize) {
    marketSize = pMarketSize;
    availableGoods = new ArrayList<Goods>();
    generateGoods();
  }

  private void generateGoods() {
    availableGoods = new ArrayList<Goods>();
    int probabilityTotal = IntStream.of(GOODS_PROBABILITIES).sum();
    //System.out.println(probabilityTotal);
    boolean[] selectedGoods = new boolean[GOODS_PROBABILITIES.length];
    Arrays.fill(selectedGoods,false);
    for (int i = 0; i < marketSize; i++) {
      boolean goodsNotAlreadyChosen = false;
      int cycles = 0;
      while (!goodsNotAlreadyChosen) {
        int random = RNG.randInt(0,probabilityTotal-1);
        //System.out.println("Random: " + random);
        int goodsIndex = getGood(random, probabilityTotal);
        if (selectedGoods[goodsIndex] == false) {
          availableGoods.add(GoodsList.GOODS[goodsIndex].generateGoodsMarketValue(GOODS_PROBABILITIES[goodsIndex]));
          selectedGoods[goodsIndex] = true;
          goodsNotAlreadyChosen = true;
          break;
        }
        cycles++;
        //System.out.println("We've already seen " + GoodsList.GOODS[goodsIndex].name + ". Trying again!");
      }
      //System.out.println(cycles);

    }
  }

  private int getGood(int random, int totalProb) {
    int total = 0;
    for (int i = 0; i < GOODS_PROBABILITIES.length; i++) {
      total = total + GOODS_PROBABILITIES[i];
      //System.out.println("Current total: " + total + ". We're at " + GoodsList.GOODS[i].name);
      if (random < total) {
        return i;
      }
    }
    return total-1; // Shouldn't get here!
  }

  private int[] GOODS_PROBABILITIES = new int[] {
    6,//Cheese
    2,//Guns
    3,//Pr0n
    8,//Scrap
    4,//Cigars
    2,//80's video games
    5,//Industrial chems
    6,//Space Ganj
    4,//Hoverboards
    3,//Partially fresh water
    3,//Hooch
    9,//Electronics
    7,//Beach towels
    2,//Religious artifacts
    1//AI Programs
  };

}
