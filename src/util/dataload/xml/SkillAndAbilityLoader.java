package util.dataload.xml;

import characters.skills.Skill;

import java.util.ArrayList;

import characters.skills.SkillsHolder;
import characters.skills.abilities.*;
import org.w3c.dom.*;
import util.collections.tree.Tree;
import util.collections.tree.TreeNode;

public class SkillAndAbilityLoader extends XMLLoader {

	public static ArrayList<Skill> loadSkills() {
		return loadSkillsFromXML("C:\\Workspaces\\SSRPG\\dat\\abilities.xml");
	}

	public static ArrayList<Skill> loadSkills(String testXmlPath) { return loadSkillsFromXML(testXmlPath); }

	private static int skillCounter = 0;
	private static int abilityCounter = 0;

	private static ArrayList<Skill> loadSkillsFromXML(String xmlFilePath) {
		resetSkillAndAbilityTrackers();
		ArrayList<Skill> skills = new ArrayList<>();

		Document xmlDoc = getDocTreeForFile(xmlFilePath);
		NodeList nodes = xmlDoc.getElementsByTagName("Skill");

		for (int i = 0; i < nodes.getLength(); i++) {
			skills.add(generateSkillFromNode(nodes.item(i)));
		}

		return skills;
	}

	private static void resetSkillAndAbilityTrackers() {
		skillCounter = 0;
		abilityCounter = 0;
		SkillsHolder.ABILITY_ENUM.clear();
		SkillsHolder.SKILLS_ENUM.clear();
	}

	private static Skill generateSkillFromNode(Node node) {
		String skillName = getNamedAttributeFromNode(node, "Name");
		AbilityTree skillTree = generateAbilityTreeFromNode(node);
		return new Skill(assignSkillID(skillName), skillName, skillTree);
	}

	private static String getNamedAttributeFromNode(Node node, String attributeName) {
		NamedNodeMap attributeMap = node.getAttributes();
		Node nameNode = attributeMap.getNamedItem(attributeName);
		return nameNode.getNodeValue();
	}

	private static AbilityTree generateAbilityTreeFromNode(Node skillNode) {
		Node rootAbilityNode = getRootAbilityNode(skillNode);
		Ability rootAbility = createAbilityFromNode(rootAbilityNode);
		TreeNode<Ability> rootTreeNode = new TreeNode<>(null, rootAbility);
		recursiveAddAbilitiesToParent(rootTreeNode, rootAbilityNode);
		Tree<Ability> tree = new Tree<>(rootTreeNode);
		AbilityTree aTree = new AbilityTree(tree);
		return aTree;
	}

	private static Node getRootAbilityNode(Node skillNode) {
		Node n = skillNode.getFirstChild();
		while (!n.getNodeName().equals("Ability")) {
			n = n.getNextSibling();
		}
		return n;
	}

	private static void recursiveAddAbilitiesToParent(TreeNode<Ability> treeNode, Node xmlNode) {
		NodeList children = xmlNode.getChildNodes();
		for (int i = 0; i < children.getLength(); i++) {
			Node xmlChild = children.item(i);
			if (xmlChild.getNodeName().equals("Ability")) {
				Ability childAbility = createAbilityFromNode(xmlChild);
				TreeNode<Ability> treeNodeChild = new TreeNode<>(treeNode, childAbility);
				recursiveAddAbilitiesToParent(treeNodeChild, xmlChild);
			}
		}
	}

	private static Ability createAbilityFromNode(Node abilityNode) {
		String type = getNamedAttributeFromNode(abilityNode, "Type");
		String name = getNamedAttributeFromNode(abilityNode, "Name");
		String description = getNamedAttributeFromNode(abilityNode, "Description");

		if (type.equals("Boolean")) return createBooleanAbility(name, description);

		String values = getNamedAttributeFromNode(abilityNode, "ValuePerLevel");
		if (type.equals("Int")) return createIntAbility(name, description, values);
		if (type.equals("Float")) return createDoubleAbility(name, description, values);
		return null;
	}

	private static BooleanAbility createBooleanAbility(String name, String description) {
		return new BooleanAbility(assignAbilityID(name), name, description);
	}

	private static IntAbility createIntAbility(String name, String description, String valueList) {
		return new IntAbility(assignAbilityID(name), name, description, valueList);
	}

	private static DoubleAbility createDoubleAbility(String name, String description, String valueList) {
		return new DoubleAbility(assignAbilityID(name), name, description, valueList);
	}

	private static int assignSkillID(String skillName) {
		skillCounter++;
		SkillsHolder.SKILLS_ENUM.put(skillName, skillCounter);
		return skillCounter;
	}

	private static int assignAbilityID(String abilityName) {
		abilityCounter++;
		SkillsHolder.ABILITY_ENUM.put(abilityName, abilityCounter);
		return abilityCounter;
	}

	// getters for skill and ability counter, just for testing purposes

	static int getSkillCounter() {
		return skillCounter;
	}

	static int getAbilityCounter() {
		return abilityCounter;
	}
}
