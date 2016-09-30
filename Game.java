import ship.*;
import map.*;
import goods.*;
import util.csv.*;
import events.*;
import characters.*;
import util.ConsoleInputHandler;

import java.util.ArrayList;
import java.util.List;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Game {

    public static void main(String[] args) {

        EventOutcome ev = new EventOutcome(200,new ArrayList<Crewmember>(), new ArrayList<Goods>());
        System.out.println("event worth " + ev.getMoneyReward());

        GridMap map = GridMap.generateGridMap(11,7);
        System.out.println("Initialising scanner");
        GridPoint start = new GridPoint(2,3);
        Ship p1 = new Ship("Jp",100,3,map,start);
        p1.setMoney(20000);

        CSV planets = CSVReader.readCSV("planets");
        CSV goodsCSV = CSVReader.readCSV("goods");
        ArrayList<GoodsForSale> goods = new ArrayList<GoodsForSale>();

        for (int i = 1; i < goodsCSV.rows; i++) {
            ArrayList<String> values = goodsCSV.getZeroIndexedRow(i);
            goods.add(new GoodsForSale(Integer.parseInt(values.get(0)),
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

            String input = ConsoleInputHandler.getStringFromUser("");

            if (input.equalsIgnoreCase("scan")) {
                p1.scan();
            } else if (input.equalsIgnoreCase("trade")) {
                GridSquare location = map.getSquareAt(p1.getLocation());

                if (location instanceof Planet) {

                    System.out.print("Buying or selling?: ");
                    char tradeChoice = ConsoleInputHandler.getCharFromUser("");

                    Planet planet = (Planet) location;

                    if (tradeChoice == 'b' || tradeChoice == 'B') {

                        //todo: refactor this into a function at Market
                        ArrayList<GoodsForSale> availableGoods = planet.market.availableGoods;
                        System.out.println("GOODS:");
                        System.out.println(" Enter an index to buy Goods, or return");
                        int goodsIndex = 0;
                        for (GoodsForSale g : availableGoods) {
                            int actualValueDiffFromBaseValue = g.baseValue - g.getPurchaseValue();
                            String legal = g.legal ? "" : "[ILLEGAL]";
                            String profit = actualValueDiffFromBaseValue < 0 ? "[+]" : "[-]";
                            System.out.println(" "+(goodsIndex++)+" - " + g.name + " : " + g.getPurchaseValue() + " CREDS  "+ profit + legal);
                        }

                        int choice = ConsoleInputHandler.getIntFromUser("");

                        GoodsForSale selectedGoods = availableGoods.get(choice);
                        int goodsValue = selectedGoods.getPurchaseValue();
                        System.out.println(selectedGoods.name + " costs " + goodsValue + " per unit.");
                        System.out.println("How many units would you like to buy?");
                        int numberCanAfford = p1.getMoney() / goodsValue;
                        System.out.println("--> You can afford " + numberCanAfford);
                        System.out.print(">> ");


                        int quantity = ConsoleInputHandler.getIntFromUser("");

                        CargoBay playerCargo = p1.getCargoBay();
                        int cargoSize = playerCargo.getFilledCapacity();
                        int cargoMaxSize = playerCargo.getMaxCapacity();
                        if (quantity <= numberCanAfford) {
                            if (cargoSize + quantity <= cargoMaxSize) {
                                int totalCost = goodsValue*quantity;
                                playerCargo.addCargo(new PurchasedGoods(selectedGoods, quantity, planet.gridPoint));
                                p1.setMoney(p1.getMoney() - totalCost);
                                System.out.println("Bought " + quantity + " " + selectedGoods.name + " for " +  totalCost + " CREDS.");
                            } else {
                                System.out.println("Not enough cargo space.");
                            }
                        }
                    }

                    if (tradeChoice == 's' || tradeChoice == 'S') {
                        System.out.println("CARGO:");
                        CargoBay playerCargo = p1.getCargoBay();
                        List<PurchasedGoods> cargo = playerCargo.getCargo();
                        int goodsIndex = 0;

                        for (PurchasedGoods pg : cargo) {
                            int localValueOfGoods = planet.market.getValueForSpecificGoods(pg);
                            int profit = localValueOfGoods - pg.purchasedValue;
                            String legal = pg.legal ? "" : "[ILLEGAL]";
                            String profitString = profit == 0 ? "[=]" : profit < 0 ? "[-]" : "[+]";
                            System.out.println(" "+(goodsIndex++)+" - " + pg.getQuantity() + "x +" + pg.name + " : " + localValueOfGoods + " CREDS  "+ profitString + legal);
                        }

                        System.out.print(">> ");
                        int choice = ConsoleInputHandler.getIntFromUser("");

                        PurchasedGoods selectedGoods = cargo.get(choice);
                        int quantityOwned = selectedGoods.getQuantity();
                        int localValueOfGoods = planet.market.getValueForSpecificGoods(selectedGoods);
                        int profit = localValueOfGoods - selectedGoods.purchasedValue;

                        System.out.println("How many units would you like to sell?");
                        System.out.println("--> You can sell " + quantityOwned);

                        if (profit > 0) {
                            System.out.println("--> You will make a profit on this sale.");
                        } else if (profit <= 0) {
                            System.out.println("--> You will NOT profit on this sale.");
                        }

                        int quantitySold = ConsoleInputHandler.getIntFromUser("");

                        if (quantitySold <= quantityOwned) {
                            int totalMoneyMade = localValueOfGoods*quantitySold;
                            p1.setMoney(p1.getMoney() + totalMoneyMade);
                            if (quantitySold == quantityOwned) {
                                playerCargo.removeCargo(choice);
                            } else {
                                playerCargo.removeCargo(choice, quantitySold);
                            }
                            System.out.println("Sold " + quantitySold + " " + selectedGoods.name + " for " + totalMoneyMade + " CREDS.");
                        }
                    }
                } else {
                    System.out.println("Cannot trade here.");
                }
            } else if (input.equalsIgnoreCase("travel")) {
                while (true) {
                    System.out.println("Select a square to travel to: ('A-Z','0-9'):");
                    int y = 2;
                    char xChar = ConsoleInputHandler.getCharFromUser("x");
                    System.out.println(xChar);
                    y = ConsoleInputHandler.getIntFromUser("y");

                    int x = xLetters.indexOf(xChar);


                    GridPoint destination = new GridPoint(x,y);
                    try {
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
                        break;
                    } catch (IndexOutOfBoundsException ioobe) {
                        System.out.println("Square out of bounds!");
                    }
                }

            } else if (input.equalsIgnoreCase("ship")) {
                p1.shipStatus();
            } else {
                System.out.println("Command \"" + input + "\" not recognised.");
                System.out.println("Available commands: [scan] [trade] [travel] [ship]");
                System.out.println("Un-installed tools: [cargo] [crew]");
            }
            try { Thread.sleep(1000); } catch (Exception e) { e.printStackTrace(); }
            System.out.println("");
        }
    }

}
