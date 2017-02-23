package goods;

public abstract class Goods {

  public final int id;
  public final String name;
  public final boolean legal;
  public final int baseValue;

  public Goods(int iD, String pName, boolean areGoodsLegal, int pBaseValue) {
      id = iD;
      name = pName;
      legal = areGoodsLegal;
      baseValue = pBaseValue;
  }

  public int calculateGoodsLocalValue(int marketProbability) {
      double multiplier = 1.5;
      multiplier = multiplier - (double)marketProbability/10.0;
      return (int) (baseValue * multiplier);
  }

}
