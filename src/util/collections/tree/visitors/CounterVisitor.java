package util.collections.tree.visitors;

import util.collections.tree.TreeNode;

import java.util.function.Predicate;

/**
 * CounterVisitor counts the nodes in a tree matching a predicate
 * If no predicate is provided, CounterVisitor counts all nodes in the tree
 */
public class CounterVisitor<E> extends TreeNodeVisitor<E>  {

	private Predicate<TreeNode<E>> predicate = null;
	int count;

	public CounterVisitor(Predicate<TreeNode<E>> predicate) {
		this.predicate = predicate;
		count = 0;
	}

	public CounterVisitor() {
		count = 0;
	}

	@Override
	public void visit(TreeNode<E> node) {
		if (predicate != null && !predicate.test(node)) return;
		count++;
	}

	public int getCount() {
		return count;
	}

}
