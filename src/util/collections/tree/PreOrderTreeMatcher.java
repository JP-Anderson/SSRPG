package util.collections.tree;

import util.streams.StreamHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class PreOrderTreeMatcher<E> {

	private Predicate<TreeNode<E>> predicate;
	private Tree<E> tree;

	public ArrayList<TreeNode<E>> getMatchingNodes(Tree<E> tree, Predicate<TreeNode<E>> matcherFunction) {
		this.tree = tree;
		this.predicate = matcherFunction;
		return matchNodes();
	}

	public ArrayList<E> getMatchingNodesAndUnbox(Tree<E> tree, Predicate<TreeNode<E>> matcherFunction) {
		ArrayList<E> unboxedNodes = new ArrayList<E>();
		for (TreeNode<E> node : getMatchingNodes(tree, matcherFunction)) {
			unboxedNodes.add(node.getNodeItem());
		}
		return unboxedNodes;
	}

	private ArrayList<TreeNode<E>> matchNodes() {
		ArrayList<TreeNode<E>> matchingNodes = emptyNodeList();
		if (tree.getRoot() != null) matchingNodes.addAll(matchWithPreOrder(tree.getRoot()));
		return matchingNodes;
	}

	private ArrayList<TreeNode<E>> matchWithPreOrder(TreeNode<E> node) {
		ArrayList<TreeNode<E>> collectedNodes = emptyNodeList();
		if (predicate.test(node)) {
			collectedNodes.add(node);
		}
		List<TreeNode<E>> children = node.getChildren();
		for (TreeNode<E> child : children) {
			collectedNodes.addAll(matchWithPreOrder(child));
		}
		return collectedNodes;
	}

	private ArrayList<TreeNode<E>> emptyNodeList() {
		return new ArrayList<>();
	}

}
