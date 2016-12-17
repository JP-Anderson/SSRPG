package util.collections.tree;

import characters.abilities.Ability;

import java.util.ArrayList;
import java.util.Arrays;

public class TreeTest {

    // In this class the generic tree class will be populated and tested with Ability objects
    public static void main(String[] args) {
        Tree<Ability> abilityTree = init();
        PreOrderTreeSearcher<Ability> pots = new PreOrderTreeSearcher<>();
        if (pots.contains(abilityTree, a -> a.id == Ability.AbilityID.FIVE))
            System.out.println("We have FIVE!");
        if (pots.contains(abilityTree, a -> a.id == Ability.AbilityID.ONE))
            System.out.println("We have ONE!");
    }

    private static Tree<Ability> init() {
        /*
              1
              |
           --------
           |      |
         1-1     1-2
           |
        1-1-1

         */
        Ability a1 = new Ability("A1", Ability.AbilityID.ONE);
        Ability a1_1 = new Ability("A1-1", Ability.AbilityID.TWO);
        Ability a1_2 = new Ability("A1-2", Ability.AbilityID.THREE);
        Ability a1_1_1 = new Ability("A1-1-1", Ability.AbilityID.FOUR);

        TreeNode<Ability> n1 = new TreeNode<>(null, a1);
        TreeNode<Ability> n1_1 = new TreeNode<>(n1, a1_1);
        TreeNode<Ability> n1_2 = new TreeNode<>(n1, a1_2);
        n1.setChildren(new ArrayList<>(Arrays.asList(n1_1,n1_2)));
        TreeNode<Ability> n1_1_1 = new TreeNode<>(n1_1, a1_1_1);
        n1_1.setChildren(new ArrayList<>(Arrays.asList(n1_1_1)));

        return new Tree<>(n1);
    }

}
