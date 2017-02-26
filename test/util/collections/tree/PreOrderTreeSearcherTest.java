package util.collections.tree;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PreOrderTreeSearcherTest {

	static PreOrderTreeSearcher<Integer> searcher;
	private static Tree<Integer> searchTree;

	@BeforeAll
	static void setUp() {
		searcher = new PreOrderTreeSearcher<>();
		TreeNode<Integer> root = new TreeNode<>(null, 1);
		TreeNode<Integer> two = new TreeNode<>(root,2);
		TreeNode<Integer> three = new TreeNode<>(root,3);
		TreeNode<Integer> four = new TreeNode<>(two,4);
		TreeNode<Integer> five = new TreeNode<>(three,5);
		searchTree = new Tree<>(root);
	}

	@Test
	void getElementWorksForRootElementInTree() {
		assertNotNull(searcher.getElement(searchTree, i -> i == 1));
	}

	@Test
	void getElementWorksForBranchElementInTree() {
		assertNotNull(searcher.getElement(searchTree, i -> i == 3));
	}

	@Test
	void getElementWorksForLeafElementInTree() {
		assertNotNull(searcher.getElement(searchTree, i -> i == 4));
	}

	@Test
	void getElementReturnsNullForElementNotInTree() {
		assertNull(searcher.getElement(searchTree, i -> i == 6));
	}

	@Test
	void doesTreeContainReturnsTrueForRootElementInTree() {
		assertTrue(searcher.doesTreeContain(searchTree, i -> i == 1));
	}

	@Test
	void doesTreeContainReturnsTrueForElementInTree() {
		assertTrue(searcher.doesTreeContain(searchTree, i -> i == 4));
	}

	@Test
	void doesTreeContainReturnsFalseForElementNotInTree() {
		assertFalse(searcher.doesTreeContain(searchTree, i -> i == 9));
	}

}