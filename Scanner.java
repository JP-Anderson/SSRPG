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
        for (int rowIndex = 0; rowIndex < gridMap.getHeight(); rowIndex++) {
            printRow(rowIndex);
        }
    }

    private void printHeading() {
        System.out.print("  ");
        for (int colIndex = 0; colIndex < gridMap.getWidth(); colIndex++) {
            for (int blockColIndex = 0; blockColIndex < blockSize; blockColIndex++) {
                if (indexIsInCentre(blockColIndex)) {
                    System.out.print(" " + xCoOrdinates.charAt(colIndex) + " ");
                } else System.out.print("   ");
            }
        }
        System.out.println("");
        printHeadingUnderline();
    }

    private void printHeadingUnderline() {
        System.out.print("  ");
        for (int colIndex = 0; colIndex < gridMap.getWidth(); colIndex++) {
            printCharacterXTimes('_',blockSize*3);
        }
        System.out.println("");
    }

    private void printRow(int rowIndex) {
        for (int blockRowIndex = 0; blockRowIndex < blockSize; blockRowIndex++) {
            printBlockRow(rowIndex, blockRowIndex);
        }
    }

    private void printBlockRow(int rowIndex, int blockRowIndex) {
        for (int colIndex = 0; colIndex < gridMap.getWidth(); colIndex++) {
            if (colIndex == 0) {
                printLeftBorder(rowIndex, blockRowIndex);
            }
            for (int blockColIndex = 0; blockColIndex < blockSize; blockColIndex++ ) {
                if (indexIsInCentre(blockColIndex) && indexIsInCentre(blockRowIndex)) {
                    printBlockCentreRow(rowIndex, blockRowIndex, colIndex);
                } else {
                    printBlockOuterRow(blockColIndex, blockRowIndex);
                }
            }
        }
        System.out.println("");
    }

    private void printLeftBorder(int rowIndex, int blockRowIndex) {
        if (indexIsInCentre(blockRowIndex)) {
            System.out.print(rowIndex + "|");
        } else System.out.print(" |");
    }

    private void printBlockCentreRow(int rowIndex, int blockRowIndex, int colIndex) {
        GridSquare currentSquare = gridMap.getSquareAt(new GridPoint(colIndex,rowIndex));
        if ( ! (currentSquare instanceof EmptyGridSquare) ) {
            System.out.print("-" + currentSquare.symbol + "-");
        } else System.out.print("   ");
    }

    private void printBlockOuterRow(int blockColIndex, int blockRowIndex) {
        if (indexIsInCentre(blockColIndex)) {
            System.out.print(" | ");
        } else if (indexIsInCentre(blockRowIndex)) {
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
