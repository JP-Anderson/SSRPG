package util.collections.tree;

import java.util.List;
import java.util.function.Predicate;

public class PreOrderTreeSearcher <E> {

    private Predicate<E> _predicate;
    private Tree<E> _tree;

    public boolean contains(Tree<E> tree, Predicate<E> predicate) {
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
            List<TreeNode<E>> children = n.getChildren();
            if (children != null) {
                for (TreeNode<E> node : children) preOrderTraversal(node);
            }
        } return false;
    }

}
