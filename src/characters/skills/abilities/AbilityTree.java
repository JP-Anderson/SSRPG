package characters.skills.abilities;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import util.collections.tree.PreOrderTreeMatcher;
import util.collections.tree.Tree;

import java.util.ArrayList;

public class AbilityTree {

	private int abilitiesUnlocked;
	private final Tree<Ability> tree;
	private PreOrderTreeMatcher<Ability> matcher;

	public AbilityTree(Tree<Ability> newTree) {
		abilitiesUnlocked = 0;
		tree = newTree;
		matcher = new PreOrderTreeMatcher<>();
	}

	public Tree<Ability> getTree() {
		return tree;
	}

	/*
	This method returns an ArrayList of abilities in the tree which could be upgraded, these are:
		- Abilities yet to be unlocked that are after an unlocked ability in the tree
		- Abilities which have been unlocked but are not upgraded to the maximum level
	 */
	public ArrayList<Ability> getUpgradeableAbilities() {
		return matcher.getMatchingNodesAndUnbox(tree, node -> node.getParent() == null
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
}
