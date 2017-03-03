package characters.exceptions;

public class NotEnoughSkillPointsException extends AbilityException {

	public final int requiredSkillPoints;
	public final int actualSkillPoints;

	public NotEnoughSkillPointsException(int required, int actual) {
		requiredSkillPoints = required;
		actualSkillPoints = actual;
	}

	@Override
	public String getMessage() {
		return "Ability cannot be upgraded, requires " + requiredSkillPoints + " and only has " + actualSkillPoints + " available.";
	}

}
