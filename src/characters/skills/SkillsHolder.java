package characters.skills;

import util.dataload.xml.SkillAndAbilityLoader;

import java.util.ArrayList;
import java.util.HashMap;

public final class SkillsHolder {

	public static final HashMap<String, Integer> SKILLS_ENUM = new HashMap<>();

	public static final HashMap<String, Integer> ABILITY_ENUM = new HashMap<>();

	private SkillsHolder() {
		// private constructor, don't initialise this class
	}

	private static ArrayList<Skill> skills = null;

	private static void loadSkills() {
		skills = SkillAndAbilityLoader.loadSkills();
	}

	public static ArrayList<Skill> getSkills() {
		if (skills == null) loadSkills();
		return skills;
	}

	public static Skill getSkill(String skillName) {
		if (skills == null) loadSkills();
		for (Skill s : skills) {
			if (s._name.equals(skillName)) return new SkillCopier().copySkill(s);
		}
		return null;
	}

	public static Skill getSkill(int index) {
		return skills != null ? skills.get(index) : getSkills().get(index);
	}

}
