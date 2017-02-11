package characters.abilities;

/**
 * Created by Jp on 11/02/2017.
 */
public abstract class ValueAbility extends Ability {

	public final int _levels;

	private int abilityLevel = 0;

	public ValueAbility(int id, String name, String description, String levelValueString) {
		super(id, name, description);
		_levels = countNumberOfValuesInStringList(levelValueString);
	}

	public int getAbilityLevel() {
		return abilityLevel;
	}

	public void levelUp() {
		if (abilityLevel < _levels) {
			abilityLevel++;
		}
	}

	@Override
	public boolean isUnlocked() {
		return abilityLevel > 0;
	}

	/*
		This function counts the number of values in a semicolon delimited list
		i starts at 1 as all lists will contain one value, for each semicolon it adds to i
	 */
	private static int countNumberOfValuesInStringList(String list) {
		int i = 1;
		for (char c : list.toCharArray()) if (c == ';') i++;
		return i;
	}

}
