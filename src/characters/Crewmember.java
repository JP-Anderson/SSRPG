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
		if (level.getLevel() > 1) {
			for (int i = 1; i < level.getLevel(); i++) {
				crewmemberClass.incrementAvailableAbilityUpgrades();
			}
		}
	}

	public void gainExperience(int xp) {
		int startingLevel = level.getLevel();
		level.gainExperience(xp);
		if (level.getLevel() > startingLevel) {
			int levelsGained = level.getLevel() - startingLevel;
			for (int i = 0; i < levelsGained; i++ ) {
				crewmemberClass.incrementAvailableAbilityUpgrades();
			}
		}
	}

	public Level getLevel() {
		return level;
	}

	public int availableAbilityUpgrades() {
		return crewmemberClass.availableAbilityUpgrades();
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

	public Ability tryAndGetAbility(String abilityName) {
		return crewmemberClass.getAbilityIfItExists(abilityName);
	}

	public boolean hasAbility(String abilityName) {
		return crewmemberClass.getAbilityIfItExists(abilityName) != null;
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
