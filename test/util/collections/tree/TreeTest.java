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
	
	@Test
	public void cantAddTreeNodeToParentTwice() {
		// Creating parent and child node
		TreeNode<Integer> parent = new TreeNode<>(null, 1);
		TreeNode<Integer> child = new TreeNode<>(parent, 2);
		// Adding child to parent again
		parent.addChild(child);
		assertEquals(1, parent.getNumberOfChildren());		
	}
	
	@Test
	public void changingNodesParentRemovesItFromOldParentsChildren() {
		// Creating parent and child node
		TreeNode<Integer> firstParent = new TreeNode<>(null, 0);
		TreeNode<Integer> child = new TreeNode<>(firstParent, 2);
		TreeNode<Integer> newParent = new TreeNode<>(null, 1);
		// Adding child to new parent
		newParent.addChild(child);
		assertEquals(0, firstParent.getNumberOfChildren());
		assertEquals(1, newParent.getNumberOfChildren());
		assertEquals(newParent, child.getParent());
	}

}