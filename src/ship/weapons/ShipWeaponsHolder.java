package ship.weapons;

import util.dataload.csv.CSV;
import util.dataload.csv.CSVReader;

import java.util.ArrayList;
import java.util.HashMap;

public class ShipWeaponsHolder {

	private static ShipWeaponsHolder instance;

	private static HashMap<Integer, ShipWeapon> weaponsById = null;
	private static HashMap<String, ShipWeapon> weaponsByName = null;

	private ShipWeaponsHolder() {}

	public static ShipWeapon getWeapon(String name) {
		getWeapons("weapons");
		return weaponsByName.get(name);
	}

	public static ShipWeapon getWeapon(int id) {
		getWeapons("weapons");
		return weaponsById.get(id);
	}

	private static ShipWeaponsHolder getWeapons(String filename) {
		if (instance == null) {
			loadWeapons(filename);
		}
		return instance;
	}

	private static void loadWeapons(String filename) {
		instance = new ShipWeaponsHolder();
		CSV weaponCsv = CSVReader.readCSV("weapons");
		weaponsById = new HashMap<>();
		weaponsByName = new HashMap<>();
		for (int i = 0; i < weaponCsv.rows-1; i++) {
			ArrayList<String> values = weaponCsv.getZeroIndexedRow(i+1);
			ShipWeapon weapon = new ShipWeapon(
					Integer.parseInt(values.get(0)),
					values.get(1),
					ShipWeapon.WeaponType.values()[Integer.parseInt(values.get(2))],
					Integer.parseInt(values.get(3)),
					Integer.parseInt(values.get(4)),
					Integer.parseInt(values.get(5)),
					Integer.parseInt(values.get(6)),
					Double.parseDouble(values.get(7)),
					Integer.parseInt(values.get(8)));
			weaponsById.put(weapon.id, weapon);
			weaponsByName.put(weapon.name, weapon);
		}
	}

}
