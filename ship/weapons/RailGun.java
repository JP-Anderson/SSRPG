package ship.weapons;

// Temporary class for testing.
// Will add a Weapon csv and read in weapons using a CSVReader
public class RailGun extends ShipWeapon {

    public RailGun() {
        super("RailGun");
        cost = 300;
        cooldown = 4;
        hullDamage = 400;
        shieldDamage = 600;
        requiredWeaponModulePower = 3;
        weaponType = ShipWeapon.WeaponType.LAZER;
    }

}
