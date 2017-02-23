package characters;

import characters.classes.CrewmemberClass;
import ship.modules.MannableShipModule;

public class Crewmember {

    public final String name;
    private final Skills skills;
    private int level;
    private MannableShipModule mannedModule;

    public final CrewmemberClass crewmemberClass;

    public Crewmember(String pName, Skills newSkills, CrewmemberClass newClass) {
        name = pName;
        skills = newSkills;
        crewmemberClass = newClass;
        level = 1;
    }

    public void setMannedModule(MannableShipModule moduleManned) {
        mannedModule = moduleManned;
    }

    public MannableShipModule getMannedModule() {
        return mannedModule;
    }

    public void removeFromCurrentMannedModule() {
        if (mannedModule != null) {
            mannedModule.removeCrewmember();
            mannedModule = null;
        }
    }

}
