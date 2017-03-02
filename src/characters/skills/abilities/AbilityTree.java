package characters.skills.abilities;

import characters.exceptions.AbilityAtMaxLevelException;
import characters.exceptions.NotEnoughSkillPointsException;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import util.collections.tree.PreOrderTreeMatcher;
import util.collections.tree.Tree;

import java.util.ArrayList;

public class AbilityTree {

	private final Tree<Ability> tree;
	private int abilitiesUnlocked;
	private int possibleSkillPoints;
	private ArrayList<Ability> upgradeableAbilities;
	private PreOrderTreeMatcher<Ability> matcher;

	public AbilityTree(Tree<Ability> newTree) {
		tree = newTree;
		matcher = new PreOrderTreeMatcher<>();
		upgradeableAbilities = new ArrayList<Ability>();
	}

	public Tree<Ability> getTree() {
		return tree;
	}

	public void levelUp() {
		possibleSkillPoints++;
		updateUpgradeableAbilities();
	}

	public void upgradeAbility(int upgradeableAbilitiesIndex) {
		if (possibleSkillPoints > abilitiesUnlocked) {
			try {
				upgradeableAbilities.get(upgradeableAbilitiesIndex).levelUp();
				updateUpgradeableAbilities();
				abilitiesUnlocked++;
			} catch (AbilityAtMaxLevelException ae) {
				throw ae;
			}
		}
		else throw new NotEnoughSkillPointsException(abilitiesUnlocked + 1, possibleSkillPoints);
	}

	/*
	This method returns an ArrayList of abilities in the tree which could be upgraded, these are:
		- Abilities yet to be unlocked that are after an unlocked ability in the tree
		- Abilities which have been unlocked but are not upgraded to the maximum level
	 */
	public ArrayList<Ability> getUpgradeableAbilities() {
		updateUpgradeableAbilities();
		return upgradeableAbilities;
	}

	private void updateUpgradeableAbilities() {
		upgradeableAbilities =  matcher.getMatchingNodesAndUnbox(tree,
				node -> node.getParent() == null
						|| node.getParent().getNodeItem().isUnlocked()
						&& ! node.getNodeItem().isFullyUnlocked());
	}

	/*
	Given an Ability ID, this method returns the Ability if it exists in the unlocked portion of
	the AbilityTree.
	 */
	public Ability getAbilityIfUnlocked(int abilityId) {
		throw new NotImplementedException();
	}

	protected int getPossibleSkillPoints() {
		return possibleSkillPoints;
	}
}
