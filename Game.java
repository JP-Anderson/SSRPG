public class Game {

  public static void main(String[] args) {
    System.out.println("Making planet Earth");
    Planet earth = new Planet("Earth", new GridPoint(2,3),9);

    System.out.println("Making planet Jupiter");
    Planet jupiter = new Planet("Jupiter", new GridPoint(6,6),9);

    System.out.println("Goods available at planet " + earth.name);
    for (Goods g : earth.market.availableGoods) {
      System.out.println(g.name + " " + g.getActualValue());
    }

    System.out.println("");
    System.out.println("Initialising scanner");
    Scanner scanner = new Scanner();

    GridMap map = GridMap.generateGridMap(11,7);
    map.populateGridSquare(earth);
    map.populateGridSquare(jupiter);

    scanner.scan(map);



  }
}
