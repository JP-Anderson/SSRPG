package ship.weapons;

public abstract class ShipWeapon {

    public final String name;
    private WeaponType weaponType;
    private int cost;
    private int type;
    private int cooldown;
    private int hullDamage;
    private int shieldDamage;
    private int requiredWeaponModulePower;

    public ShipWeapon(String pName) {
        name = pName;
    }

    public enum WeaponType {
        LAZER, PLASMA, ROCKET
    }

}
