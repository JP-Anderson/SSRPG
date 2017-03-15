package util.collections.tree.visitors;

import util.collections.tree.TreeNode;

/**
 * Base class for Visitor 'actions' to carry out on each node during a tree traversal.
 */
public abstract class TreeNodeVisitor<E> {

	public abstract void visit(TreeNode<E> node);

}
