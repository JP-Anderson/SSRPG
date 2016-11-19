package ship.modules;

import ship.weapons.Attack;
import ship.weapons.ShipWeapon.WeaponType;
import ship.shields.ShieldSystem;

public class ShieldModule extends ShipModule {

    public final int maxShieldPowerSupported;
    private ShieldSystem shields;

    public ShieldModule(int maxShieldPower) {
        maxShieldPowerSupported = maxShieldPower;
        shields = null;
    }

    public ShieldSystem shields() {
        return shields;
    }

    public void shields(ShieldSystem newShields) {
        shields = newShields;
    }

    @Override
    public void printInformation() {
        if (shields == null) {
            System.out.println("Empty Shield Module ... PWR = ["+maxShieldPowerSupported+"]");
            System.out.println("No shields.");
        } else {
            System.out.println(" - SHIELD MODULE ["+shields.name+"]");
            System.out.println("  + SHIELD POWER ["+maxShieldPowerSupported+"]");
            System.out.println("  + RMNG SHIELDS ["+shields.remainingShields+"]");
            System.out.println("  + MAXM SHIELDS ["+shields.maxShields+"]");

        }
    }

    public Attack shieldAttack(Attack attack) {
        Attack shieldedAttack = null;
        if (attack.weaponType == WeaponType.ROCKET) {
            shieldedAttack = new Attack(
                attack.shieldDamage,
                attack.hullDamage,
                attack.accuracy,
                attack.weaponType);
        } else {
            int absorbedHullDamage = attack.hullDamage;
            if (shields.remainingShields == 0) {
                // Here we will need to reset the number of turns til Shields can charge
                // Or set the shield to depleted/damaged if it isn't already
            } else if (shields.remainingShields > attack.shieldDamage) {
                absorbedHullDamage = (int)(attack.hullDamage*shields.hullDamageAbsorption);
            } else {
                absorbedHullDamage = (int)(attack.hullDamage*0.6);
            }
            shields.remainingShields = shields.remainingShields - attack.shieldDamage;
            if (shields.remainingShields < 0) shields.remainingShields = 0;
            shieldedAttack = new Attack(
                attack.shieldDamage,
                absorbedHullDamage,
                attack.accuracy,
                attack.weaponType);

        }
        return shieldedAttack;
    }

}
