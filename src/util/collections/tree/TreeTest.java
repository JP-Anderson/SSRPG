package util.collections.tree;

import characters.skills.abilities.Ability;
import characters.skills.abilities.BooleanAbility;

import java.util.ArrayList;
import java.util.Arrays;

public class TreeTest {

	// In this class the generic tree class will be populated and tested with Ability objects
	public static void main(String[] args) {
		Tree<Ability> abilityTree = init();
		PreOrderTreeSearcher<Ability> pots = new PreOrderTreeSearcher<>();
		if (pots.doesTreeContain(abilityTree, a -> a._id == 5))
			System.out.println("We have FIVE!");
		if (pots.doesTreeContain(abilityTree, a -> a._id == 1))
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
		Ability a1 = new BooleanAbility(1, "A1", "D1");
		Ability a1_1 = new BooleanAbility(2, "A1-1", "D1-1");
		Ability a1_2 = new BooleanAbility(3, "A1-2", "D1-2");
		Ability a1_1_1 = new BooleanAbility(4, "A1-1-1", "D1-1-1");

		TreeNode<Ability> n1 = new TreeNode<>(null, a1);
		TreeNode<Ability> n1_1 = new TreeNode<>(n1, a1_1);
		TreeNode<Ability> n1_2 = new TreeNode<>(n1, a1_2);
		n1.addChildren(new ArrayList<>(Arrays.asList(n1_1, n1_2)));
		TreeNode<Ability> n1_1_1 = new TreeNode<>(n1_1, a1_1_1);
		n1_1.addChildren(new ArrayList<>(Arrays.asList(n1_1_1)));

		return new Tree<>(n1);
	}

}
