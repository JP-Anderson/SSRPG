package characters.skills.abilities;

import characters.skills.Skill;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import util.collections.tree.TreeNode;
import util.dataload.xml.SkillAndAbilityLoader;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class AbilityTreeTest {

	String testXmlFilePath = "C:\\Workspaces\\SSRPG\\dat\\test\\test_abilities.xml";


	//region getUpgradeableAbilities
	@Test
	public void getUpgradeableAbilitiesReturnsOneAbilityForEmptyAbilityTree() {
		AbilityTree tree = loadTestSkillTree();
		ArrayList<Ability> availableAbilities = tree.getUpgradeableAbilities();

		assertEquals(1, availableAbilities.size());
		Ability abilityOne = availableAbilities.get(0);
		assertEquals("Skill2 Ability1", abilityOne._name);
	}
	//endregion



	private AbilityTree loadTestSkillTree() {
		ArrayList<Skill> skills = SkillAndAbilityLoader.loadSkills(testXmlFilePath);
		return skills.get(1)._abilities;
	}

}