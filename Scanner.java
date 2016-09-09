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
        for (int yIndex = 0; yIndex < gridMap.getHeight(); yIndex++) {
            printRow(yIndex);
        }
    }

    private void printHeading() {
        System.out.print("  ");
        for (int xIndex = 0; xIndex < gridMap.getWidth(); xIndex++) {
            for (int blockXIndex = 0; blockXIndex < blockSize; blockXIndex++) {
                if (indexIsInCentre(blockXIndex)) {
                    System.out.print(" " + xCoOrdinates.charAt(xIndex) + " ");
                } else System.out.print("   ");
            }
        }
        System.out.println("");
        printHeadingUnderline();
    }

    private void printHeadingUnderline() {
        System.out.print("  ");
        for (int xIndex = 0; xIndex < gridMap.getWidth(); xIndex++) {
            printCharacterXTimes('_',blockSize*3);
        }
        System.out.println("");
    }

    private void printRow(int yIndex) {
        for (int blockYIndex = 0; blockYIndex < blockSize; blockYIndex++) {
            printBlockRow(yIndex, blockYIndex);
        }
    }

    private void printBlockRow(int yIndex, int blockYIndex) {
        for (int xIndex = 0; xIndex < gridMap.getWidth(); xIndex++) {
            if (xIndex == 0) {
                printLeftBorder(yIndex, blockYIndex);
            }
            for (int blockXIndex = 0; blockXIndex < blockSize; blockXIndex++ ) {
                if (indexIsInCentre(blockXIndex) && indexIsInCentre(blockYIndex)) {
                    printBlockCentreRow(yIndex, blockYIndex, xIndex);
                } else {
                    printBlockOuterRow(blockXIndex, blockYIndex);
                }
            }
        }
        System.out.println("");
    }

    private void printLeftBorder(int yIndex, int blockYIndex) {
        if (indexIsInCentre(blockYIndex)) {
            System.out.print(yIndex + "|");
        } else System.out.print(" |");
    }

    private void printBlockCentreRow(int yIndex, int blockYIndex, int xIndex) {
        GridSquare currentSquare = gridMap.getSquareAt(new GridPoint(xIndex,yIndex));
        if ( ! (currentSquare instanceof EmptyGridSquare) ) {
            System.out.print("-" + currentSquare.symbol + "-");
        } else System.out.print("   ");
    }

    private void printBlockOuterRow(int blockXIndex, int blockYIndex) {
        if (indexIsInCentre(blockXIndex)) {
            System.out.print(" | ");
        } else if (indexIsInCentre(blockYIndex)) {
            System.out.print("---");
        } else System.out.print("   ");
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
