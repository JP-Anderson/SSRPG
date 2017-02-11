package util.collections.tree;

public class Tree<E> {

    private TreeNode<E> root;

    public Tree(E newRootObject) {
        root = new TreeNode<>(null, newRootObject);
    }

    public Tree(TreeNode<E> newRoot) {
        root = newRoot;
    }

    public TreeNode<E> getRoot() { return root; }

}
