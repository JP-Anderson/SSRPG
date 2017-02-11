package characters.abilities;

import java.util.List;
import java.util.ArrayList;
import util.collections.tree.Tree;
import util.collections.tree.TreeNode;

public class AbilityTree {

	private final Tree<Ability> _tree;

	public AbilityTree(Tree<Ability> tree) {
		_tree = tree;
	}

}
