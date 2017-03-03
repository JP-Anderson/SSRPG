package util.collections.tree;

import characters.skills.SkillsHolder;
import characters.skills.abilities.Ability;
import characters.skills.abilities.AbilityTree;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class ConsoleTreePrinter<E> extends TreePrinter<E> {

	public static void main(String[] args) {
//		TreeNode three = new TreeNode("3 - Ability (1/2)", emptyList());
//		TreeNode four = new TreeNode("4 - Ability 4 (0/2)", emptyList());
//		List<TreeNode> oneChildren = emptyList();
//		oneChildren.add(three);
//		oneChildren.add(four);
//
//		TreeNode one = new TreeNode("1 - Ability One (3/3)", oneChildren);
//		TreeNode two = new TreeNode("2 - Ability2 (1/2)", emptyList());
//		List<TreeNode> rChildren = emptyList();
//		rChildren.add(one);
//		rChildren.add(two);
//		TreeNode r = new TreeNode("r", rChildren);

		AbilityTree ability = SkillsHolder.getSkill(2)._abilities;

		ConsoleTreePrinter<Ability> ctp = new ConsoleTreePrinter<>();
		ctp.print(ability.getTree().getRoot());
	}

	private static List<TreeNode> emptyList() {
		return new ArrayList<>();
	}

	public void printTree(Tree<E> tree) {
		TreeNode<E> root = tree.getRoot();
		print(root);
	}

	private void print(TreeNode<E> root) {
		print(root, "", true);
	}

	private void print(TreeNode<E> node, String prefix, boolean isTail) {
		String name = getStringForObjectName(node.getNodeItem());
		System.out.println(prefix + (isTail ? "└── " : "├── ") + name);
		List<TreeNode<E>> children = node.getChildren();
		for (int i = 0; i < children.size() - 1; i++) {
			print(children.get(i),prefix + (isTail ? "    " : "│   "), false);
		}
		if (children.size() > 0) {
			print(children.get(children.size() - 1),prefix + (isTail ?"    " : "│   "), true);
		}
	}

	public String getStringForObjectName(E object) {
		Field[] fields = object.getClass().getFields();
		for (Field f : fields) {
			if (f.getName().equals("_name")) try {
				return (String) f.get(object);
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		return "";
	}

//	private static class TreeNode {
//
//		final String name;
//		final List<TreeNode> children;
//
//		public TreeNode(String name, List<TreeNode> children) {
//			this.name = name;
//			this.children = children;
//		}
//
//		public void print() {
//			print("", true);
//		}
//
//		private void print(String prefix, boolean isTail) {
//			System.out.println(prefix + (isTail ? "└── " : "├── ") + name);
//			for (int i = 0; i < children.size() - 1; i++) {
//				children.get(i).print(prefix + (isTail ? "    " : "│   "), false);
//			}
//			if (children.size() > 0) {
//				children.get(children.size() - 1)
//						.print(prefix + (isTail ?"    " : "│   "), true);
//			}
//		}
//
//	}

}
