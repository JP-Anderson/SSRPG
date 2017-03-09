package characters.classes;

import characters.exceptions.AbilityException;
import characters.skills.Skill;
import characters.skills.SkillsHolder;
import characters.skills.abilities.*;
import util.collections.tree.PreOrderTreeSearcher;

import java.util.ArrayList;

public abstract class CrewmemberClass {

	public final String _className;
	//pilot, navigator, weapons, engineer, trader, shields, scoundrel

	protected Skill skill;

	public CrewmemberClass(String className) {
		_className = className;
		loadSkills();
	}

	private final void loadSkills() {
		skill = SkillsHolder.getSkill(_className);
	}

	public int availableAbilityUpgrades() {
		return skill._abilities.getAvailablePointsToAssign();
	}
	
	public void incrementAvailableAbilityUpgrades() {
		skill._abilities.levelUp();
	}

	public ArrayList<Ability> getUpgradeableAbilities() {
		return skill._abilities.getUpgradeableAbilities();
	}

	public Ability getAbilityIfItExists(String abilityName) {
		PreOrderTreeSearcher<Ability> treeSearcher = new PreOrderTreeSearcher<>();
		return treeSearcher.getElement(
				skill._abilities.getTree(),
				a -> a._name.equals(abilityName) && a.isUnlocked());
	}

	public boolean hasAbility(String abilityName) {
		PreOrderTreeSearcher<Ability> treeSearcher = new PreOrderTreeSearcher<>();
		return treeSearcher.doesTreeContain(
				skill._abilities.getTree(),
				a -> a._name.equals(abilityName) && a.isUnlocked());
	}

	public void upgradeAbility(int upgradeableAbilityIndex) throws AbilityException {
		skill._abilities.upgradeAbility(upgradeableAbilityIndex);
	}

}
