public class Planet extends GridSquare {

  public final String name;
  public final int marketSize;
  public final Market market;

  public Planet(String pName, GridPoint gPoint, int pMarketSize) {
    super(gPoint,pName.charAt(0));
    name = pName;
    marketSize = pMarketSize;
    market = Market.getMarket(marketSize);
  }

}
