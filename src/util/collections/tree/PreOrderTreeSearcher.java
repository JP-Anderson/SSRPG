package util.collections.tree;

import java.util.List;
import java.util.function.Predicate;

public class PreOrderTreeSearcher<E> {

	private Predicate<E> _predicate;
	private Tree<E> _tree;

	public E getElement(Tree<E> tree, Predicate<E> predicate) {
		_predicate = predicate;
		_tree = tree;
		return getItem();
	}

	public boolean doesTreeContain(Tree<E> tree, Predicate<E> predicate) {
		_predicate = predicate;
		_tree = tree;
		return getItem() != null;
	}

	private E getItem() {
		return getItemWithPreOrder(_tree.getRoot());
	}

	private E getItemWithPreOrder(TreeNode<E> n) {
		if (_predicate.test(n.getNodeItem())) {
			return n.getNodeItem();
		} else {
			List<TreeNode<E>> children = n.getChildren();
			if (children != null) {
				for (TreeNode<E> node : children) preOrderTraversal(node);
			}
		}
		return null;
	}

	private boolean preOrderTraversal(TreeNode<E> n) {
		if (_predicate.test(n.getNodeItem())) {
			return true;
		} else {
			List<TreeNode<E>> children = n.getChildren();
			if (children != null) {
				for (TreeNode<E> node : children) preOrderTraversal(node);
			}
		}
		return false;
	}

}
