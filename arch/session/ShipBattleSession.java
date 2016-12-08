package arch.session;

import util.*;
import ship.AbstractShip;
import ship.Ship;
import ship.EnemyShip;
import ship.modules.*;
import ship.weapons.*;
import arch.view.ShipBattleView;

import java.util.ArrayList;

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
            m.setWeapon(new BurstLaserMk3());
        }
        currentActiveShip = ship1;
    }

    @Override
    public void run() {
        while(checkFightersAlive()) {
            nextTurn();
            try {
                Thread.sleep(1500);
            } catch(InterruptedException e){
                System.out.println("got interrupted!");
            }
        }
        System.out.println("Fight over.");

    }

    private boolean checkFightersAlive() {
        return !ship1.isDestroyed() && !ship2.isDestroyed();
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
            currentActiveShip.rechargeShields();
            System.out.println("Defence phase.");
        }

        boolean weaponCheckPhase() {
            for (WeaponModule m : currentActiveShip.getWeaponModules()) {
                if (m.getWeapon() != null) {
                    if (m.getTurnsTilWeaponReady() == 0) {
                        return true;
                    } else {
                        System.out.println(m.getWeapon().name + " will be ready to fire in "
                            + m.getTurnsTilWeaponReady() + " turns.");
                        System.out.println(m.getTurnsTilWeaponReady());
                        m.decrementTurnsTilWeaponReady();
                    }
                }
            }
            return false;
        }

        void attackPhase() {
            ArrayList<WeaponModule> readyWeapons = new ArrayList<>();
            if (isPlayer()) System.out.println("Charged weapons:");
            int i = 0;
            for (WeaponModule m : currentActiveShip.getWeaponModules()) {
                if (m.getWeapon() != null) {
                    if (m.getTurnsTilWeaponReady() == 0) {
                        readyWeapons.add(m);
                        if (isPlayer()) System.out.println("("+i+") " + m.getWeapon().name);
                        i++;
                    }
                }
            }

            int weaponCount = readyWeapons.size();
            int choice = chooseWeaponAtIndex(weaponCount);

            WeaponModule m = readyWeapons.get(choice);
            Attack a = m.attack();
            if (a != null) {
                System.out.println(a.hullDamage + "," + a.shieldDamage + "," + a.accuracy);
                AbstractShip shipToAttack = currentActiveShip == ship1 ? ship2 : ship1;
                shipToAttack.sustainFire(a);
            }
            m.resetTurnsTilWeaponReady();
        }

        abstract int chooseWeaponAtIndex(int weaponCount);

        abstract boolean isPlayer();

    }

    class PlayerTurn extends Turn {

        @Override
        int chooseWeaponAtIndex(int weaponCount) {
            return ConsoleInputHandler.getIntInRangeFromUser(weaponCount);
        }

        @Override
        boolean isPlayer() { return true; }

    }

    class CPUTurn extends Turn {

        @Override
        int chooseWeaponAtIndex(int weaponCount) {
            return 0;
        }

        @Override
        boolean isPlayer() { return false; }

    }

}
