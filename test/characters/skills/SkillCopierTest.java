package characters.skills;

import characters.skills.abilities.Ability;
import characters.skills.abilities.DoubleAbility;
import characters.skills.abilities.IntAbility;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import util.collections.tree.PreOrderTreeMatcher;
import util.dataload.xml.SkillAndAbilityLoader;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class SkillCopierTest {

	private ArrayList<Skill> skills = SkillAndAbilityLoader.loadSkills("C:\\Workspaces\\SSRPG\\dat\\test\\test_abilities.xml");

	@Test
	void copiedSkillHasNonEquivalentReference() {
		Skill skill = skills.get(1);
		SkillCopier copier = new SkillCopier();
		Skill skillCopy = copier.copySkill(skill);
		assertNotEquals(skill, skillCopy);
	}

	@Test
	void copiedRootNodeReferenceIsDifferent() {
		Skill skill = skills.get(1);
		Skill skillCopy = copySkill(skill);
		assertNotEquals(
				skill._abilities.getTree().getRoot().getNodeItem(),
				skillCopy._abilities.getTree().getRoot().getNodeItem());
	}

	@Test
	void copiedRootNodeValuesAreIdentical() {
		Skill skill = skills.get(1);
		Skill skillCopy = copySkill(skill);
		IntAbility root = (IntAbility) skill._abilities.getTree().getRoot().getNodeItem();
		IntAbility rootCopy = (IntAbility) skillCopy._abilities.getTree().getRoot().getNodeItem();
		assertTrue(root._name.equals(rootCopy._name));
		assertTrue(root._description.equals(rootCopy._description));
		assertTrue(root.getValuesString().equals(rootCopy.getValuesString()));
	}

	@Test
	void copiedBranchNodeValuesHaveDifferentReference() {
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
	void copiedBranchNodeValuesAreIdentical() {
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
	void copiedLeafNodeValuesHaveDifferentReference() {
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
	void copiedLeafNodeValuesAreIdentical() {
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