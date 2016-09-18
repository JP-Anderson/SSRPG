import ship.*;
import map.*;
import util.csv.*;
import goods.*;

import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Game {

    public static void main(String[] args) {

        GridMap map = GridMap.generateGridMap(11,7);
        System.out.println("Initialising scanner");

        GridPoint start = new GridPoint(2,3);
        Ship p1 = new Ship("Jp",100,3,map,start);
        p1.setMoney(20000);

        CSV planets = CSVReader.readCSV("planets");
        CSV goodsCSV = CSVReader.readCSV("goods");
        ArrayList<Goods> goods = new ArrayList<Goods>();

        for (int i = 1; i < goodsCSV.rows; i++) {
            ArrayList<String> values = goodsCSV.getZeroIndexedRow(i);
            goods.add(new Goods(Integer.parseInt(values.get(0)),
            values.get(1),
            Integer.parseInt(values.get(2)) == 0 ? false : true,
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

                    System.out.print("Buying or selling?: ");
                    char tradeChoice = 'a';
                    try {
                        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                        tradeChoice = br.readLine().charAt(0);

                    } catch (Exception io) {
                        io.printStackTrace();
                    }

                    if (tradeChoice == 'b' || tradeChoice == 'B') {

                        Planet planet = (Planet) location;
                        ArrayList<Goods> availableGoods = planet.market.availableGoods;
                        System.out.println("GOODS:");
                        System.out.println(" Enter an index to buy Goods, or return");
                        int goodsIndex = 0;
                        for (Goods g : availableGoods) {
                            int actualValueDiffFromBaseValue = g.baseValue - g.getActualValue();
                            String legal = g.legal ? "" : "[ILLEGAL]";
                            String profit = actualValueDiffFromBaseValue < 0 ? "[+]" : "[-]";
                            System.out.println(" "+(goodsIndex++)+" - " + g.name + " : " + g.getActualValue() + " CREDS  "+ profit + legal);
                        }

                        System.out.print(">> ");
                        int choice = 0;
                        try {
                            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                            choice = Integer.parseInt(br.readLine());
                        } catch (Exception io) {
                            io.printStackTrace();
                        }

                        Goods selectedGoods = availableGoods.get(choice);
                        int goodsValue = selectedGoods.getActualValue();
                        System.out.println(selectedGoods.name + " costs " + goodsValue + " per unit.");
                        System.out.println("How many units would you like to buy?");
                        int numberCanAfford = p1.getMoney() / goodsValue;
                        System.out.println("--> You can afford " + numberCanAfford);
                        System.out.print(">> ");

                        boolean validIndex = false;

                        int quantity = 0;
                        try {
                            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                            quantity = Integer.parseInt(br.readLine());
                            validIndex = true;
                        } catch (Exception io) {
                            System.out.println("Index not recognised, returning to menu.");
                        }

                        if (validIndex) {
                            CargoBay playerCargo = p1.getCargo();
                            int cargoSize = playerCargo.getFilledCapacity();
                            int cargoMaxSize = playerCargo.getMaxCapacity();
                            if (quantity <= numberCanAfford) {
                                if (cargoSize + quantity <= cargoMaxSize) {
                                    for (int i = 0; i < quantity; i++) {
                                        playerCargo.addCargo(selectedGoods);
                                        p1.setMoney(p1.getMoney() - goodsValue);
                                    }
                                } else {
                                    System.out.println("Not enough cargo space.");
                                }
                            }

                        }
                    }

                    if (tradeChoice == 's' || tradeChoice == 'S') {
                        System.out.println("Selling");
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


                boolean canTravel = p1.travel(destination, distance);
                if (canTravel) {
                    System.out.println("You travel " + distance + " to reach " + destinationString);
                }
                else {
                    System.out.println("You do not have enough fuel.");
                }

            } else if (input.equalsIgnoreCase("ship")) {
                p1.shipStatus();
            } else {
                System.out.println("Command \"" + input + "\" not recognised.");
                System.out.println("Available commands: [scan] [trade] [travel] [ship]");
                System.out.println("Un-installed tools: [cargo] [crew]");
            }

            try { Thread.sleep(2000); } catch (Exception e) { e.printStackTrace(); }
            System.out.println("");
        }

    }

}
