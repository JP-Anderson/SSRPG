package characters.exceptions;

public class AbilityNotUnlockedException extends AbilityException {

	public final String abilityName;

	public AbilityNotUnlockedException(String abilityName) {
		this.abilityName = abilityName;
	}

	@Override
	public String getMessage() {
		return "Ability " + abilityName + " has not been unlocked.";
	}

}
