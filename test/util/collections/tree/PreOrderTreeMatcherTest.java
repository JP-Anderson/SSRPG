package util.collections.tree;

import org.junit.Before;
import org.junit.Test;

import base.SsrpgTest;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class PreOrderTreeMatcherTest extends SsrpgTest {

	PreOrderTreeMatcher<Integer> searcher;
	private Tree<Integer> searchTree;

	@Before
	public void setUp() {
		searcher = new PreOrderTreeMatcher<>();
		TreeNode<Integer> root = new TreeNode<>(null, 1);
		TreeNode<Integer> two = new TreeNode<>(root,2);
		TreeNode<Integer> three = new TreeNode<>(root,3);
		TreeNode<Integer> four = new TreeNode<>(two,4);
		TreeNode<Integer> five = new TreeNode<>(three,5);
		TreeNode<Integer> secondOne = new TreeNode<>(five,1);
		searchTree = new Tree<>(root);
	}

	@Test
	public void getIntegerNodesLessThanThree() {
		ArrayList<TreeNode<Integer>> matchingNodes = searcher.getMatchingNodes(searchTree, n -> n.getNodeItem() < 3);
		assertEquals(3, matchingNodes.size());
	}

	@Test
	public void getIntegersFromNodesLessThanThree() {
		ArrayList<Integer> matchingInts = searcher.getMatchingNodesAndUnbox(searchTree, n -> n.getNodeItem() < 3);
		assertEquals(3, matchingInts.size());
	}

	@Test
	public void getIntegerNodesBetweenOneAndFourInclusive() {
		ArrayList<TreeNode<Integer>> matchingNodes = searcher.getMatchingNodes(searchTree,
				n -> n.getNodeItem() >= 1 && n.getNodeItem() <= 4);
		assertEquals(5, matchingNodes.size());
	}

	@Test
	public void getIntegerNodesEqualToOne() {
		ArrayList<TreeNode<Integer>> matchingNodes = searcher.getMatchingNodes(searchTree, n -> n.getNodeItem() == 1);
		assertEquals(2, matchingNodes.size());
	}

	@Test
	public void getIntegerNodesEqualToFour() {
		ArrayList<TreeNode<Integer>> matchingNodes = searcher.getMatchingNodes(searchTree, n -> n.getNodeItem() == 4);
		assertEquals(1, matchingNodes.size());
	}

	@Test
	public void getAndUnboxNodesWithoutChildren() {
		ArrayList<Integer> matchingNodes = searcher.getMatchingNodesAndUnbox(searchTree,
				n -> n.getNumberOfChildren() == 0);

		assertTrue(matchingNodes.contains(1));
		assertTrue(matchingNodes.contains(4));
		assertEquals(2, matchingNodes.size());
	}

	@Test
	public void getAndUnboxNodesWithValueOneWithoutChildren() {
		ArrayList<Integer> matchingNodes = searcher.getMatchingNodesAndUnbox(searchTree,
				n -> n.getNumberOfChildren() == 0 && n.getNodeItem() == 1);

		assertTrue(matchingNodes.contains(1));
		assertEquals(1, matchingNodes.size());
	}

}