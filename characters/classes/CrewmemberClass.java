package characters.classes;

import characters.abilities.*;

public abstract class CrewmemberClass {

    public final String className;
    //pilot, navigator, weapons, engineer, trader, shields, scoundrel

    protected AbilityTree abilities;

    public CrewmemberClass(String cName) {
        className = cName;
        abilities = new AbilityTree();
        buildAbilityTree();
    }

    public abstract void buildAbilityTree();

    public AbilityTree getAbilityTree() { return abilities; }

}
