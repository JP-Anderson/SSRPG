package map;

public class Planet extends GridSquare {

	public final int id;
	public final String name;
	public final int marketSize;
	public final Market market;

	public Planet(int pId, String pName, GridPoint gPoint, int pMarketSize) {
		super(gPoint, pName.charAt(0));
		id = pId;
		name = pName;
		marketSize = pMarketSize;
		market = Market.getMarket(marketSize, pId);
	}

}
