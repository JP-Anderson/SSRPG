public class GridPoint {

  public final int x;
  public final int y;

  public GridPoint(int X, int Y) {
    x = X;
    y = Y;
  }

  public int comparePoints(GridPoint point) {
    int xDist = getXDistance(point.x);
    int yDist = getYDistance(point.y);
    if (xDist == 0) {
      System.out.println("No x distance, using vertical y distance");
      return yDist;
    }
    if (yDist == 0) {
      System.out.println("No y distance, using horizontal x distance");
      return xDist;
    }
    int xSq = xDist*xDist;
    int ySq = yDist*yDist;
    System.out.println("x sq = " + xSq);
    System.out.println("y sq = " + ySq);
    return (int)(Math.sqrt(xSq+ySq));
  }

  private int getXDistance(int pointX) {
    if (x <= pointX) return pointX - x;
    else return x - pointX;
  }

  private int getYDistance(int pointY) {
    if (y <= pointY) return pointY - y;
    else return y - pointY;
  }
}
