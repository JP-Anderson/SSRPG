package characters.exceptions;

public class AbilityAtMaxLevelException extends AbilityException {

	public final int maxLevelHit;

	public AbilityAtMaxLevelException(int max) {
		maxLevelHit = max;
	}

	@Override
	public String getMessage() {
		return "Ability is already at the max level of " + maxLevelHit + ".";
	}

}
