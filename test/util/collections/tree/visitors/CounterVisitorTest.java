package util.collections.tree.visitors;

import characters.skills.Skill;
import characters.skills.abilities.Ability;
import characters.skills.abilities.BooleanAbility;
import characters.skills.abilities.DoubleAbility;
import org.junit.jupiter.api.Test;
import util.collections.tree.Tree;
import util.collections.tree.TreeNode;
import util.collections.tree.strategies.PreOrderTreeTraversal;
import util.dataload.xml.SkillAndAbilityLoader;

import java.util.ArrayList;
import java.util.function.Predicate;

import static org.junit.jupiter.api.Assertions.*;

class CounterVisitorTest {

	private ArrayList<Skill> skills = SkillAndAbilityLoader.loadSkills("C:\\Workspaces\\SSRPG\\dat\\test\\test_abilities.xml");

	@Test
	public void counterVisitorWithoutPredicateCountsAllAbilities() {
		CounterVisitor<Ability> counterVisitor =  getCounterResultsForAll();
		assertTrue(counterVisitor.getCount() == 6);
	}

	private CounterVisitor<Ability> getCounterResultsForAll() {
		Tree<Ability> testTree = getSkillTwoTestTree();
		CounterVisitor<Ability> counterVisitor = new CounterVisitor<>();
		PreOrderTreeTraversal<Ability> traversalStrategy = new PreOrderTreeTraversal<>(testTree, counterVisitor);
		traversalStrategy.traverseTree();
		return counterVisitor;
	}

	@Test
	public void counterVisitorWithPreOrderCountsBooleanAbilities() {
		CounterVisitor<Ability> counterVisitor = getCounterResultsForBooleanAbility();
		assertTrue(counterVisitor.getCount() == 1);
	}

	private CounterVisitor<Ability> getCounterResultsForBooleanAbility() {
		Tree<Ability> testTree = getSkillTwoTestTree();
		Predicate<TreeNode<Ability>> countAbilityOnType = tn -> tn.getNodeItem() instanceof BooleanAbility;
		CounterVisitor<Ability> counterVisitor = new CounterVisitor<>(countAbilityOnType);
		PreOrderTreeTraversal<Ability> traversalStrategy = new PreOrderTreeTraversal<>(testTree, counterVisitor);

		traversalStrategy.traverseTree();
		return counterVisitor;
	}

	@Test
	public void counterVisitorWithPreOrderCountsDoubleAbilities() {
		CounterVisitor<Ability> counterVisitor = getCounterResultsForDoubleAbility();
		assertTrue(counterVisitor.getCount() == 4);
	}

	private CounterVisitor<Ability> getCounterResultsForDoubleAbility() {
		Tree<Ability> testTree = getSkillTwoTestTree();
		Predicate<TreeNode<Ability>> countAbilityOnType = tn -> tn.getNodeItem() instanceof DoubleAbility;
		CounterVisitor<Ability> counterVisitor = new CounterVisitor<>(countAbilityOnType);
		PreOrderTreeTraversal<Ability> traversalStrategy = new PreOrderTreeTraversal<>(testTree, counterVisitor);

		traversalStrategy.traverseTree();
		return counterVisitor;
	}

	private Tree<Ability> getSkillTwoTestTree() {
		return skills.get(1)._abilities.getTree();
	}

}