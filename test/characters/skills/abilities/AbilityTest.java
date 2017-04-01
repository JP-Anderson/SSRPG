package characters.skills.abilities;

import characters.exceptions.AbilityAtMaxLevelException;
import characters.exceptions.AbilityNotUnlockedException;
import characters.skills.Skill;
import ship.ShipModules;

import org.junit.Test;

import base.SsrpgTest;
import util.collections.tree.PreOrderTreeMatcher;
import util.dataload.xml.SkillAndAbilityLoader;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class AbilityTest extends SsrpgTest {
	
	String testXmlFilePath = "C:\\Workspaces\\SSRPG\\dat\\test\\test_abilities.xml";

	@Test
	public void lockedAbilitiesIdentifyAsLocked() {
		IntAbility intAbility = getIntAbility();
		DoubleAbility doubleAbility = getDoubleAbility();
		BooleanAbility booleanAbility = getBooleanAbility();

		assertFalse(intAbility.isUnlocked() && doubleAbility.isUnlocked() && booleanAbility.isUnlocked());
	}

	@Test
	public void canOnlyUpgradeBooleanAbilityOnce() {
		BooleanAbility booleanAbility = getBooleanAbility();
		booleanAbility.levelUp();

		assertTrue(booleanAbility.isUnlocked());
		assertTrue(booleanAbility.isFullyUnlocked());
		
		boolean exceptionThrown = false;
		try {
			booleanAbility.levelUp();
		} catch (AbilityAtMaxLevelException e) {
			exceptionThrown = true;
		}
		assertTrue(exceptionThrown);
	}

	@Test
	public void canUpgradeIntAbilityUntilMaxLevel() {
		IntAbility intAbility = getIntAbility();
		int levels = intAbility.levels;

		for (int i = 0; i < levels-1; i++) {
			intAbility.levelUp();
			assertTrue(intAbility.isUnlocked());
			assertFalse(intAbility.isFullyUnlocked());
		}

		intAbility.levelUp();
		assertTrue(intAbility.isFullyUnlocked());
		
		boolean exceptionThrown = false;
		try {
			intAbility.levelUp();
		} catch (AbilityAtMaxLevelException e) {
			exceptionThrown = true;
		}
		assertTrue(exceptionThrown);
	}

	@Test
	public void canUpgradeDoubleAbilityUntilMaxLevel() {
		DoubleAbility doubleAbility = getDoubleAbility();
		int levels = doubleAbility.levels;

		doubleAbility.levelUp();
		assertTrue(doubleAbility.isUnlocked());
		assertFalse(doubleAbility.isFullyUnlocked());

		doubleAbility.levelUp();
		assertTrue(doubleAbility.isFullyUnlocked());

		boolean exceptionThrown = false;
		try {
			doubleAbility.levelUp();
		} catch (AbilityAtMaxLevelException e) {
			exceptionThrown = true;
		}
		assertTrue(exceptionThrown);
	}

	@Test
	public void intAbilityReturnsCorrectIntValue() {
		IntAbility intAbility = getIntAbility();
		

		boolean exceptionThrown = false;
		try {
			intAbility.getAbilityValue();
		} catch (AbilityNotUnlockedException e) {
			exceptionThrown = true;
		}
		assertTrue(exceptionThrown);

		intAbility.levelUp();
		assertEquals(1, (int) intAbility.getAbilityValue());

		intAbility.levelUp();
		assertEquals(2, (int) intAbility.getAbilityValue());

		intAbility.levelUp();
		assertEquals(3, (int) intAbility.getAbilityValue());

		intAbility.levelUp();
		assertEquals(4, (int) intAbility.getAbilityValue());
	}

	@Test
	public void doubleAbilityReturnsCorrectDoubleValue() {
		DoubleAbility doubleAbility = getDoubleAbility();

		boolean exceptionThrown = false;
		try {
			doubleAbility.getAbilityValue();
		} catch (AbilityNotUnlockedException e) {
			exceptionThrown = true;
		}
		assertTrue(exceptionThrown);
		
		doubleAbility.levelUp();
		assertEquals(0.25, (double) doubleAbility.getAbilityValue(), 0D);

		doubleAbility.levelUp();
		assertEquals(0.5, (double) doubleAbility.getAbilityValue(), 0D);
	}


	private IntAbility getIntAbility() {
		PreOrderTreeMatcher<Ability> searcher = new PreOrderTreeMatcher<>();
		return (IntAbility) searcher.getMatchingNodesAndUnbox(
				loadTestAbilityTree().getTree(),
				tn -> tn.getNodeItem()._name.equals("Skill2 Ability1")).get(0);
	}

	private DoubleAbility getDoubleAbility() {
		PreOrderTreeMatcher<Ability> searcher = new PreOrderTreeMatcher<>();
		return (DoubleAbility) searcher.getMatchingNodesAndUnbox(
				loadTestAbilityTree().getTree(),
				tn -> tn.getNodeItem()._name.equals("Skill2 Ability2")).get(0);
	}

	private BooleanAbility getBooleanAbility() {
		PreOrderTreeMatcher<Ability> searcher = new PreOrderTreeMatcher<>();
		return (BooleanAbility) searcher.getMatchingNodesAndUnbox(
				loadTestAbilityTree().getTree(),
				tn -> tn.getNodeItem()._name.equals("Skill2 Ability4")).get(0);
	}

	private AbilityTree loadTestAbilityTree() {
		ArrayList<Skill> skills = SkillAndAbilityLoader.loadSkills(testXmlFilePath);
		return skills.get(1)._abilities;
	}

}