package util.collections.tree;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TreeTest {

	private static Tree<Integer> searchTree;

	@BeforeAll
	static void setUp() {
		TreeNode<Integer> root = new TreeNode<>(null, 1);
		TreeNode<Integer> two = new TreeNode<>(root,2);
		TreeNode<Integer> three = new TreeNode<>(root,3);
		TreeNode<Integer> four = new TreeNode<>(two,4);
		TreeNode<Integer> five = new TreeNode<>(three,5);
		searchTree = new Tree<Integer>(root);
	}

	@Test
	public void getRootReturnsCorrectResult() {
		assertEquals(1, Math.toIntExact(searchTree.getRoot().getNodeItem()));
	}

	@Test
	public void assertRootHasCorrectChildren() {
		assertEquals(2, searchTree.getRoot().getChildren().size());
	}

}