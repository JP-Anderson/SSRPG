package util.dataload.xml;

import characters.skills.Skill;
import java.util.ArrayList;

import characters.skills.abilities.*;
import org.w3c.dom.*;
import util.collections.tree.Tree;
import util.collections.tree.TreeNode;

public class SkillAndAbilityLoader extends XMLLoader {

	public static ArrayList<Skill> loadSkills() {
		return loadSkillsFromXML();
	}

	private static int skillCounter = 0;
	private static int abilityCounter = 0;

	private static int assignSkillID() {
		return skillCounter++;
	}

	private static int assignAbilityID() {
		return abilityCounter++;
	}

	private static ArrayList<Skill> loadSkillsFromXML() {
		ArrayList<Skill> skills = new ArrayList<>();

		Document xmlDoc = getDocTreeForFile("C:\\Workspaces\\SSRPG\\dat\\abilities.xml");
		NodeList nodes = xmlDoc.getElementsByTagName("Skill");

		for (int i = 0; i < nodes.getLength(); i++) {
			skills.add(generateSkillFromNode(nodes.item(i)));
		}

		return skills;
	}

	private static Skill generateSkillFromNode(Node node) {
		String skillName = getNamedAttributeFromNode(node, "Name");
		AbilityTree skillTree = generateAbilityTreeFromNode(node);
		return new Skill(assignSkillID(), skillName, skillTree);
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
				treeNode.addChild(treeNodeChild);
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
		return new BooleanAbility(assignAbilityID(), name, description);
	}

	private static IntAbility createIntAbility(String name, String description, String valueList) {
		return new IntAbility(assignAbilityID(), name, description, valueList);
	}

	private static DoubleAbility createDoubleAbility(String name, String description, String valueList) {
		return new DoubleAbility(assignAbilityID(), name, description, valueList);
	}

}
