package arch.session;

import arch.view.ConsoleIOHandler;
import ship.AIShip;
import ship.CombatSequencer;
import ship.Ship;
import ship.PlayerShip;
import ship.modules.*;
import ship.weapons.*;
import util.rng.RNG;

import java.util.ArrayList;

/*
 * THIS SESSION PACKAGE IS OUTDATED
 * SEE SESSIONS PACKAGE FOR INTERFACES AND CLASSES NEEDED FOR USE IN GDX VERSION OF GAME
 */

public class ShipBattleSession extends Session {

	private final Ship ship1;
	private final Ship ship2;

	private RNG rng = new RNG();

	private Ship currentActiveShip;

	public ShipBattleSession(ConsoleIOHandler injectedView, Ship newShip1, Ship newShip2) {
		super(injectedView, "ShipBattleSession");
		ship1 = newShip1;
		ship1.addWeaponModule(3);
		ship2 = newShip2;

		for (WeaponModule m : ship1.getWeaponModules()) {
			m.setWeapon(ShipWeaponsHolder.getWeapon("Rail Gun"));
		}

		currentActiveShip = determineWhichShipGetsFirstTurn();
		view.outputHandler.sendStringToView(currentActiveShip.name + " is going first.");
	}

	private Ship determineWhichShipGetsFirstTurn() {
		return ship1.getScoreForFirstTurnChance() <= ship2.getScoreForFirstTurnChance()
				? ship2
				: ship1;
	}

	@Override
	public void run() {
		while (checkFightersAlive()) {
			nextTurn();
			try {
				Thread.sleep(1500);
			} catch (InterruptedException e) {
				view.outputHandler.sendStringToView("got interrupted!");
			}
		}
		view.outputHandler.sendStringToView("Fight over.");

	}

	private boolean checkFightersAlive() {
		return !ship1.isDestroyed() && !ship2.isDestroyed();
	}

	private void nextTurn() {
		Turn turn = null;
		if (currentActiveShip instanceof PlayerShip) turn = new PlayerTurn();
		else if (currentActiveShip instanceof AIShip) turn = new CPUTurn();
		turn.runTurn();
		currentActiveShip = currentActiveShip == ship1 ? ship2 : ship1;
	}

	abstract class Turn {

		CombatSequencer combatSequencer;

		void runTurn() {
			moduleSequencePhase();
			defencePhase();
			if (weaponCheckPhase()) {
				attackPhase();
			}
		}

		void moduleSequencePhase(){
			currentActiveShip.getModulesForCombat().chargeNextModule();
		}

		void defencePhase() {
			currentActiveShip.rechargeShields();
			view.outputHandler.sendStringToView("Defence phase.");
		}

		boolean weaponCheckPhase() {
			for (WeaponModule m : currentActiveShip.getWeaponModules()) {
				if (m.getWeapon() != null) {
					if (m.isReadyToFire() && m.moduleIsCharged()) {
						return true;
					} else {
						view.outputHandler.sendStringToView(m.getWeapon().name + " will be ready to fire in "
								+ m.getBaseTurnsTilWeaponReady() + " turns.");
						view.outputHandler.sendStringToView(Integer.toString(m.getBaseTurnsTilWeaponReady()));
						m.decrementTurnsTilWeaponReady();
					}
				}
			}
			return false;
		}

		void attackPhase() {
			ArrayList<WeaponModule> readyWeapons = new ArrayList<>();
			if (isPlayer()) view.outputHandler.sendStringToView("Charged weapons:");
			int i = 0;
			for (WeaponModule m : currentActiveShip.getWeaponModules()) {
				if (m.getWeapon() != null) {
					if (m.getBaseTurnsTilWeaponReady() == 0) {
						readyWeapons.add(m);
						if (isPlayer()) view.outputHandler.sendStringToView("(" + i + ") " + m.getWeapon().name);
						i++;
					}
				}
			}

			int weaponCount = readyWeapons.size();
			int choice = chooseWeaponAtIndex(weaponCount);

			WeaponModule m = readyWeapons.get(choice);
			Attack a = m.attack(rand);
			if (a != null) {
				view.outputHandler.sendStringToView(a.hullDamage + "," + a.shieldDamage + "," + a.accuracy);
				Ship shipToAttack = currentActiveShip == ship1 ? ship2 : ship1;
				shipToAttack.sustainFire(a, rng);
			}
			m.resetBaseTurnsTilWeaponReady();
		}

		abstract int chooseWeaponAtIndex(int weaponCount);

		abstract boolean isPlayer();

	}

	class PlayerTurn extends Turn {

		@Override
		int chooseWeaponAtIndex(int weaponCount) {
			return view.inputHandler.getIntInRangeFromUser(weaponCount);
		}

		@Override
		boolean isPlayer() {
			return true;
		}

	}

	class CPUTurn extends Turn {

		@Override
		int chooseWeaponAtIndex(int weaponCount) {
			return 0;
		}

		@Override
		boolean isPlayer() {
			return false;
		}

	}

}
