import java.util.ArrayList;

public class Scanner {

    public final GridMap gridMap;
    public final static int SMALL = 3;
    public static final int blockSize = SMALL;
    private static final String xCoOrdinates = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    public static Scanner scanner = null;

    public static Scanner getScanner(GridMap map) {
        if (scanner == null) {
        scanner = new Scanner(map);
        }
        return scanner;
    }

    private Scanner (GridMap map) {
        gridMap = map;
    }

    public void scan() {
        printHeading();
        for (int yPoint = 0; yPoint < gridMap.getHeight(); yPoint++) {
            printRow(yPoint);
        }
    }

    private void printHeading() {
        System.out.print("  ");
        for (int xPoint = 0; xPoint < gridMap.getWidth(); xPoint++) {
            for (int blockXPoint = 0; blockXPoint < blockSize; blockXPoint++) {
                if (indexIsInCentre(blockXPoint)) {
                    System.out.print(" " + xCoOrdinates.charAt(xPoint) + " ");
                } else System.out.print("   ");
            }
        }
        System.out.println("");
        printHeadingUnderline();
    }

    private void printHeadingUnderline() {
        System.out.print("  ");
        for (int xPoint = 0; xPoint < gridMap.getWidth(); xPoint++) {
            printCharacterXTimes('_',blockSize*3);
        }
        System.out.println("");
    }

    private void printRow(int yPoint) {
        for (int blockYPoint = 0; blockYPoint < blockSize; blockYPoint++) {
            for (int xPoint = 0; xPoint < gridMap.getWidth(); xPoint++) {
                if (xPoint == 0) {
                    if (indexIsInCentre(blockYPoint)) {
                        System.out.print(yPoint + "|");
                    } else System.out.print(" |");
                }
                for (int blockXPoint = 0; blockXPoint < blockSize; blockXPoint++ ) {
                    if (indexIsInCentre(blockXPoint) && indexIsInCentre(blockYPoint)) {
                        GridSquare currentSquare = gridMap.getSquareAt(new GridPoint(xPoint,yPoint));
                        if ( ! (currentSquare instanceof EmptyGridSquare) ) {
                            System.out.print("-" + currentSquare.symbol + "-");
                        } else System.out.print("   ");
                    } else if (indexIsInCentre(blockXPoint)) {
                        System.out.print(" | ");
                    } else if (indexIsInCentre(blockYPoint)) {
                        System.out.print("---");
                    } else System.out.print("   ");
                }
            }
            System.out.println("");
        }
    }

    private boolean indexIsInCentre(int index) {
        return index == blockSize/2;
    }

    private void printCharacterXTimes(char ch, int x) {
        if (x > 0) {
            System.out.print(ch);
            printCharacterXTimes(ch,x-1);
        }
    }

}
