package util.collections.tree;

import org.junit.Before;
import org.junit.Test;

import base.SsrpgTest;

import static org.junit.Assert.*;

public class TreeTest extends SsrpgTest {

	private Tree<Integer> searchTree;

	@Before
	public void setUp() {
		TreeNode<Integer> root = new TreeNode<>(null, 1);
		TreeNode<Integer> two = new TreeNode<>(root,2);
		TreeNode<Integer> three = new TreeNode<>(root,3);
		TreeNode<Integer> four = new TreeNode<>(two,4);
		TreeNode<Integer> five = new TreeNode<>(three,5);
		searchTree = new Tree<>(root);
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