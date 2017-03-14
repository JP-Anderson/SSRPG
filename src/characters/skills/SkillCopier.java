package characters.skills;

import characters.skills.abilities.*;
import util.collections.tree.Tree;
import util.collections.tree.TreeNode;

import java.util.List;

/**
 * Class for copying a Skill
 */
public class SkillCopier {

	public Skill copySkill(Skill skill) {
		return new Skill(skill._id, skill._name, copyTree(skill._abilities));
	}

	private AbilityTree copyTree(AbilityTree abilityTree) {
		Tree treeCopy = new Tree(copyTreeNodes(null, abilityTree.getTree().getRoot()));
		return new AbilityTree(treeCopy);
	}

	private TreeNode<Ability> copyTreeNodes(TreeNode copiedParent, TreeNode<Ability> treeNode) {
		TreeNode<Ability> nodeCopy;
		if (copiedParent == null) {
			nodeCopy = new TreeNode(null, copyAbility((Ability)treeNode.getNodeItem()));
		} else {
			nodeCopy = new TreeNode(copiedParent, copyAbility((Ability)treeNode.getNodeItem()));
		}
		copiedParent = nodeCopy;
		List<TreeNode<Ability>> children = treeNode.getChildren();
		for (TreeNode<Ability> child : children) {
			copyTreeNodes(copiedParent, child);
		}
		return nodeCopy;
	}

	private Ability copyAbility(Ability ability) {
		String abilityClass = ability.getClass().getSimpleName();
		if (abilityClass.equals("IntAbility")) {
			IntAbility intAbility = (IntAbility) ability;
			return new IntAbility(ability._id, ability._name, ability._description, intAbility.getValuesString());
		} else if (abilityClass.equals("BooleanAbility")) {
			return new BooleanAbility(ability._id, ability._name, ability._description);
		} else if (abilityClass.equals("DoubleAbility")) {
			DoubleAbility doubleAbility = (DoubleAbility) ability;
			return new DoubleAbility(ability._id, ability._name, ability._description, doubleAbility.getValuesString());
		}
		throw new UnknownAbilityTypeException();
	}

	private class UnknownAbilityTypeException extends RuntimeException {	}

}
