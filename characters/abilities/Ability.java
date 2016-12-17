package characters.abilities;

public class Ability {

    public final String name;
    public final AbilityID id;
    public Ability(String pName, AbilityID pId) {
        name = pName;
        id = pId;
    }

    public enum AbilityID {
        ONE, TWO, THREE, FOUR, FIVE
    }

}
