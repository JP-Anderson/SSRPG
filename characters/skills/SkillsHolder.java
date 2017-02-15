package characters.skills;

import characters.skills.Skill;
import util.dataload.xml.SkillAndAbilityLoader;

import java.util.ArrayList;

public final class SkillsHolder {

	private SkillsHolder() {
		// private constructor, don't initialise this class
	}

	private static ArrayList<Skill> skills = null;

	public static ArrayList<Skill> getSkills() {
		if (skills == null) {
			skills = SkillAndAbilityLoader.loadSkills();
		}
		return skills;
	}

	public static Skill getSkillAtIndex(int index) {
		return skills != null ? skills.get(index) : getSkills().get(index);
	}

}
