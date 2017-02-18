package ship;

public class EnemyShip extends Ship {

    public EnemyShip(String name, ShipModules modules) {
        super(name, modules);
        maxHullIntegrity = 100;
        remainingHullIntegrity = 100;
    }


}
