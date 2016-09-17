package ship;

import goods.Goods;
import java.util.List;
import java.util.ArrayList;

public class CargoBay {

    private int capacity;
    private int filledCapacity;
    private List<Goods> cargo;

    public CargoBay(int newCapacity) {
        capacity = newCapacity;
        filledCapacity = 0;
        cargo = new ArrayList<Goods>();
    }

    public CargoBay(int newCapacity, List<Goods> newCargo) {
        capacity = newCapacity;
        filledCapacity = newCargo.size();
        cargo = newCargo;
    }

    public List<Goods> getCargo() {
        return cargo;
    }

    public boolean addCargo(Goods newGoods) {
        if (filledCapacity < capacity) {
            cargo.add(newGoods);
            filledCapacity++;
            return true;
        }
        return false;
    }

    public int getFilledCapacity() { return filledCapacity; }

    public int getMaxCapacity() { return capacity; }

    public void setCapacity(int newCapacity) {
        if (newCapacity >= filledCapacity) {
            capacity = newCapacity;
        }
    }

}
