package characters.classes;

import characters.skills.Skill;
import characters.skills.SkillsHolder;
import characters.skills.abilities.*;

public abstract class CrewmemberClass {

    public final String _className;
    //pilot, navigator, weapons, engineer, trader, shields, scoundrel

    protected Skill skill;

    public CrewmemberClass(String className) {
        _className = className;
        loadSkills();
    }

    public final void loadSkills() {
        skill = SkillsHolder.getSkill(_className);
    }

    // Do we need a getter, should we hide this tree?
    //public AbilityTree getAbilityTree() { return abilities; }

    public boolean hasAbility() {
        return true;
    }


}
