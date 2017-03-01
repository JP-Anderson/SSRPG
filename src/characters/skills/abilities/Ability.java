package characters.skills.abilities;

public abstract class Ability {

	public final int _id;
	public final String _name;
	public final String _description;

	boolean unlocked;

	public Ability(int id, String name, String description) {
		_id = id;
		_name = name;
		_description = description;
		unlocked = false;
	}

	public boolean isUnlocked() {
		return unlocked;
	}

	public abstract boolean isFullyUnlocked();

	public abstract void levelUp();

}
