package characters;

import characters.classes.CrewmemberClass;

public class Crewmember {

    public final String name;
    private final Skills skills;
    private int level;

    public final CrewmemberClass crewmemberClass;

    public Crewmember(String pName, Skills newSkills, CrewmemberClass newClass) {
        name = pName;
        skills = newSkills;
        crewmemberClass = newClass;
        level = 1;
    }

}
