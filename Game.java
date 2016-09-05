import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Game {

  public static void main(String[] args) {

      Ship p1 = new Ship("Jp",15,3);
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
          map.populateGridSquare(new Planet(name,
          new GridPoint(gridX, gridY),
          marketSize));
          System.out.println("added " + name);
      }
      map.placePlayer(p1);
      GridPoint l = p1.getLocation();
      System.out.println(p1.name + "is at " + l.x + "," + l.y);

      System.out.println("Select a square to travel to: ('X','Y'):");
      System.out.print("X = ");
      int x = 2;
      int y = 2;
      try {
          BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
          x = Integer.parseInt(br.readLine());

      } catch (Exception io) {
          io.printStackTrace();
      }
      try {
          System.out.println("Y = ");
          BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
          y = Integer.parseInt(br.readLine());
      } catch (Exception io) {
          io.printStackTrace();
      }

      GridPoint destination = new GridPoint(x,y);
      int distance = p1.getLocation().comparePoints(destination);

      System.out.println("Distance of " + distance);

      scanner.scan(map);
  }
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
