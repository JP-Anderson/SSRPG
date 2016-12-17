package util.collections.tree;

import java.util.ArrayList;
import java.util.List;

public class TreeNode<E> {

    private TreeNode parent;
    private List<TreeNode> children;
    private E item;

    public TreeNode(TreeNode pParent, E pItem) {
        parent = pParent;
        item = pItem;
    }

    public void setChildren(ArrayList<TreeNode> newChildren) { children = newChildren; }

    public List<TreeNode> getChildren() { return children; }

    public void setNodeItem(E newItem) { item = newItem; }

    public E getNodeItem() { return item; }

}
