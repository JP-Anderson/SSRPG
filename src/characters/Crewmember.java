package characters;

import characters.classes.CrewmemberClass;
import characters.exceptions.AbilityException;
import characters.skills.abilities.Ability;

import java.util.ArrayList;

public class Crewmember {

	public final String name;
	private Level level;
	private boolean isManningModule = false;

	public final CrewmemberClass crewmemberClass;

	public Crewmember(String pName, CrewmemberClass newClass, int startingLevel) {
		name = pName;
		crewmemberClass = newClass;
		level = new Level(startingLevel);
	}

	public void gainExperience(int xp) {
		level.gainExperience(xp);
	}

	public Level getLevel() {
		return level;
	}

	public ArrayList<Ability> getUpgradeableAbilities() {
		return crewmemberClass.getUpgradeableAbilities();
	}

	public boolean tryToLevelUpAbility(int upgradeableAbilityIndex) {
		try {
			crewmemberClass.upgradeAbility(upgradeableAbilityIndex);
		} catch(AbilityException ae) {
			return false;
		}
		return true;
	}

	public Ability hasAbility(String abilityName) {
		return crewmemberClass.getAbilityIfItExists(abilityName);
	}

	public boolean isManningAModule() {
		return isManningModule;
	}

	public void manningModule() {
		isManningModule = true;
	}

	public void notManningModule() {
		isManningModule = false;
	}

}
