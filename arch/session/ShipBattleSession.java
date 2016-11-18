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
        int TURNS = 9;
        for (int i = 0; i < TURNS; i++) nextTurn();

    }

    private void nextTurn() {
        Turn turn = null;
        if (currentActiveShip instanceof Ship) turn = new PlayerTurn();
        else if (currentActiveShip instanceof EnemyShip) turn = new CPUTurn();
        turn.runTurn();
        currentActiveShip = currentActiveShip == ship1 ? ship2: ship1;
    }

    abstract class Turn {

        void runTurn() {
            defencePhase();
            if (weaponCheckPhase()) {
                attackPhase();
            }
        }

        void defencePhase() {
            // Use ShieldModule here and recharge shields if necessary
            System.out.println("Defence phase.");
        }

        boolean weaponCheckPhase() {
            for (WeaponModule m : currentActiveShip.getWeaponModules()) {
                if (m.getWeapon() != null) {
                    ShipWeapon w = m.getWeapon();
                    if (m.getTurnsTilWeaponReady() == 0) {
                        Attack a = m.attack();
                        System.out.println(a.hullDamage +","+ a.shieldDamage +","+ a.accuracy);
                        m.resetTurnsTilWeaponReady();
                        return true;
                    } else {
                        System.out.println(w.name + " will be ready to fire in "
                            + m.getTurnsTilWeaponReady() + " turns.");
                        System.out.println(m.getTurnsTilWeaponReady());
                        m.decrementTurnsTilWeaponReady();
                    }
                }
            }
            return false;
        }
        abstract void attackPhase();
    }

    class PlayerTurn extends Turn {

        void attackPhase() {
            String input = ConsoleInputHandler.getStringFromUser("Hello: ");
            System.out.println("Player will attack if possible in this turn.");
        }

    }

    class CPUTurn extends Turn {

        void attackPhase() {
            System.out.println("CPU controlled enemy will attack if possible this turn.");
        }

    }

}
