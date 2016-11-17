package ship.ship_battle;

import ship.weapons.Attack;

// This class will be provided an Attack: the chance of hitting the target,
// critical chance, and target damage will then be calculated in this class.
public class ShipAttackAttempt {

    public final int shieldDamage;
    public final int hullDamage;

    public static ShipAttackAttempt generateAttack(Attack attackAttempt) {
        return new ShipAttackAttempt(
            attackAttempt.shieldDamage,attackAttempt.hullDamage);

    }

    private ShipAttackAttempt(int shield, int hull) {
        shieldDamage = shield;
        hullDamage = hull;
    }

}
