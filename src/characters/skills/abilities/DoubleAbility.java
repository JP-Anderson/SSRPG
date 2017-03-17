package characters.skills.abilities;

import characters.exceptions.AbilityNotUnlockedException;

public class DoubleAbility extends ValueAbility {

	double[] levelValues;

	public DoubleAbility(int id, String name, String description, String levelValueString) {
		super(id, name, description, levelValueString);
		retrieveDoublesFromString(levelValueString);
	}

	@Override
	public Double getAbilityValue() {
		if (isUnlocked()) return levelValues[abilityLevel-1];
		else throw new AbilityNotUnlockedException(_name);
	}

	private void retrieveDoublesFromString(String levelValueString) {
		String[] strings = levelValueString.split(";");
		levelValues = new double[levels];
		for (int i = 0; i < strings.length; i++) {
			levelValues[i] = Double.parseDouble(strings[i]);
		}
	}

}
