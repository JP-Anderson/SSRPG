package characters.skills.abilities;

public class BooleanAbility extends Ability {

	public BooleanAbility(int id, String name, String description) {
		super(id, name, description);
	}

	@Override
	public void levelUp() {
		unlocked = true;
	}
}
