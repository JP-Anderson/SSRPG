package arch.interfaces;

import java.util.List;

import goods.GoodsForSale;
import goods.PurchasedGoods;
import map.gridsquares.Planet;
import ship.PlayerShip;

public interface TradeSessionInterface {
	
	public void start(PlayerShip player, Planet planet);

	public List<GoodsForSale> viewGoodsAvailableToBuy();
	
	public boolean tryToBuyGoods(GoodsForSale goods, int amount);
	
	public List<PurchasedGoods> viewPlayersGoodsToSell();
	
	public boolean tryToSellGoods(PurchasedGoods goods, int amount);

	
}
