package characters.skills.abilities;

import characters.exceptions.AbilityAtMaxLevelException;

public class BooleanAbility extends Ability {

	public BooleanAbility(int id, String name, String description) {
		super(id, name, description);
	}

	@Override
	public boolean isFullyUnlocked() {
		return unlocked;
	}

	@Override
	public void levelUp() {
		if (!isUnlocked()) unlocked = true;
		else throw new AbilityAtMaxLevelException(1);
	}
}
