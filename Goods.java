public class Goods {

  public final int id;
  public final String name;
  public final boolean legal;
  public final int baseValue;
  private int actualValue;

  public Goods(int iD, String pName, boolean areGoodsLegal, int pBaseValue) {
    id = iD;
    name = pName;
    legal = areGoodsLegal;
    baseValue = pBaseValue;
  }

  public Goods generateGoodsMarketValue(int marketProbability) {
    double multiplier = 1.5;
    multiplier = multiplier - (double)marketProbability/10.0;
    actualValue = (int) (baseValue * multiplier);

    //System.out.println("Market value for " + name + " is " + actualValue + " from " + baseValue);
    return this;
  }

  public int getActualValue() { return actualValue; }

}
