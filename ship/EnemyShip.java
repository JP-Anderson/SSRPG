package ship;

public class EnemyShip extends Ship {

    public EnemyShip(String name) {
        super(name);
        maxHullIntegrity = 100;
        remainingHullIntegrity = 100;
    }


}
