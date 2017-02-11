package util.dataload.xml;

import characters.Skill;
import java.util.ArrayList;

import characters.abilities.Ability;
import characters.abilities.AbilityTree;
import org.w3c.dom.*;

public class SkillAndAbilityLoader extends XMLLoader {

	private static int skillCounter = 0;
	private static int abilityCounter = 0;

	private static int assignSkillID() {
		return skillCounter++;
	}

	private static int assignAbilityID() {
		return abilityCounter++;
	}

	public static ArrayList<Skill> loadSkills() {
		return loadSkillsFromXML();
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
		Ability rootAbility = new Ability(assignAbilityID(), "Name", "D", 0);
		AbilityTree tree = new AbilityTree();
		return tree;
	}

	private static Node getRootAbilityNode(Node skillNode) {
		Node n = skillNode.getFirstChild();
		while (n.getNodeName() != "Ability") {
			n = n.getNextSibling();
		}
		return n;
	}

	private static Ability createAbilityFromNode(Node abilityNode) {
		return new Ability(
				assignAbilityID(),
				getNamedAttributeFromNode(abilityNode, "Name"),
				getNamedAttributeFromNode(abilityNode, "Description"),
				0);
	}

}
