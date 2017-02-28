package characters.skills.abilities;

import util.collections.tree.Tree;

public class AbilityTree {

	private int abilitiesUnlocked;

	private final Tree<Ability> tree;

	public AbilityTree(Tree<Ability> newTree) {
		abilitiesUnlocked = 0;
		tree = newTree;
	}

	public Tree<Ability> getTree() {
		return tree;
	}

}
