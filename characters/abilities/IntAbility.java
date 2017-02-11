package characters.abilities;

public class IntAbility extends ValueAbility {

	int[] levelValues;

	public IntAbility(int id, String name, String description, String levelValueString) {
		super(id, name, description, levelValueString);
		retrieveIntegersFromString(levelValueString);
	}

	private void retrieveIntegersFromString(String levelValueString) {
		String[] strings = levelValueString.split(";");
		levelValues = new int[_levels];
		for (int i = 0; i < strings.length; i++) {
			levelValues[i] = Integer.parseInt(strings[i]);
		}
	}
}
