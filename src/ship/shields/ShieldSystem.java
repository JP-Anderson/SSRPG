package ship.shields;

import ship.weapons.Attack;
import ship.weapons.ShipWeapon;

public abstract class ShieldSystem {

	public final String name;
	private int cost;

	private int maxTurnsTilRecharge;
	private int actualTurnsTilRecharge;
	private int rechargePerTurn;

	private int maxShields;
	private int remainingShields;
	private double hullDamageAbsorption;
	private ShieldState shieldState;
	private int requiredShieldModulePower;

	public enum ShieldState {
		CHARGED, DAMAGED, DEPLETED, RECHARGING
	}

	public ShieldSystem(String newName,
						int newCost,
						int newRechargeTurns,
						int newRechargePerTurn,
						int newMax,
						double newHullDamageAbsorption,
						int requiredPower) {
		name = newName;
		cost = newCost;
		maxTurnsTilRecharge = newRechargeTurns;
		rechargePerTurn = newRechargePerTurn;
		actualTurnsTilRecharge = 0;
		maxShields = newMax;
		hullDamageAbsorption = newHullDamageAbsorption;
		remainingShields = maxShields;
		shieldState = ShieldState.CHARGED;
		requiredShieldModulePower = requiredPower;
	}

	public Attack attemptToShieldAttack(Attack attack) {
		if (attack.weaponType == ShipWeapon.WeaponType.ROCKET) {
			remainingShields = remainingShields - attack.shieldDamage;
			updateShieldStateAfterTakingDamage();
			return attack;
		} else if (remainingShields == 0) {
			updateShieldStateAfterTakingDamage();
			return attack;
		}
		Attack absorbedAttack = absorbAttack(attack);
		updateShieldStateAfterTakingDamage();
		return absorbedAttack;
	}

	private void updateShieldStateAfterTakingDamage() {
		if (remainingShields > 0) shieldState = ShieldState.DAMAGED;
		else shieldState = ShieldState.DEPLETED;
		actualTurnsTilRecharge = maxTurnsTilRecharge;
	}

	private Attack absorbAttack(Attack attack) {
		int absorbedHullDamage = (int) (attack.hullDamage * (1 - hullDamageAbsorption));
		remainingShields = remainingShields - attack.shieldDamage;
		if (remainingShields < 0) remainingShields = 0;
		return new Attack(
				attack.shieldDamage,
				absorbedHullDamage,
				attack.accuracy,
				attack.weaponType);
	}

	public void updateShieldStatusForNewRound() {
		if (--actualTurnsTilRecharge <= 0) {
			shieldState = ShieldState.RECHARGING;
			remainingShields += rechargePerTurn;
			if (remainingShields >= maxShields) {
				remainingShields = maxShields;
				shieldState = ShieldState.CHARGED;
			}
		}
	}

	public ShieldState getShieldState() {
		return shieldState;
	}

    /* Getters and Setters */

	public int getMaxShields() {
		return maxShields;
	}

	public int getRemainingShields() {
		return remainingShields;
	}

	public int getMaxTurnsTilRecharge() {
		return maxTurnsTilRecharge;
	}

	public int getActualTurnsTilRecharge() {
		return actualTurnsTilRecharge;
	}

	public int getRechargePerTurn() {
		return rechargePerTurn;
	}

	public double getHullDamageAbsorption() {
		return hullDamageAbsorption;
	}

}
