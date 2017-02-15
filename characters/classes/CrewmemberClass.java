package characters.classes;

import characters.skills.abilities.*;

public abstract class CrewmemberClass {

    public final String className;
    //pilot, navigator, weapons, engineer, trader, shields, scoundrel

    protected AbilityTree abilities;

    public CrewmemberClass(String cName) {
        className = cName;
        //abilities = new AbilityTree( need to stick a base ability here! );
        //buildAbilityTree();
    }

    public abstract void buildAbilityTree();

    // Do we need a getter, should we hide this tree?
    //public AbilityTree getAbilityTree() { return abilities; }

    public boolean hasAbility() {
        return true;
    }


}
