package ship.modules;

import arch.view.ConsoleIOHandler;
import goods.GoodsForSale;
import goods.GoodsList;
import goods.PurchasedGoods;
import map.GridPoint;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class CargoBayModuleTest {

	private static ConsoleIOHandler consoleIOHandler = new ConsoleIOHandler();
	private static GoodsForSale[] goodsList = GoodsList.GOODS;

	private CargoBayModule getCargoBayModule() {
		return new CargoBayModule(consoleIOHandler, "TestCargoBayModule", 3, 20);
	}

	private PurchasedGoods purchaseGoods(int goodsIndex, int amountPurchased) {
		return new PurchasedGoods(goodsList[goodsIndex], amountPurchased, new GridPoint(1,2));
	}

	@Test
	public void addCargoSingleItem() {
		CargoBayModule cargoBayModule = getCargoBayModule();

		assertEquals(cargoBayModule.getFilledCapacity(), 0);

		cargoBayModule.addCargo(purchaseGoods(1,1));

		assertEquals(cargoBayModule.getFilledCapacity(), 1);
	}

	@Test
	public void addCargoMultipleItems() {
		CargoBayModule cargoBayModule = getCargoBayModule();

		assertEquals(cargoBayModule.getFilledCapacity(), 0);

		cargoBayModule.addCargo(purchaseGoods(1,12));

		assertEquals(cargoBayModule.getFilledCapacity(), 12);
	}

	@Test
	public void addCargoCantExceedCapacity() {
		CargoBayModule cargoBayModule = getCargoBayModule();

		cargoBayModule.addCargo(purchaseGoods(1,15));

		assertEquals(cargoBayModule.getFilledCapacity(), 15);

		assertFalse(cargoBayModule.addCargo(purchaseGoods(3,6)));
		assertEquals(cargoBayModule.getFilledCapacity(), 15);
	}

	@Test
	public void addCargoCanAddCargoUpToCorrectCapacity() {
		CargoBayModule cargoBayModule = getCargoBayModule();

		cargoBayModule.addCargo(purchaseGoods(2,1));

		assertEquals(cargoBayModule.getFilledCapacity(), 1);

		cargoBayModule.addCargo(purchaseGoods(5,19));

		assertEquals(cargoBayModule.getFilledCapacity(), cargoBayModule.getMaxCapacity());
		assertTrue(cargoBayModule.isFull());
	}

	@Test
	public void addCargoReturnsFalseWhenCargoBayIsFull() {
		CargoBayModule cargoBayModule = getCargoBayModule();

		cargoBayModule.addCargo(purchaseGoods(2,14));
		cargoBayModule.addCargo(purchaseGoods(8,1));
		cargoBayModule.addCargo(purchaseGoods(2,5));

		assertEquals(cargoBayModule.getFilledCapacity(), cargoBayModule.getMaxCapacity());
		assertTrue(cargoBayModule.isFull());

		assertFalse(cargoBayModule.addCargo(purchaseGoods(1,1)));
		assertFalse(cargoBayModule.addCargo(purchaseGoods(1,12)));
	}

	@Test
	public void removeCargoAtIndexRemovesAllGoodsOfType() {
		CargoBayModule cargoBayModule = getCargoBayModule();
		cargoBayModule.addCargo(purchaseGoods(1,10));

		assertEquals(cargoBayModule.getFilledCapacity(),10);
		cargoBayModule.removeCargo(0);
		assertEquals(cargoBayModule.getFilledCapacity(),0);
	}

	@Test
	public void removeCargoAtIndexRemovesSingleGoods() {
		CargoBayModule cargoBayModule = getCargoBayModule();
		cargoBayModule.addCargo(purchaseGoods(1,1));

		assertEquals(cargoBayModule.getFilledCapacity(), 1);

		cargoBayModule.removeCargo(0);
		assertEquals(cargoBayModule.getFilledCapacity(), 0);
	}

	@Test
	public void removeCargoCanRemoveMultipleGoods() {
		CargoBayModule cargoBayModule = getCargoBayModule();
		cargoBayModule.addCargo(purchaseGoods(1,5));

		assertEquals(cargoBayModule.getFilledCapacity(), 5);

		cargoBayModule.removeCargo(0, 4);
		assertEquals(cargoBayModule.getFilledCapacity(), 1);
	}

	@Test
	public void isFullReturnsTrueWhenShipIsFull() {
		CargoBayModule cargoBayModule = getCargoBayModule();
		cargoBayModule.addCargo(purchaseGoods(0,20));

		assertTrue(cargoBayModule.isFull());
		assertEquals(cargoBayModule.getFilledCapacity(), cargoBayModule.getMaxCapacity());
	}

	@Test
	public void isFullReturnsFalseWhenShipIsNotFull() {
		CargoBayModule cargoBayModule = getCargoBayModule();

		assertFalse(cargoBayModule.isFull());

		cargoBayModule.addCargo(purchaseGoods(0,19));

		assertFalse(cargoBayModule.isFull());
	}

	@Test
	public void contrabandCargoRatioReturnsCorrectRatioWithContraband() {
		CargoBayModule cargoBayModule = getCargoBayModule();

		// Five Illegal goods
		cargoBayModule.addCargo(purchaseGoods(1,4));
		cargoBayModule.addCargo(purchaseGoods(2,1));

		// Four Legal goods
		cargoBayModule.addCargo(purchaseGoods(0,4));

		assertEquals(0.6666666666666666, cargoBayModule.contrabandCargoRatio());
	}

	@Test
	public void contrabandCargoRatioReturnsZeroWithNoContraband() {
		CargoBayModule cargoBayModule = getCargoBayModule();

		// Four Legal goods
		cargoBayModule.addCargo(purchaseGoods(0,4));

		assertEquals(0.0, cargoBayModule.contrabandCargoRatio());
	}

	@Test
	public void contrabandCargoRatioReturnsZeroWithNoCargo() {
		CargoBayModule cargoBayModule = getCargoBayModule();

		assertEquals(0.0, cargoBayModule.contrabandCargoRatio());
	}

}