package arch.sessions;

import java.util.List;

import arch.interfaces.TradeSessionInterface;
import goods.GoodsForSale;
import goods.PurchasedGoods;
import map.gridsquares.Planet;
import ship.PlayerShip;
import ship.modules.CargoBayModule;

public class TradeSession implements TradeSessionInterface {
	
	PlayerShip player;
	Planet planet;
	
	@Override
	public void start(PlayerShip player, Planet planet) {
		this.player = player;
		this.planet = planet;
	}

	@Override
	public List<GoodsForSale> viewGoodsAvailableToBuy() {
		return planet.market.availableGoods;
	}

	@Override
	public boolean tryToBuyGoods(GoodsForSale goods, int amount) {
		CargoBayModule playerCargo = player.getCargoBay();
		int cargoSize = playerCargo.getFilledCapacity();
		int cargoMaxSize = playerCargo.getMaxCapacity();
		
		int goodsValue = goods.getPurchaseValue();
		int numberCanAfford = player.getMoney() / goodsValue;
		
		if (amount <= numberCanAfford && amount > 0) {
			if (cargoSize + amount <= cargoMaxSize) {
				int totalCost = goodsValue * amount;
				playerCargo.addCargo(new PurchasedGoods(goods, amount, planet.gridPoint));
				player.setMoney(player.getMoney() - totalCost);
				System.out.println("Bought " + amount + " " + goods.name + " for " + totalCost + " CREDS.");
				return true;
			} else {
				System.out.println("Not enough cargo space.");
			}
		} else if (amount == 0) {
			System.out.println("You buy nothing.");
		} else {
			System.out.println("You cannot afford that amount of " + goods.name);
		}
		return false;
	}

	@Override
	public List<PurchasedGoods> viewPlayersGoodsToSell() {
		return player.getCargoBay().getCargo();
	}

	@Override
	public boolean tryToSellGoods(PurchasedGoods goods, int amount) {
		int quantityOwned = goods.getQuantity();
		int localValueOfGoods = planet.market.getValueForSpecificGoods(goods);
		int profit = localValueOfGoods - goods.purchasedValue;

		System.out.println("How many units would you like to sell?");
		System.out.println("--> You can sell " + quantityOwned);

		if (profit > 0) {
			System.out.println("--> You will make a profit on this sale.");
		} else if (profit < 0) {
			System.out.println("--> You will NOT profit on this sale.");
		} else if (profit == 0) {
			System.out.println("--> You will break even on this sale.");
		}

		if (amount <= quantityOwned) {
			int totalMoneyMade = localValueOfGoods * amount;
			player.setMoney(player.getMoney() + totalMoneyMade);
			CargoBayModule playerCargo = player.getCargoBay();
			if (amount == quantityOwned) {
				playerCargo.removeCargo(playerCargo.getCargo().indexOf(goods));
			} else {
				playerCargo.removeCargo(playerCargo.getCargo().indexOf(goods), amount);
			}
			System.out.println("Sold " + amount + " " + goods.name + " for " + totalMoneyMade + " CREDS.");
			return true;
		} else System.out.println("You don't have that much " + goods.name);

		return false;
	}
	

}
