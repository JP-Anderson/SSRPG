package ship.shields;

public abstract class ShieldSystem {
    public final String name;
    public int cost;
    public int turnsTilRecharge;
    // Could recharge per turn be a double
    public int rechargePerTurn;
    public int maxShields;
    public int remainingShields;
    public double hullDamageAbsorption;
    public ShieldState shieldState;
    public int requiredShieldModulePower;

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
        turnsTilRecharge = newRechargeTurns;
        rechargePerTurn = newRechargePerTurn;
        maxShields = newMax;
        hullDamageAbsorption = newHullDamageAbsorption;
        remainingShields = maxShields;
        shieldState = ShieldState.CHARGED;
        requiredShieldModulePower = requiredPower;
    }

}
