import java.util.ArrayList;

public class Scanner {

  public final static int SMALL = 3;

  public static final int blockSize = SMALL;
  private static final String xCoOrdinates = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

  public void scan(GridMap gridMap) {
    System.out.print("  ");
    for (int w = 0; w < gridMap.getWidth(); w++) {
      for (int bw = 0; bw < blockSize; bw++) {
        if (bw == blockSize/2) {
          System.out.print(" " + xCoOrdinates.charAt(w) + " ");
        } else System.out.print("   ");
      }
    }
	System.out.println("");

    System.out.print("  ");
    for (int w = 0; w < gridMap.getWidth(); w++) {
      printCharacterXTimes('_',blockSize*3);
    }
    System.out.println("");

    for (int h = 0; h < gridMap.getHeight(); h++) {
      for (int bh = 0; bh < blockSize; bh++) {
        for (int w = 0; w < gridMap.getWidth(); w++) {
          if (w == 0) {
            if (bh == blockSize/2) {
              System.out.print(h + "|");
            } else System.out.print(" |");
          }
          for (int bw = 0; bw < blockSize; bw++ ) {
            int centre = blockSize/2;
            if (bw == centre && bh == centre) {
              GridSquare currentSquare = gridMap.getSquareAt(new GridPoint(w,h));
              if ( ! (currentSquare instanceof EmptyGridSquare) ) {
                  System.out.print("-" + currentSquare.symbol + "-");
              } else System.out.print("-+-");
            } else if (bw == centre) {
              System.out.print(" | ");
            } else if (bh == centre) {
              System.out.print("---");
            } else System.out.print("   ");
          }
        }
        System.out.println("");
      }
    }
  }

  private void printCharacterXTimes(char ch, int x) {
    if (x > 0) {
      System.out.print(ch);
      printCharacterXTimes(ch,x-1);
    }
  }

  public void printSquare() {
    System.out.println(".");
  }

}
