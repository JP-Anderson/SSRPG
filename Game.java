import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Game {

  public static void main(String[] args) {

        GridMap map = GridMap.generateGridMap(11,7);
        System.out.println("Initialising scanner");

        GridPoint start = new GridPoint(1,2);
        Ship p1 = new Ship("Jp",15,3,map,start);

      CSV planets = CSVReader.readCSV("planets");
      CSV goodsCSV = CSVReader.readCSV("goods");
      ArrayList<Goods> goods = new ArrayList<Goods>();
      for (int i = 1; i < goodsCSV.rows; i++) {
          ArrayList<String> values = goodsCSV.getZeroIndexedRow(i);
          goods.add(new Goods(Integer.parseInt(values.get(0)),
            values.get(1),
            Goods.Legality.values()[Integer.parseInt(values.get(2))],
            Integer.parseInt(values.get(3))
          ));
          //System.out.println("goods:" + values.toString());
      }
      for (int i = 1; i < planets.rows; i++) {
        ArrayList<String> planet = planets.getZeroIndexedRow(i);
        int id = Integer.parseInt(planet.get(0));
        String name = planet.get(1);
        int gridX = Integer.parseInt(planet.get(2));
        int gridY = Integer.parseInt(planet.get(3));
        int marketSize = Integer.parseInt(planet.get(4));
        map.populateGridSquare(new Planet(id,
        name,
        new GridPoint(gridX, gridY),
        marketSize));
        //System.out.println("added " + name);
      }
      boolean firstRun = true;
      while (true) {
          GridPoint l = p1.getLocation();

          String xLetters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
          System.out.println(p1.name + " is at " + xLetters.charAt(l.x) + "," + l.y);
          String input = null;
          System.out.print(">> ");
          try {
              BufferedReader commandReader = new BufferedReader(new InputStreamReader(System.in));
              input = commandReader.readLine();
          } catch (Exception io) {
              io.printStackTrace();
          }

          if (input.equalsIgnoreCase("scan")) {
              p1.scan();
          } else if (input.equalsIgnoreCase("trade")) {
              GridSquare location = map.getSquareAt(p1.getLocation());
              if (location instanceof Planet) {
                  Planet planet = (Planet) location;
                  ArrayList<Goods> availableGoods = planet.market.availableGoods;
                  System.out.println("GOODS:");
                  for (Goods g : availableGoods) {
                      System.out.println(" - " + g.name + " : " + g.getActualValue() + " CREDS   ");
                  }
              } else {
                  System.out.println("Cannot trade here.");
              }
          } else if (input.equalsIgnoreCase("travel")) {
              System.out.println("Select a square to travel to: ('A-Z','0-9'):");
              System.out.print("X = ");
              char xChar = 'B';
              int y = 2;
              try {
                  BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                  xChar = br.readLine().charAt(0);

              } catch (Exception io) {
                  io.printStackTrace();
              }
              try {
                  System.out.print("Y = ");
                  BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                  y = Integer.parseInt(br.readLine());
              } catch (Exception io) {
                  io.printStackTrace();
              }

              int x = xLetters.indexOf(xChar);

              GridPoint destination = new GridPoint(x,y);
              GridSquare destinationSquare = map.getSquareAt(destination);
              int distance = p1.getLocation().comparePoints(destination);

              boolean destinationIsAPlanet = destinationSquare instanceof Planet;

              String destinationString;
              if (destinationIsAPlanet) {
                  Planet planet = (Planet) destinationSquare;
                  destinationString = planet.name;
              } else {
                  destinationString = "nowhere";
              }

              System.out.println("You travel " + distance + " to reach " + destinationString);
              p1.setLocation(destination);
              if (destinationIsAPlanet) {
                  Planet planet = (Planet) destinationSquare;
                  ArrayList<Goods> availableGoods = planet.market.availableGoods;
                  System.out.println("GOODS:");
                  for (Goods g : availableGoods) {
                      System.out.println(" - " + g.name + " : " + g.getActualValue() + " CREDS   ");
                  }
              }
          } else {
              System.out.println("Command \"" + input + "\" not recognised.");
              System.out.println("Available commands: [scan] [trade] [travel]");
          }

          try { Thread.sleep(2000); } catch (Exception e) { e.printStackTrace(); }
          System.out.println("");
      }

  }

}
