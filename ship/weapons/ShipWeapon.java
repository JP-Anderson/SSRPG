package ship.weapons;

// Will eventually generate these classes by csv, so all public fields will be final
public abstract class ShipWeapon {

    public final String name;
    public WeaponType weaponType;
    public int cost;
    public int cooldown;
    public int hullDamage;
    public int shieldDamage;
    public int requiredWeaponModulePower;

    public ShipWeapon(String pName) {
        name = pName;
    }

    public enum WeaponType {
        LAZER, PLASMA, ROCKET
    }

}
