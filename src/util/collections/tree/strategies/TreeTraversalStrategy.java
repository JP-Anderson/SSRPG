package util.collections.tree.strategies;

import util.collections.tree.Tree;
import util.collections.tree.TreeNode;
import util.collections.tree.visitors.*;

/**
 * Base class for tree traversal strategies such as inorder, preorder etc.
 */
public abstract class TreeTraversalStrategy<E> {

	Tree<E> tree;
	protected TreeNodeVisitor<E> visitorAction;

	public TreeTraversalStrategy(Tree<E> searchTree, TreeNodeVisitor<E> action) {
		tree = searchTree;
		visitorAction = action;
	}

	public void traverseTree() {
		TreeNode<E> root = tree.getRoot();
		traverse(root);
	}

	protected abstract void traverse(TreeNode<E> node);

}
