package util.collections.tree.visitors;

import util.collections.tree.TreeNode;

import java.util.ArrayList;
import java.util.function.Predicate;

/**
 * MatcherVisitor collects nodes from within a tree which match a predicate.
 */
class MatcherVisitor<E> extends TreeNodeVisitor<E> {

	private Predicate<TreeNode<E>> predicate;
	private ArrayList<TreeNode<E>> matches;

	public MatcherVisitor(Predicate<TreeNode<E>> predicate) {
		this.predicate = predicate;
		this.matches = new ArrayList<>();
	}

	public void visit(TreeNode<E> node) {
		if (predicate.test(node)) matches.add(node);
	}

	public ArrayList<TreeNode<E>> getMatches() {
		return matches;
	}

}
