package characters.skills;

import characters.skills.abilities.AbilityTree;

public class Skill {

	public final int _id;
	public final String _name;
	public final AbilityTree _abilities;

	public Skill(int id, String name, AbilityTree abilities) {
		_id = id;
		_name = name;
		_abilities = abilities;
	}

}