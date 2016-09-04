public class EmptyGridSquare extends GridSquare {

  public static EmptyGridSquare generateEmptyGridSquare(GridPoint gridPoint) {
    return new EmptyGridSquare(gridPoint, ' ');
  }

  private EmptyGridSquare(GridPoint gridPoint, char mapSymbol) {
    super(gridPoint, mapSymbol);
  }

}
