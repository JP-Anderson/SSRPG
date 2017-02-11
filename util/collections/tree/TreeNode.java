package util.collections.tree;

import java.util.ArrayList;
import java.util.List;

public class TreeNode<E> {

    private TreeNode<E> parent;
    private List<TreeNode<E>> children;
    private E item;

    public TreeNode(TreeNode pParent, E pItem) {
        parent = pParent;
        item = pItem;
    }

    public void setNodeItem(E newItem) { item = newItem; }

    public E getNodeItem() { return item; }

    public void addChildren(ArrayList<TreeNode<E>> newChildren) { children.addAll(newChildren); }

    public void addChild(TreeNode<E> newChild) { children.add(newChild); }

    public List<TreeNode<E>> getChildren() { return children; }

    public TreeNode<E> getParent() { return parent; }

}
