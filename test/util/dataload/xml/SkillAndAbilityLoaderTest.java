package util.dataload.xml;

import characters.skills.Skill;
import characters.skills.abilities.Ability;
import characters.skills.abilities.AbilityTree;
import org.junit.jupiter.api.Test;
import util.collections.tree.TreeNode;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class SkillAndAbilityLoaderTest {

	String testXmlFilePath = "C:\\Workspaces\\SSRPG\\dat\\test\\test_abilities.xml";

	@Test
	void loaderLoadsCorrectNumberOfSkillsAndAbilities() {
		ArrayList<Skill> skills = SkillAndAbilityLoader.loadSkills(testXmlFilePath);

		Skill skill1 = skills.get(0);
		AbilityTree skill1Tree = skill1._abilities;
		TreeNode<Ability> rootAbility = skill1Tree.getTree().getRoot();
		assertEquals(2, rootAbility.getChildren().size());

		Skill skill2 = skills.get(1);
		AbilityTree skill2Tree = skill2._abilities;
		TreeNode<Ability> skill2RootAbility = skill2Tree.getTree().getRoot();
		ArrayList<TreeNode<Ability>> secondLevelNodes = (ArrayList<TreeNode<Ability>>) skill2RootAbility.getChildren();
		assertEquals(2, secondLevelNodes.size());

		TreeNode<Ability> skill2Ability3 = secondLevelNodes.get(1);
		assertEquals(skill2Ability3.getNodeItem()._name, "Skill2 Ability3");
		ArrayList<TreeNode<Ability>> thirdLevelNodes = (ArrayList<TreeNode<Ability>>) skill2Ability3.getChildren();
		assertEquals(1, thirdLevelNodes.size());

		TreeNode<Ability> skill2Ability4 = thirdLevelNodes.get(0);
		ArrayList<TreeNode<Ability>> fourthLevelNodes = (ArrayList<TreeNode<Ability>>) skill2Ability4.getChildren();
		assertEquals(2, fourthLevelNodes.size());

		assertEquals(2, SkillAndAbilityLoader.getSkillCounter());
		assertEquals(9, SkillAndAbilityLoader.getAbilityCounter());
	}

}