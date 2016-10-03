package ship;

import goods.PurchasedGoods;
import java.util.List;
import java.util.ArrayList;

public class CargoBay {

    private int capacity;
    private int filledCapacity;
    private List<PurchasedGoods> cargo;

    public CargoBay(int newCapacity) {
        capacity = newCapacity;
        filledCapacity = 0;
        cargo = new ArrayList<PurchasedGoods>();
    }

    public CargoBay(int newCapacity, List<PurchasedGoods> newCargo) {
        capacity = newCapacity;
        filledCapacity = newCargo.size();
        cargo = newCargo;
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

    public int getFilledCapacity() { return filledCapacity; }

    public int getMaxCapacity() { return capacity; }

    public void setCapacity(int newCapacity) {
        if (newCapacity >= filledCapacity) {
            capacity = newCapacity;
        }
    }

}
