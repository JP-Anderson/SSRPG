package map.gridsquares;

import map.GridPoint;
import map.Market;

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
		//TODO: Should this be a parameter?
		// Might be redundant param as most planets will be either tradeable or landable.
		// Possibly need to introduce XML for storing planets and make this optional and set on instantation
		isLandable = true;
		isTradeable = true;
	}

}
