package characters.skills.abilities;

import characters.exceptions.AbilityNotUnlockedException;

public class IntAbility extends ValueAbility {

	int[] levelValues;

	public IntAbility(int id, String name, String description, String levelValueString) {
		super(id, name, description, levelValueString);
		retrieveIntegersFromString(levelValueString);
	}

	@Override
	public Integer getAbilityValue() {
		if (isUnlocked()) return levelValues[abilityLevel-1];
		else throw new AbilityNotUnlockedException(_name);
	}

	private void retrieveIntegersFromString(String levelValueString) {
		String[] strings = levelValueString.split(";");
		levelValues = new int[_levels];
		for (int i = 0; i < strings.length; i++) {
			levelValues[i] = Integer.parseInt(strings[i]);
		}
	}
}
