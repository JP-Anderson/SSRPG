package characters.exceptions;

public class AbilityNotUnlockedException extends RuntimeException {

	public final String abilityName;

	public AbilityNotUnlockedException(String abilityName) {
		this.abilityName = abilityName;
	}
}
