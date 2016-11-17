package arch.session;

import ship.AbstractShip;
import ship.Ship;
import ship.EnemyShip;
import arch.view.ShipBattleView;

public class ShipBattleSession extends Session {

    private final AbstractShip ship1;
    private final AbstractShip ship2;

    private AbstractShip currentActiveShip;

    private ShipBattleView view;

    public ShipBattleSession(AbstractShip newShip1, AbstractShip newShip2) {
        super("ShipBattleSession");
        ship1 = newShip1;
        ship2 = newShip2;
        currentActiveShip = ship1;
    }

    @Override
    public void run() {
        //TODO need to replace System calls with output handler class
        nextTurn();
        nextTurn();

    }

    private void nextTurn() {
        Turn turn = null;
        if (currentActiveShip instanceof Ship) turn = new PlayerTurn();
        else if (currentActiveShip instanceof EnemyShip) turn = new CPUTurn();
        turn.runTurn();
        currentActiveShip = currentActiveShip == ship1 ? ship2: ship1;
    }

    static abstract class Turn {
        void runTurn() {
            attackPhase();
        }

        abstract void attackPhase();
    }

    static class PlayerTurn extends Turn {

        void attackPhase() {
            System.out.println("Player will attack if possible in this turn.");
        }

    }

    static class CPUTurn extends Turn {

        void attackPhase() {
            System.out.println("CPU controlled enemy will attack if possible this turn.");
        }

    }

}
