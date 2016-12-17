package util.collections.tree;

import characters.abilities.Ability;

import java.util.List;
import java.util.ArrayList;
import java.util.function.Predicate;

public class PreOrderTreeSearcher <E> {

    private Predicate<E> _predicate;
    private Tree _tree;


    // for now, just thinking about pre order iteration
    // Root node first, then its children from left to right
    public boolean contains(Tree tree, Predicate<E> predicate) {
        _predicate = predicate;
        _tree = tree;
        return preOrderTreeIterator();
    }

    private boolean preOrderTreeIterator() {
        return preOrderTraversal(_tree.getRoot());
    }

    private boolean preOrderTraversal(TreeNode<E> n) {
        if (_predicate.test(n.getNodeItem())) {
            return true;
        } else {
            List<TreeNode> children = n.getChildren();
            if (children != null) {
                for (TreeNode<E> node : children) preOrderTraversal(node);
            }
        } return false;
    }

}
