package ship.modules;

import arch.view.View;
import goods.Goods;
import goods.PurchasedGoods;

import java.util.List;
import java.util.ArrayList;

public class CargoBayModule extends MannableShipModule {

	private int capacity;
	private int filledCapacity;
	private List<PurchasedGoods> cargo;

	public CargoBayModule(View view, String moduleName, int powerRequirement, int newCapacity) {
		super(view, moduleName, ShipModuleType.CARGO, powerRequirement);
		capacity = newCapacity;
		filledCapacity = 0;
		cargo = new ArrayList<PurchasedGoods>();
	}

	@Override
	public String moduleTypeString() {
		return "CargoBayModule";
	}

	public List<PurchasedGoods> getCargo() {
		return cargo;
	}

	public boolean addCargo(PurchasedGoods newGoods) {
		int amountOfNewCargo = newGoods.getQuantity();
		if (filledCapacity + amountOfNewCargo <= capacity) {
			cargo.add(newGoods);
			filledCapacity = filledCapacity + amountOfNewCargo;
			return true;
		}
		return false;
	}

	public void removeCargo(int cargoIndex, int unitsToRemove) {
		PurchasedGoods goods = cargo.get(cargoIndex);
		filledCapacity = filledCapacity - unitsToRemove;
		goods.setQuantity(goods.getQuantity() - unitsToRemove);
	}

	public void removeCargo(int cargoIndex) {
		PurchasedGoods goods = cargo.get(cargoIndex);
		filledCapacity = filledCapacity - goods.getQuantity();
		cargo.remove(cargoIndex);
	}

	public boolean isFull() {
		return capacity <= filledCapacity;
	}

	public int getFilledCapacity() {
		return filledCapacity;
	}

	public int getMaxCapacity() {
		return capacity;
	}

	public void setCapacity(int newCapacity) {
		if (newCapacity >= filledCapacity) {
			capacity = newCapacity;
		}
	}

	public double contrabandCargoRatio() {
		int contrabandCount = 0;
		for (Goods good : cargo) {
			if (!good.legal) contrabandCount++;
		}
		if (cargo.size() == 0) return 0;
		return (double) contrabandCount/cargo.size();
	}

}
