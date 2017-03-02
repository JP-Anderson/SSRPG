package characters.exceptions;

public class AbilityAtMaxLevelException extends RuntimeException {

	public final int maxLevelHit;

	public AbilityAtMaxLevelException(int max) {
		maxLevelHit = max;
	}

}
