package map;

public class GridPoint {

	public final int x;
	public final int y;

	public GridPoint(int X, int Y) {
		x = X;
		y = Y;
	}

	public boolean isValidSquare(int gridMaxX, int gridMaxY) {
		if (x < 0 || gridMaxX <= x) {
			////System.out.println("Not valid: x = " + x + " maxX = " + gridMaxX);
			return false;
		} else if (y < 0 || gridMaxY <= y) {
			////System.out.println("Not valid: y = " + y + " maxY = " + gridMaxY);
			return false;
		} else return true;
	}

	public int comparePoints(GridPoint point) {
		int xDist = getXDistance(point.x);
		int yDist = getYDistance(point.y);
		if (xDist == 0) {
			//System.out.println("No x distance, using vertical y distance" + yDist);
			return yDist;
		}
		if (yDist == 0) {
			//System.out.println("No y distance, using horizontal x distance" + xDist);
			return xDist;
		}
		double xSq = Math.pow((double) xDist, 2);
		double ySq = Math.pow((double) yDist, 2);
		//System.out.println("x sq = " + xSq);
		//System.out.println("y sq = " + ySq);
		//System.out.println("sum = " + xSq + ySq);
		//System.out.println("double sqrt = " + Math.sqrt(xSq + ySq));
		int sqi = (int) Math.sqrt(xSq + ySq);
		//System.out.println("int sqrt = " + sqi);
		return sqi;
	}

	private int getXDistance(int pointX) {
		int d = 0;
		if (x <= pointX) d = pointX - x;
		else if (x == pointX) d = 0;
		else d = x - pointX;
		//System.out.println("X dist = " + d);
		return d;
	}

	private int getYDistance(int pointY) {
		if (y <= pointY) return pointY - y;
		else if (y == pointY) return 0;
		else return y - pointY;
	}
}
