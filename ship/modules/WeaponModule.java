package ship.modules;

import ship.weapons.*;

public class WeaponModule extends ShipModule {

    public final int power;
    private ShipWeapon loadedWeapon;

    public WeaponModule(String weaponName, int weaponPower) {
        super(weaponName);
        power = weaponPower;
    }

    @Override
    public void printInformation() {
        System.out.println(" - WEAPON MODULE ["+name+"]");
        System.out.println("  + POWER ["+power+"]");
    }

    public void setWeapon(ShipWeapon newWeapon) { loadedWeapon = newWeapon; }

    public ShipWeapon getWeapon() { return loadedWeapon; }

}
