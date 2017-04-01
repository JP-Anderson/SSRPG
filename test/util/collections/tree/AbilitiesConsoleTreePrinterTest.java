package util.collections.tree;

import characters.skills.Skill;
import characters.skills.abilities.Ability;
import org.junit.Test;

import base.SsrpgTest;
import util.dataload.xml.SkillAndAbilityLoader;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class AbilitiesConsoleTreePrinterTest extends SsrpgTest {

	private ArrayList<Skill> skills = SkillAndAbilityLoader.loadSkills("C:\\Workspaces\\SSRPG\\dat\\test\\test_abilities.xml");

	// No assertions, just used to run for verification in the console
	@Test
	public void printTestTree() {
		Tree<Ability> tree = skills.get(1)._abilities.getTree();
		AbilitiesConsoleTreePrinter treePrinter = new AbilitiesConsoleTreePrinter();
		treePrinter.printTree(tree);
	}

}