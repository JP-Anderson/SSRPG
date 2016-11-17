package arch.session;

import util.*;
import ship.AbstractShip;
import ship.Ship;
import ship.EnemyShip;
import ship.modules.*;
import ship.weapons.*;
import arch.view.ShipBattleView;

public class ShipBattleSession extends Session {

    private final AbstractShip ship1;
    private final AbstractShip ship2;

    private AbstractShip currentActiveShip;

    private ShipBattleView view;

    public ShipBattleSession(AbstractShip newShip1, AbstractShip newShip2) {
        super("ShipBattleSession");
        ship1 = newShip1;
        ship1.addWeaponModule(3);
        ship2 = newShip2;
        ship2.addWeaponModule(2);

        for (WeaponModule m : ship1.getWeaponModules()) {
            m.setWeapon(new RailGun());
        }

        for (WeaponModule m : ship2.getWeaponModules()) {
            m.setWeapon(new RailGun());
        }

        for (WeaponModule m : ship2.getWeaponModules()) {
            m.setWeapon(new BurstLaserMk3());
        }
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
            defencePhase();
            attackPhase();
        }

        void defencePhase() {
            // Use ShieldModule here and recharge shields if necessary
            System.out.println("Defence phase.");
        }

        abstract void attackPhase();
    }

    static class PlayerTurn extends Turn {

        void attackPhase() {
            String input = ConsoleInputHandler.getStringFromUser("Hello: ");
            System.out.println("Player will attack if possible in this turn.");
        }

    }

    static class CPUTurn extends Turn {

        void attackPhase() {
            System.out.println("CPU controlled enemy will attack if possible this turn.");
        }

    }

}
