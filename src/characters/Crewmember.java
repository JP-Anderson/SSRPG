package characters;

import characters.classes.CrewmemberClass;
import characters.skills.abilities.Ability;
import ship.modules.MannableShipModule;

public class Crewmember {

	public final String name;
	private int level;
	private boolean isManningModule = false;

	public final CrewmemberClass crewmemberClass;

	public Crewmember(String pName, CrewmemberClass newClass) {
		name = pName;
		crewmemberClass = newClass;
		level = 1;
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
