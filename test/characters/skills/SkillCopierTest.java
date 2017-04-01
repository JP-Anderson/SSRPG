package characters.skills;

import characters.skills.abilities.Ability;
import characters.skills.abilities.DoubleAbility;
import characters.skills.abilities.IntAbility;
import org.junit.Before;
import org.junit.Test;

import base.SsrpgTest;
import util.collections.tree.PreOrderTreeMatcher;
import util.collections.tree.strategies.PreOrderTreeTraversal;
import util.collections.tree.visitors.CounterVisitor;
import util.dataload.xml.SkillAndAbilityLoader;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class SkillCopierTest extends SsrpgTest {

	private ArrayList<Skill> skills = SkillAndAbilityLoader.loadSkills("C:\\Workspaces\\SSRPG\\dat\\test\\test_abilities.xml");

	@Test
	public void copiedSkillTreeHasCorrectNodeCount() {
		Skill skill = skills.get(1);
		SkillCopier copier = new SkillCopier();
		Skill skillCopy = copier.copySkill(skill);

		CounterVisitor<Ability> counterVisitor = new CounterVisitor<>();
		PreOrderTreeTraversal<Ability> treeTraverser = new PreOrderTreeTraversal<>(skill._abilities.getTree(), counterVisitor);
		treeTraverser.traverseTree();

		// Having to create a visitor and initialising the traverser with it is probably a design that needs to be changed
		// Means traversers cannot be reused
		// and visitors will have to be reset or prevented from reuse
		// TODO: Will wrap these in a future commit, ignoring from this commit for now.
		CounterVisitor<Ability> counterVisitor2 = new CounterVisitor<>();
		PreOrderTreeTraversal<Ability> treeTraverser2 = new PreOrderTreeTraversal<>(skill._abilities.getTree(), counterVisitor2);
		treeTraverser2.traverseTree();

		assertTrue(counterVisitor.getCount() == 6);
		assertTrue(counterVisitor2.getCount() == 6);
	}

	@Test
	public void copiedSkillHasNonEquivalentReference() {
		Skill skill = skills.get(1);
		SkillCopier copier = new SkillCopier();
		Skill skillCopy = copier.copySkill(skill);
		assertNotEquals(skill, skillCopy);
	}

	@Test
	public void copiedRootNodeReferenceIsDifferent() {
		Skill skill = skills.get(1);
		Skill skillCopy = copySkill(skill);
		assertNotEquals(
				skill._abilities.getTree().getRoot().getNodeItem(),
				skillCopy._abilities.getTree().getRoot().getNodeItem());
	}

	@Test
	public void copiedRootNodeValuesAreIdentical() {
		Skill skill = skills.get(1);
		Skill skillCopy = copySkill(skill);
		IntAbility root = (IntAbility) skill._abilities.getTree().getRoot().getNodeItem();
		IntAbility rootCopy = (IntAbility) skillCopy._abilities.getTree().getRoot().getNodeItem();
		assertTrue(root._name.equals(rootCopy._name));
		assertTrue(root._description.equals(rootCopy._description));
		assertTrue(root.getValuesString().equals(rootCopy.getValuesString()));
	}

	@Test
	public void copiedBranchNodeValuesHaveDifferentReference() {
		Skill skill = skills.get(1);
		Skill skillCopy = copySkill(skill);

		PreOrderTreeMatcher<Ability> treeMatcher = new PreOrderTreeMatcher<>();

		DoubleAbility branchAbility = (DoubleAbility)
				treeMatcher.getMatchingNodesAndUnbox(skill._abilities.getTree(),
						a -> a.getNodeItem()._name.equals("Skill2 Ability3")).get(0);
		DoubleAbility branchAbilityCopy = (DoubleAbility)
				treeMatcher.getMatchingNodesAndUnbox(skillCopy._abilities.getTree(),
						a -> a.getNodeItem()._name.equals("Skill2 Ability3")).get(0);

		assertNotEquals(branchAbility, branchAbilityCopy);
	}

	@Test
	public void copiedBranchNodeValuesAreIdentical() {
		Skill skill = skills.get(1);
		Skill skillCopy = copySkill(skill);

		PreOrderTreeMatcher<Ability> treeMatcher = new PreOrderTreeMatcher<>();

		DoubleAbility branchAbility = (DoubleAbility)
				treeMatcher.getMatchingNodesAndUnbox(skill._abilities.getTree(),
						a -> a.getNodeItem()._name.equals("Skill2 Ability3")).get(0);
		DoubleAbility branchAbilityCopy = (DoubleAbility)
				treeMatcher.getMatchingNodesAndUnbox(skillCopy._abilities.getTree(),
						a -> a.getNodeItem()._name.equals("Skill2 Ability3")).get(0);

		assertTrue(branchAbility._name.equals(branchAbilityCopy._name));
		assertTrue(branchAbility._description.equals(branchAbilityCopy._description));
		assertTrue(branchAbility.getValuesString().equals(branchAbilityCopy.getValuesString()));
	}

	@Test
	public void copiedLeafNodeValuesHaveDifferentReference() {
		Skill skill = skills.get(1);
		Skill skillCopy = copySkill(skill);

		PreOrderTreeMatcher<Ability> treeMatcher = new PreOrderTreeMatcher<>();

		DoubleAbility branchAbility = (DoubleAbility)
				treeMatcher.getMatchingNodesAndUnbox(skill._abilities.getTree(),
						a -> a.getNodeItem()._name.equals("Skill2 Ability6")).get(0);
		DoubleAbility branchAbilityCopy = (DoubleAbility)
				treeMatcher.getMatchingNodesAndUnbox(skillCopy._abilities.getTree(),
						a -> a.getNodeItem()._name.equals("Skill2 Ability6")).get(0);

		assertNotEquals(branchAbility, branchAbilityCopy);
	}

	@Test
	public void copiedLeafNodeValuesAreIdentical() {
		Skill skill = skills.get(1);
		Skill skillCopy = copySkill(skill);

		PreOrderTreeMatcher<Ability> treeMatcher = new PreOrderTreeMatcher<>();

		DoubleAbility branchAbility = (DoubleAbility)
				treeMatcher.getMatchingNodesAndUnbox(skill._abilities.getTree(),
						a -> a.getNodeItem()._name.equals("Skill2 Ability6")).get(0);
		DoubleAbility branchAbilityCopy = (DoubleAbility)
				treeMatcher.getMatchingNodesAndUnbox(skillCopy._abilities.getTree(),
						a -> a.getNodeItem()._name.equals("Skill2 Ability6")).get(0);

		assertTrue(branchAbility._name.equals(branchAbilityCopy._name));
		assertTrue(branchAbility._description.equals(branchAbilityCopy._description));
		assertTrue(branchAbility.getValuesString().equals(branchAbilityCopy.getValuesString()));
	}

	private Skill copySkill(Skill skillToCopy) {
		return new SkillCopier().copySkill(skillToCopy);
	}

}