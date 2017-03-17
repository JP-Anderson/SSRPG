package util.collections.tree;

import characters.skills.abilities.Ability;
import characters.skills.abilities.ValueAbility;

public class AbilitiesConsoleTreePrinter extends ConsoleTreePrinter<Ability> {

	StringBuilder stringBuilder = new StringBuilder();
	Ability currentAbility = null;

	@Override
	protected String getStringToPrint(Ability object) {
		stringBuilder.setLength(0);
		currentAbility = object;

		printNodeCount();
		printChar(' ');
		printAbilityName();
		printChar(' ');
		printChar('(');
		printAbilityProgress();
		printChar(')');
		return stringBuilder.toString();
	}

	private void printNodeCount() {
		stringBuilder.append("["+nodeCount+"]");
	}

	private void printAbilityName() {
		String abilityName = super.getStringToPrint(currentAbility);
		stringBuilder.append(abilityName+"");
	}

	private void printAbilityProgress() {
		if (determineObjectIsValueAbility(currentAbility)) {
			ValueAbility valueAbility = (ValueAbility) currentAbility;
			stringBuilder.append(valueAbility.getAbilityLevel() + "/" + valueAbility.getMaxAbilityLevel());
		} else {
			if (currentAbility.isUnlocked()) stringBuilder.append("1/1");
			else stringBuilder.append("0/1");
		}
	}

	private void printChar(char character) {
		stringBuilder.append(character);
	}

	private boolean determineObjectIsValueAbility(Ability object) {
		if (object instanceof ValueAbility) return true;
		return false;
	}

}


