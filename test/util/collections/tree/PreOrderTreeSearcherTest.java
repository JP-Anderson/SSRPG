package util.collections.tree;

import org.junit.Before;
import org.junit.Test;

import base.SsrpgTest;

import static org.junit.Assert.*;

public class PreOrderTreeSearcherTest extends SsrpgTest {

	PreOrderTreeSearcher<Integer> searcher;
	private Tree<Integer> searchTree;

	@Before
	public void setUp() {
		searcher = new PreOrderTreeSearcher<>();
		TreeNode<Integer> root = new TreeNode<>(null, 1);
		TreeNode<Integer> two = new TreeNode<>(root,2);
		TreeNode<Integer> three = new TreeNode<>(root,3);
		TreeNode<Integer> four = new TreeNode<>(two,4);
		TreeNode<Integer> five = new TreeNode<>(three,5);
		searchTree = new Tree<>(root);
	}

	@Test
	public void getElementWorksForRootElementInTree() {
		assertNotNull(searcher.getElement(searchTree, i -> i == 1));
	}

	@Test
	public void getElementWorksForBranchElementInTree() {
		assertNotNull(searcher.getElement(searchTree, i -> i == 3));
	}

	@Test
	public void getElementWorksForLeafElementInTree() {
		assertNotNull(searcher.getElement(searchTree, i -> i == 4));
	}

	@Test
	public void getElementReturnsNullForElementNotInTree() {
		assertNull(searcher.getElement(searchTree, i -> i == 6));
	}

	@Test
	public void doesTreeContainReturnsTrueForRootElementInTree() {
		assertTrue(searcher.doesTreeContain(searchTree, i -> i == 1));
	}

	@Test
	public void doesTreeContainReturnsTrueForElementInTree() {
		assertTrue(searcher.doesTreeContain(searchTree, i -> i == 4));
	}

	@Test
	public void doesTreeContainReturnsFalseForElementNotInTree() {
		assertFalse(searcher.doesTreeContain(searchTree, i -> i == 9));
	}

}