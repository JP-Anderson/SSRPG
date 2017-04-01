package map;

import org.junit.Test;

import base.SsrpgTest;

import static org.junit.Assert.*;

public class GridPointTest extends SsrpgTest {


	@Test
	public void isValidSquareValidForBoundarySquare() {
		GridPoint gp = new GridPoint(0,6);
		assertTrue(gp.isValidSquare(7,7));
	}

	@Test
	public void isValidSquareInvalidForSquareOutsideBoundary() {
		GridPoint gp = new GridPoint(0,7);
		assertFalse(gp.isValidSquare(7,7));
	}

	@Test
	public void comparePointsReturnsZeroDistanceForSamePoints() {
		GridPoint gp1 = new GridPoint(5,5);
		GridPoint gp2 = new GridPoint(5,5);

		assertTrue(0 == gp1.comparePoints(gp2));
	}

	@Test
	public void comparePointsReturnsXDistanceForPointsOnSameXAxis() {
		GridPoint gp1 = new GridPoint(1,5);
		GridPoint gp2 = new GridPoint(5,5);

		assertTrue(4 == gp1.comparePoints(gp2));
	}

	@Test
	public void comparePointsReturnsYDistanceForPointsOnSameYAxis() {
		GridPoint gp1 = new GridPoint(2,9);
		GridPoint gp2 = new GridPoint(2,1);

		assertTrue(8 == gp1.comparePoints(gp2));
	}

	@Test
	public void comparePointsReturnsSumOfSquareOfTwoSides() {
		GridPoint gp1 = new GridPoint(1,2);
		GridPoint gp2 = new GridPoint(5,8);

		double xSq = Math.pow(gp2.x-gp1.x, 2);
		double ySq = Math.pow(gp2.y-gp1.y, 2);

		int distance = (int) Math.sqrt(xSq + ySq);

		assertTrue(distance == gp1.comparePoints(gp2));
	}

}