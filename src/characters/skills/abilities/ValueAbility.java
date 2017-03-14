package characters.skills.abilities;

import characters.exceptions.AbilityAtMaxLevelException;

public abstract class ValueAbility extends Ability {

	protected final int _levels;

	protected int abilityLevel = 0;

	protected String levelValues;

	public ValueAbility(int id, String name, String description, String levelValueString) {
		super(id, name, description);
		levelValues = levelValueString;
		_levels = countNumberOfValuesInStringList(levelValueString);
	}

	public abstract Number getAbilityValue();

	public int getAbilityLevel() {
		return abilityLevel;
	}

	public void levelUp() {
		if (!isFullyUnlocked()) {
			abilityLevel++;
			if (!isUnlocked()) unlocked = true;
		} else {
			throw new AbilityAtMaxLevelException(abilityLevel);
		}
	}

	@Override
	public boolean isUnlocked() {
		if (!unlocked) unlocked = abilityLevel > 0;
		return unlocked;
	}

	@Override
	public boolean isFullyUnlocked() {
		return abilityLevel == _levels;
	}

	/*
		This function counts the number of values in a semicolon delimited list
		i starts at 1 as all lists will contain one value, for each semicolon it adds to i
	 */
	private int countNumberOfValuesInStringList(String list) {
		int i = 1;
		for (char c : list.toCharArray()) if (c == ';') i++;
		return i;
	}

	public String getValuesString() {
		return levelValues;
	}

}
