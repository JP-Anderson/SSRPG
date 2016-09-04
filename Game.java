import java.util.ArrayList;

public class Game {

  public static void main(String[] args) {

    System.out.println("Initialising scanner");
    Scanner scanner = new Scanner();

    GridMap map = GridMap.generateGridMap(11,7);

    CSV planets = CSVReader.readCSV("planets");
    for (int i = 1; i < planets.rows; i++) {
      ArrayList<String> planet = planets.getZeroIndexedRow(i);
      String name = planet.get(0);
      int gridX = Integer.parseInt(planet.get(1));
      int gridY = Integer.parseInt(planet.get(2));
      int marketSize = Integer.parseInt(planet.get(3));

      map.populateGridSquare(new Planet(
                              name,
                              new GridPoint(gridX, gridY),
                              marketSize
      ));
      System.out.println("added " + name);
    }

    scanner.scan(map);

    // System.out.println("Making planet Earth");
    // Planet earth = new Planet("Earth", new GridPoint(2,3),9);
    //
    // System.out.println("Making planet Jupiter");
    // Planet jupiter = new Planet("Jupiter", new GridPoint(6,6),9);
    //
    // System.out.println("Goods available at planet " + earth.name);
    // for (Goods g : earth.market.availableGoods) {
    //   System.out.println(g.name + " " + g.getActualValue());
    // }
    //
    // System.out.println("");
    // map.populateGridSquare(earth);
    // map.populateGridSquare(jupiter);
    //



  }
}
