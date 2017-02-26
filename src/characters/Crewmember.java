package characters;

import characters.classes.CrewmemberClass;
import characters.skills.abilities.Ability;
import ship.modules.MannableShipModule;

public class Crewmember {

	public final String name;
	private int level;
	private MannableShipModule mannedModule;

	public final CrewmemberClass crewmemberClass;

	public Crewmember(String pName, CrewmemberClass newClass) {
		name = pName;
		crewmemberClass = newClass;
		level = 1;
	}

	public Ability hasAbility(String abilityName) {
		return crewmemberClass.getAbilityIfItExists(abilityName);
	}

	public void setMannedModule(MannableShipModule moduleManned) {
		removeFromCurrentMannedModule();
		mannedModule = moduleManned;
		mannedModule.setActiveCrewmember(this);
	}

	public MannableShipModule getMannedModule() {
		return mannedModule;
	}

	public void removeFromCurrentMannedModule() {
		if (mannedModule != null) {
			mannedModule.setActiveCrewmember(null);
			mannedModule = null;
		}
	}

}
