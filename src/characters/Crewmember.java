package characters;

import characters.classes.CrewmemberClass;
import characters.skills.abilities.Ability;

public class Crewmember {

	public final String name;
	public Level level;
	private boolean isManningModule = false;

	public final CrewmemberClass crewmemberClass;

	public Crewmember(String pName, CrewmemberClass newClass, int startingLevel) {
		name = pName;
		crewmemberClass = newClass;
		level = new Level(startingLevel);
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
