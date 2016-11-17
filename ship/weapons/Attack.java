package ship.weapons;

public class Attack {

    public final int shieldDamage;
    public final int hullDamage;
    public final double accuracy;

    public Attack (int shield, int hull, double acc) {
        shieldDamage = shield;
        hullDamage = hull;
        accuracy = acc;
    }

}
