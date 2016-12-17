package util.collections.tree;

import characters.abilities.Ability.AbilityID;

public class Tree<E> {

    private TreeNode root;

    public Tree(TreeNode newRoot) {
        root = newRoot;
    }

    public TreeNode getRoot() { return root; }

}
