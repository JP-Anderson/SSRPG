package util.collections.tree;

import java.lang.reflect.Field;
import java.util.List;

public class ConsoleTreePrinter<E> {

	int nodeCount = 0;

	public void printTree(Tree<E> tree) {
		TreeNode<E> root = tree.getRoot();
		print(root);
	}

	protected void print(TreeNode<E> root) {
		print(root, "", true);
	}

	protected void print(TreeNode<E> node, String prefix, boolean isTail) {
		String informationToPrint = getStringToPrint(node.getNodeItem());
		printNodeInformation(prefix, isTail, informationToPrint);
		nodeCount++;
		List<TreeNode<E>> children = node.getChildren();
		for (int i = 0; i < children.size() - 1; i++) {
			print(children.get(i),prefix + (isTail ? "    " : "|   "), false);
		}
		if (children.size() > 0) {
			print(children.get(children.size() - 1),prefix + (isTail ?"    " : "|   "), true);
		}
	}

	protected String getStringToPrint(E object) {
		String stringToPrint = null;
		Field[] fields = object.getClass().getFields();
		for (Field f : fields) {
			if (f.getName().equals("name")) try {
				stringToPrint = (String) f.get(object);
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		if (stringToPrint == null) stringToPrint = "";
		return stringToPrint;
	}

	protected void printNodeInformation(String prefix, boolean isTail, String information) {
		System.out.println(prefix + (isTail ? "^-> " : "|-> ") + information);
	}

}
