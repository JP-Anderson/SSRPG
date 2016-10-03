package ship;

import ship.weapons.*;

public class WeaponModule implements ShipModule {

    public final String name;
    public final int power;
    private ShipWeapon loadedWeapon;

    public WeaponModule(String engineName, int weaponPower) {
        name = engineName;
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
