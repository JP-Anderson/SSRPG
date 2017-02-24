package goods;

public class GoodsForSale extends Goods {

	private int purchaseValue;

	public GoodsForSale(int iD, String pName, boolean areGoodsLegal, int pBaseValue) {
		super(iD, pName, areGoodsLegal, pBaseValue);
	}

	public GoodsForSale generateGoodsMarketValue(int marketProbability) {
		purchaseValue = calculateGoodsLocalValue(marketProbability);
		//System.out.println("Market value for " + name + " is " + actualValue + " from " + baseValue);
		return this;
	}

	public int getPurchaseValue() {
		return purchaseValue;
	}

}
