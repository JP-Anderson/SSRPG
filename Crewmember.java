public class Crewmember {
    private String name;
    private final Skills skills;
    private int level;
    public Crewmember(String pName, Skills newSkills) {
        name = pName;
        skills = newSkills;
        level = 1;
    }
}
