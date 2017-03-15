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

class MatcherVisitorTest {

	private ArrayList<Skill> skills = SkillAndAbilityLoader.loadSkills("C:\\Workspaces\\SSRPG\\dat\\test\\test_abilities.xml");

	@Test
	public void matcherVisitorWithPreOrderGetsCorrectAbilityForRoot() {
		String rootAbilityName = "Skill2 Ability1";
		MatcherVisitor<Ability> matcherVisitor = getMatcherResultsForAbilityWithName(rootAbilityName);
		ArrayList<TreeNode<Ability>> matches = matcherVisitor.getMatches();

		assertTrue(matches.size() == 1);
		assertTrue(matches.get(0).getNodeItem()._name.equals(rootAbilityName));
	}

	@Test
	public void matcherVisitorWithPreOrderGetsCorrectAbilityForBranch() {
		String branchAbilityName = "Skill2 Ability3";
		MatcherVisitor<Ability> matcherVisitor = getMatcherResultsForAbilityWithName(branchAbilityName);
		ArrayList<TreeNode<Ability>> matches = matcherVisitor.getMatches();

		assertTrue(matches.size() == 1);
		assertTrue(matches.get(0).getNodeItem()._name.equals(branchAbilityName));
	}

	@Test
	public void matcherVisitorWithPreOrderGetsCorrectAbilityForLeaf() {
		String leafAbilityName = "Skill2 Ability6";
		MatcherVisitor<Ability> matcherVisitor = getMatcherResultsForAbilityWithName(leafAbilityName);
		ArrayList<TreeNode<Ability>> matches = matcherVisitor.getMatches();

		assertTrue(matches.size() == 1);
		assertTrue(matches.get(0).getNodeItem()._name.equals(leafAbilityName));
	}

	private MatcherVisitor<Ability> getMatcherResultsForAbilityWithName(String abilityName) {
		Tree<Ability> testTree = getSkillTwoTestTree();
		MatcherVisitor<Ability> matcherVisitor = createMatcherForAbilityWithName(abilityName);
		PreOrderTreeTraversal<Ability> traversalStrategy = new PreOrderTreeTraversal<>(testTree, matcherVisitor);

		traversalStrategy.traverseTree();
		return matcherVisitor;
	}

	private MatcherVisitor<Ability> createMatcherForAbilityWithName(String abilityName) {
		Predicate<TreeNode<Ability>> matchAbilityOnName = tn -> tn.getNodeItem()._name.equals(abilityName);
		return new MatcherVisitor<Ability>(matchAbilityOnName);
	}

	@Test
	public void matcherVisitorWithPreOrderFindsAllDoubleAbilities() {
		MatcherVisitor<Ability> matcherVisitor = getMatcherResultsForDoubleAbility();
		ArrayList<TreeNode<Ability>> matches = matcherVisitor.getMatches();

		assertTrue(matches.size() == 4);
	}

	@Test
	public void matcherVisitorWithPreOrderFindsAllBooleanAbilities() {
		MatcherVisitor<Ability> matcherVisitor = getMatcherResultsForBooleanAbility();
		ArrayList<TreeNode<Ability>> matches = matcherVisitor.getMatches();

		assertTrue(matches.size() == 1);
	}

	private MatcherVisitor<Ability> getMatcherResultsForDoubleAbility() {
		Tree<Ability> testTree = getSkillTwoTestTree();
		Predicate<TreeNode<Ability>> matchAbilityOnType = tn -> tn.getNodeItem() instanceof DoubleAbility;
		MatcherVisitor<Ability> matcherVisitor = new MatcherVisitor<>(matchAbilityOnType);
		PreOrderTreeTraversal<Ability> traversalStrategy = new PreOrderTreeTraversal<>(testTree, matcherVisitor);

		traversalStrategy.traverseTree();
		return matcherVisitor;
	}

	private MatcherVisitor<Ability> getMatcherResultsForBooleanAbility() {
		Tree<Ability> testTree = getSkillTwoTestTree();
		Predicate<TreeNode<Ability>> matchAbilityOnType = tn -> tn.getNodeItem() instanceof BooleanAbility;
		MatcherVisitor<Ability> matcherVisitor = new MatcherVisitor<>(matchAbilityOnType);
		PreOrderTreeTraversal<Ability> traversalStrategy = new PreOrderTreeTraversal<>(testTree, matcherVisitor);

		traversalStrategy.traverseTree();
		return matcherVisitor;
	}

	private Tree<Ability> getSkillTwoTestTree() {
		return skills.get(1)._abilities.getTree();
	}

}