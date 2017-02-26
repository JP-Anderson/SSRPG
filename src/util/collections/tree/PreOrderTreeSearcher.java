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
		return getElement(tree, predicate) != null;
	}

	private E getItem() {
		return getItemWithPreOrder(_tree.getRoot());
	}

	private E getItemWithPreOrder(TreeNode<E> node) {
		if (_predicate.test(node.getNodeItem())) {
			return node.getNodeItem();
		} else {
			List<TreeNode<E>> children = node.getChildren();
			if (children != null) {
				for (TreeNode<E> child : children) {
					E e = getItemWithPreOrder(child);
					if (e != null) return e;
				}
			}
		}
		return null;
	}

}
