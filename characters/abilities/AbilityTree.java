package characters.abilities;

import java.util.List;
import java.util.ArrayList;

public class AbilityTree {

    private TreeNode root;

    public AbilityTree() {
        root = new TreeNode(null);
    }

    public TreeNode getRoot() { return root; }


    private class TreeNode {

        private TreeNode parent;
        private List<TreeNode> children;
        private Ability ability;

        public TreeNode(TreeNode pParent) {
            parent = pParent;
        }

        public void setChildren(ArrayList<TreeNode> newChildren) { children = newChildren; }

        public List<TreeNode> getChildren() { return children; }

        public void setAbility(Ability newAbility) { ability = newAbility; }

        public Ability getAbility() { return ability; }

    }

}
