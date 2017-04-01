package characters.skills.abilities;

import characters.exceptions.AbilityAtMaxLevelException;
import characters.exceptions.AbilityNotUnlockedException;
import characters.exceptions.NotEnoughSkillPointsException;
import characters.skills.Skill;
import org.junit.Test;

import base.SsrpgTest;
import util.dataload.xml.SkillAndAbilityLoader;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class AbilityTreeTest extends SsrpgTest {

	String testXmlFilePath = "C:\\Workspaces\\SSRPG\\dat\\test\\test_abilities.xml";

	@Test
	public void levelUpCorrectlyIncrementsAvailableSkillPoints() {
		AbilityTree tree = loadTestSkillTree();
		assertEquals(0, tree.getPossibleSkillPoints());

		tree.levelUp();
		assertEquals(1, tree.getPossibleSkillPoints());

		tree.levelUp();
		assertEquals(2, tree.getPossibleSkillPoints());
	}

	//region getUpgradeableAbilities
	@Test
	public void getUpgradeableAbilitiesReturnsOneAbilityForEmptyAbilityTree() {
		AbilityTree tree = loadTestSkillTree();
		ArrayList<Ability> availableAbilities = tree.getUpgradeableAbilities();

		assertEquals(1, availableAbilities.size());
		Ability abilityOne = availableAbilities.get(0);
		assertEquals("Skill2 Ability1", abilityOne._name);
	}

	@Test
	public void getUpgradeableAbilitiesReturnsTwoAbilitiesForSecondLevel() {
		AbilityTree tree = loadTestSkillTree();
		tree.levelUp();
		tree.upgradeAbility(0);

		ArrayList<Ability> secondLevelAbilities = tree.getUpgradeableAbilities();
		assertEquals(3, secondLevelAbilities.size());

		ValueAbility ability1 = (ValueAbility) secondLevelAbilities.get(0);
		assertEquals("Skill2 Ability1", ability1._name);
		assertEquals(1, ability1.abilityLevel);
		assertEquals("Skill2 Ability2", secondLevelAbilities.get(1)._name);
		assertEquals("Skill2 Ability3", secondLevelAbilities.get(2)._name);
	}

	@Test
	public void fullyUpgradingAValueAbilityRemovesItFromUpgradeableAbilities() {
		AbilityTree tree = loadTestSkillTree();
		ValueAbility abilityOne = (ValueAbility) tree.getUpgradeableAbilities().get(0);
		tree.levelUp();
		tree.upgradeAbility(0);
		assertEquals(1, abilityOne.abilityLevel);
		tree.levelUp();
		tree.upgradeAbility(0);
		assertEquals(2, abilityOne.abilityLevel);
		tree.levelUp();
		tree.upgradeAbility(0);
		assertEquals(3, abilityOne.abilityLevel);
		tree.levelUp();
		tree.upgradeAbility(0);
		assertEquals(4, abilityOne.abilityLevel);
		tree.levelUp();
		
		boolean exceptionThrown = false;
		try {
			tree.upgradeAbility(0);
		} catch (AbilityAtMaxLevelException e) {
			exceptionThrown = true;
		}
		assertTrue(exceptionThrown);
	}
	//endregion

	@Test
	public void canUpgradeFirstAbilityAfterALevelUp() {
		AbilityTree tree = loadTestSkillTree();
		ValueAbility abilityOne = (ValueAbility) tree.getUpgradeableAbilities().get(0);
		assertEquals(0, abilityOne.abilityLevel);
		tree.levelUp();
		tree.upgradeAbility(0);
		assertEquals(1, abilityOne.abilityLevel);
	}

	@Test
	public void cannotUpgradeFirstAbilityWithoutLevelUp() {
		AbilityTree tree = loadTestSkillTree();
		ValueAbility abilityOne = (ValueAbility) tree.getUpgradeableAbilities().get(0);
		assertEquals(0, abilityOne.abilityLevel);
		
		boolean exceptionThrown = false;
		try {
			tree.upgradeAbility(0);
		} catch (NotEnoughSkillPointsException e) {
			exceptionThrown = true;
		}
		assertTrue(exceptionThrown);

		assertEquals(0, abilityOne.abilityLevel);
	}

	private AbilityTree loadTestSkillTree() {
		ArrayList<Skill> skills = SkillAndAbilityLoader.loadSkills(testXmlFilePath);
		return skills.get(1)._abilities;
	}

}