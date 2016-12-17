package util.collections.tree;

import characters.abilities.Ability.AbilityID;

public class Tree<E> {

    private TreeNode<E> root;

    public Tree(TreeNode<E> newRoot) {
        root = newRoot;
    }

    public TreeNode<E> getRoot() { return root; }

}
