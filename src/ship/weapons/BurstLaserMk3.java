package ship.weapons;

// Temporary class for testing.
// Will add a Weapon csv and read in weapons using a CSVReader
public class BurstLaserMk3 extends ShipWeapon {

    public BurstLaserMk3() {
        super("BurstLaserMk3");
        cost = 100;
        cooldown = 2;
        hullDamage = 100;
        baseHitChance = 0.4;
        shieldDamage = 120;
        requiredWeaponModulePower = 2;
        weaponType = ShipWeapon.WeaponType.LAZER;
    }

}
