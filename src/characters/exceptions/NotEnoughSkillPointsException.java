package characters.exceptions;

public class NotEnoughSkillPointsException extends RuntimeException {

	public final int requiredSkillPoints;
	public final int actualSkillPoints;

	public NotEnoughSkillPointsException(int required, int actual) {
		requiredSkillPoints = required;
		actualSkillPoints = actual;
	}

}
