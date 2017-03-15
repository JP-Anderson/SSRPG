package util.collections.tree.strategies;

import util.collections.tree.Tree;
import util.collections.tree.TreeNode;
import util.collections.tree.visitors.TreeNodeVisitor;

import java.util.ArrayList;

/**
 * Traverses nodes with a preorder search
 * Carries out action on the root, then it's children left to right
 */
public class PreOrderTreeTraversal<E> extends TreeTraversalStrategy<E> {

	public PreOrderTreeTraversal (Tree<E> tree, TreeNodeVisitor<E> action) {
		super(tree, action);
	}

	@Override
	protected void traverse(TreeNode<E> node) {
		visitorAction.visit(node);
		ArrayList<TreeNode<E>> children = (ArrayList<TreeNode<E>>) node.getChildren();
		for (TreeNode<E> childNode : children) traverse(childNode);
	}

}
