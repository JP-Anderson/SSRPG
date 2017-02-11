package characters.abilities;

public class Ability {

    public final int _id;
    public final String _name;
    public final String _description;
    public final int _levels;

    private int abilityLevel = 0;

    public Ability(int id, String name, String description, int levels) {
        _id = id;
        _name = name;
        _description = description;
        _levels = levels;
    }

    /*
        Returns the level of the ability, 0 means the ability is not unlocked
     */
    public int getAbilityLevel() {
        return abilityLevel;
    }

    public void levelUp() {
        if (abilityLevel < _levels) {
            abilityLevel++;
        }
    }

}
