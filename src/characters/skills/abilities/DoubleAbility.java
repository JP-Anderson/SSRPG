package characters.skills.abilities;

public class DoubleAbility extends ValueAbility {

	double[] levelValues;

	public DoubleAbility(int id, String name, String description, String levelValueString) {
		super(id, name, description, levelValueString);
		retrieveDoublesFromString(levelValueString);
	}

	private void retrieveDoublesFromString(String levelValueString) {
		String[] strings = levelValueString.split(";");
		levelValues = new double[_levels];
		for (int i = 0; i < strings.length; i++) {
			levelValues[i] = Double.parseDouble(strings[i]);
		}
	}

}
