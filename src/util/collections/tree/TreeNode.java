package util.collections.tree;

import java.util.ArrayList;
import java.util.List;

public class TreeNode<E> {

	private TreeNode<E> parent;
	private List<TreeNode<E>> children;
	private E item;

	public TreeNode(TreeNode pParent, E pItem) {
		parent = pParent;
		if (parent != null) parent.addChild(this);
		item = pItem;
		children = new ArrayList<>();
	}

	public void setNodeItem(E newItem) {
		item = newItem;
	}

	public E getNodeItem() {
		return item;
	}

	public void addChildren(ArrayList<TreeNode<E>> newChildren) {
		children.addAll(newChildren);
	}

	public void addChild(TreeNode<E> newChild) {
		children.add(newChild);
	}

	public List<TreeNode<E>> getChildren() {
		return children;
	}

	public int getNumberOfChildren() {
		return children.size();
	}

	public TreeNode<E> getParent() {
		return parent;
	}

}
