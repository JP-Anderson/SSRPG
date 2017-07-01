package arch.session;

import arch.view.ConsoleIOHandler;
import characters.skills.abilities.Ability;
import events.events.BanditEvent;
import events.events.CargoCheckEvent;
import map.gridsquares.GridSquare;
import map.gridsquares.Planet;
import ship.*;
import map.*;
import goods.*;
import ship.modules.CargoBayModule;
import ship.modules.MannableShipModule;
import util.collections.tree.AbilitiesConsoleTreePrinter;
import util.dataload.csv.*;
import events.*;
import characters.*;
import util.dataload.csv.loaders.Planets;

import java.util.ArrayList;
import java.util.List;

/*
 * THIS SESSION PACKAGE IS OUTDATED
 * SEE SESSIONS PACKAGE FOR INTERFACES AND CLASSES NEEDED FOR USE IN GDX VERSION OF GAME
 */

public class MapSession extends Session {

	private final static int FUEL_COST = 180;
	private GridMap map;
	private PlayerShip p1;
	private ArrayList<GoodsForSale> goods;

	public MapSession(ConsoleIOHandler injectedView, PlayerShip crewedPlayerShip) {
		super(injectedView, "MapSession");
		p1 = crewedPlayerShip;

		GridPoint start = new GridPoint(3, 6);

		CSV mapCsv = MapCSVReader.readCSV("map");

		map = MapCSVReader.getMap("map");
		//map = GridMap.generateGridMap(11, 7);

		initMapAndGoodsList();
		p1.initialiseMapLocation(map.getSquareAt(start), map);
	}

	private final String xLetters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

	private void initMapAndGoodsList() {
		CSV goodsCSV = CSVReader.readCSV("goods");
		goods = new ArrayList<GoodsForSale>();

		for (int i = 1; i < goodsCSV.rows; i++) {
			ArrayList<String> values = goodsCSV.getZeroIndexedRow(i);
			goods.add(new GoodsForSale(Integer.parseInt(values.get(0)),
					values.get(1),
					Integer.parseInt(values.get(2)) == 0 ? false : true,
					Integer.parseInt(values.get(3))
			));
			//view.outputHandler.sendStringToView("goods:" + values.toString());
		}

		for (Planet planet : Planets.loadPlanetsAsList("planets")) {
			map.populateGridSquare(planet);
		}

	}

	@Override
	public void run() {
		while (true) {
			printCurrentLocation();
			String input = view.inputHandler.getStringFromUser("");
			if (input.equalsIgnoreCase("scan")) {
				p1.scan();
			} else if (input.equalsIgnoreCase("trade")) {
				tradeSequence();
			} else if (input.equalsIgnoreCase("travel")) {
				travelSequence();
			} else if (input.equalsIgnoreCase("ship")) {
				p1.shipStatus();
			} else if (input.equalsIgnoreCase("cargo")) {
				printCargo();
			} else if (input.equalsIgnoreCase("fuel")) {
				fuelSequence();
			} else if (input.equalsIgnoreCase("station")) {
				crewStationsSequence();
			} else if (input.equalsIgnoreCase("crew")) {
				crewSequence();
			} else {
				consoleInformation(input);
			}
			sleep(1);
			view.outputHandler.sendStringToView("");
		}
	}

	private void printCurrentLocation() {
		GridPoint l = p1.getLocationGridPoint();
		GridSquare locationSquare = map.getSquareAt(l);
		if (locationSquare instanceof Planet) {
			Planet locationPlanet = (Planet) locationSquare;
			String locationName = locationPlanet.name;
			view.outputHandler.sendStringToView(p1.name + " is at the planet " + locationName + " (" + xLetters.charAt(l.x) + "," + l.y + ")");
		} else {
			view.outputHandler.sendStringToView(p1.name + " is at empty grid point " + xLetters.charAt(l.x) + "," + l.y);
		}
	}

	private void tradeSequence() {
		GridSquare location = map.getSquareAt(p1.getLocationGridPoint());

		if (location instanceof Planet) {

			view.outputHandler.sendStringToView("Buying or selling?: ");
			char tradeChoice = view.inputHandler.getCharFromUser("");

			Planet planet = (Planet) location;

			if (tradeChoice == 'b' || tradeChoice == 'B') {

				//todo: refactor this into a function at Market
				ArrayList<GoodsForSale> availableGoods = planet.market.availableGoods;
				view.outputHandler.sendStringToView("GOODS:");
				view.outputHandler.sendStringToView(" Enter an index to buy Goods, or return");
				int goodsIndex = 0;
				for (GoodsForSale g : availableGoods) {
					int actualValueDiffFromBaseValue = g.baseValue - g.getPurchaseValue();
					String legal = g.legal ? "" : "[ILLEGAL]";
					String profit = actualValueDiffFromBaseValue < 0 ? "[+]" : "[-]";
					view.outputHandler.sendStringToView(" " + (goodsIndex++) + " - " + g.name + " : " + g.getPurchaseValue() + " CREDS  " + profit + legal);
				}

				int goodsCount = availableGoods.size();
				int choice = view.inputHandler.getIntInRangeFromUser(goodsCount);

				GoodsForSale selectedGoods = availableGoods.get(choice);
				int goodsValue = selectedGoods.getPurchaseValue();
				view.outputHandler.sendStringToView(selectedGoods.name + " costs " + goodsValue + " per unit.");
				view.outputHandler.sendStringToView("How many units would you like to buy?");
				int numberCanAfford = p1.getMoney() / goodsValue;
				view.outputHandler.sendStringToView("--> You can afford " + numberCanAfford);


				int quantity = view.inputHandler.getIntFromUser("");

				CargoBayModule playerCargo = p1.getCargoBay();
				int cargoSize = playerCargo.getFilledCapacity();
				int cargoMaxSize = playerCargo.getMaxCapacity();
				if (quantity <= numberCanAfford && quantity > 0) {
					if (cargoSize + quantity <= cargoMaxSize) {
						int totalCost = goodsValue * quantity;
						playerCargo.addCargo(new PurchasedGoods(selectedGoods, quantity, planet.gridPoint));
						p1.setMoney(p1.getMoney() - totalCost);
						view.outputHandler.sendStringToView("Bought " + quantity + " " + selectedGoods.name + " for " + totalCost + " CREDS.");
					} else {
						view.outputHandler.sendStringToView("Not enough cargo space.");
					}
				} else if (quantity == 0) {
					view.outputHandler.sendStringToView("You buy nothing.");
				} else {
					view.outputHandler.sendStringToView("You cannot afford that amount of " + selectedGoods.name);
				}
			}

			if (tradeChoice == 's' || tradeChoice == 'S') {
				view.outputHandler.sendStringToView("CARGO:");
				CargoBayModule playerCargo = p1.getCargoBay();
				List<PurchasedGoods> cargo = playerCargo.getCargo();

				if (cargo.size() > 0) {
					int goodsIndex = 0;

					for (PurchasedGoods pg : cargo) {
						int localValueOfGoods = planet.market.getValueForSpecificGoods(pg);
						int profit = localValueOfGoods - pg.purchasedValue;
						String legal = pg.legal ? "" : "[ILLEGAL]";
						String profitString = profit == 0 ? "[=]" : profit < 0 ? "[-]" : "[+]";
						view.outputHandler.sendStringToView(" " + (goodsIndex++) + " - " + pg.getQuantity() + "x +" + pg.name + " : " + localValueOfGoods + " CREDS  " + profitString + legal);
					}

					int choice = view.inputHandler.getIntInRangeFromUser(cargo.size());

					PurchasedGoods selectedGoods = cargo.get(choice);
					int quantityOwned = selectedGoods.getQuantity();
					int localValueOfGoods = planet.market.getValueForSpecificGoods(selectedGoods);
					int profit = localValueOfGoods - selectedGoods.purchasedValue;

					view.outputHandler.sendStringToView("How many units would you like to sell?");
					view.outputHandler.sendStringToView("--> You can sell " + quantityOwned);

					if (profit > 0) {
						view.outputHandler.sendStringToView("--> You will make a profit on this sale.");
					} else if (profit < 0) {
						view.outputHandler.sendStringToView("--> You will NOT profit on this sale.");
					} else if (profit == 0) {
						view.outputHandler.sendStringToView("--> You will break even on this sale.");
					}

					int quantitySold = view.inputHandler.getIntFromUser("");

					if (quantitySold <= quantityOwned) {
						int totalMoneyMade = localValueOfGoods * quantitySold;
						p1.setMoney(p1.getMoney() + totalMoneyMade);
						if (quantitySold == quantityOwned) {
							playerCargo.removeCargo(choice);
						} else {
							playerCargo.removeCargo(choice, quantitySold);
						}
						view.outputHandler.sendStringToView("Sold " + quantitySold + " " + selectedGoods.name + " for " + totalMoneyMade + " CREDS.");
					} else view.outputHandler.sendStringToView("You don't have that much " + selectedGoods.name);
				} else view.outputHandler.sendStringToView("No cargo to sell...");
			}
		} else {
			view.outputHandler.sendStringToView("Cannot trade here.");
		}
	}

	private void travelSequence() {
		while (true) {
			view.outputHandler.sendStringToView("Select a square to travel to: ('A-Z','0-9'):");
			int y = 2;
			char xChar = view.inputHandler.getCharFromUser("x");
			y = view.inputHandler.getIntFromUser("y");

			int x = xLetters.indexOf(xChar);


			GridPoint destination = new GridPoint(x, y);
			try {
				GridSquare destinationSquare = map.getSquareAt(destination);
				int distance = p1.getLocationGridPoint().comparePoints(destination);

				boolean destinationIsAPlanet = destinationSquare instanceof Planet;

				String destinationString;
				if (destinationIsAPlanet) {
					Planet planet = (Planet) destinationSquare;
					destinationString = planet.name;
				} else {
					destinationString = "nowhere";
				}

				boolean canTravel = p1.travel(destinationSquare, distance);
				if (canTravel) {
					for (int jumps = 0; jumps < distance; jumps++) {
						double eventRoll = rand.randZeroToOne();
						if (eventRoll >= 0.97) {
							EventRunner.run(view, new BanditEvent(view), p1);
						} else if (eventRoll < 0.3) {
							EventRunner.run(view, new CargoCheckEvent(view), p1);
						}
						view.outputHandler.sendStringToView("You jump to the next sector.");
						view.outputHandler.sendStringToView(distance - jumps + " sectors away.");
						sleep(1);
						printTwoRows();
					}
					view.outputHandler.sendStringToView("You travel " + distance + " to reach " + destinationString);
				} else {
					view.outputHandler.sendStringToView("You do not have enough fuel.");
				}
				break;
			} catch (IndexOutOfBoundsException ioobe) {
				view.outputHandler.sendStringToView("Square out of bounds!");
			}
		}
	}

	private void printCargo() {
		CargoBayModule playerCargo = p1.getCargoBay();
		List<PurchasedGoods> cargo = playerCargo.getCargo();

		if (cargo.size() > 0) {
			int goodsIndex = 0;

			for (PurchasedGoods pg : cargo) {
				String legal = pg.legal ? "" : "[ILLEGAL]";
				int purchasedValue = pg.purchasedValue;
				view.outputHandler.sendStringToView(" " + (goodsIndex++) + " - " + pg.getQuantity() + "x +" + pg.name + " : bought for " + purchasedValue + " CREDS  " + legal);
			}
		} else {
			view.outputHandler.sendStringToView("Cargo hold is empty!");
		}
	}

	private void fuelSequence() {
		GridSquare location = p1.getLocation();

		if (location instanceof Planet) {
			Planet planet = (Planet) location;

			view.outputHandler.sendStringToView("Fuel costs 18 CREDS per unit.");
			view.outputHandler.sendStringToView("How much do you want to buy?");
			view.outputHandler.sendStringToView("--> Fuel status " + p1.getRemainingFuel() + "/" + p1.getFuelCapacity() + ".");

			int availableFuelSpace = p1.getFuelCapacity() - p1.getRemainingFuel();

			int quantityBought = view.inputHandler.getIntFromUser("");
			int cost = quantityBought * FUEL_COST;
			if (quantityBought <= availableFuelSpace) {
				if (cost <= p1.getMoney()) {
					view.outputHandler.sendStringToView("");
					view.outputHandler.sendStringToView("Are you sure you want to buy " + quantityBought + " fuel for " + cost + " CREDS? (Y/N)");
					char decision = view.inputHandler.getCharFromUser("");
					if (decision == 'Y' || decision == 'y') {
						p1.setRemainingFuel(p1.getRemainingFuel() + quantityBought);
						view.outputHandler.sendStringToView("You now have " + p1.getRemainingFuel() + "/" + p1.getFuelCapacity() + " fuel remaining.");
						int newBalance = p1.getMoney() - cost;
						view.outputHandler.sendStringToView("CREDS " + p1.getMoney() + " --> " + newBalance);
						p1.setMoney(newBalance);
					}
				} else view.outputHandler.sendStringToView("You don't have enough money.");
			} else view.outputHandler.sendStringToView("You don't have space for that much fuel!");
		} else view.outputHandler.sendStringToView("There is nowhere to refuel here.");
	}

	private void crewStationsSequence() {
		char decision;
		while (true) {
			view.outputHandler.sendStringToView("Would you like to move a crewmember? (Y/N)");
			decision = view.inputHandler.getCharFromUser("");
			// TODO write a function to take a range of acceptable chars to make these checks shorter
			if (decision == 'Y' || decision == 'y' || decision == 'N' || decision == 'n') break;
		}
		if (decision == 'Y' || decision == 'y') {
			view.outputHandler.sendStringToView("Which crewmember would you like to move?");
			printCrewAndMannedModule();
			ArrayList<Crewmember> crewmembers = p1.crew().getCrew();
			int crewIndex = view.inputHandler.getIntInRangeFromUser(crewmembers.size());
			Crewmember crewmemberToMove = crewmembers.get(crewIndex);
			ArrayList<MannableShipModule> mannableModules = p1.getMannableShipModulesAsList();
			if (mannableModules.size() <= 0) {
				view.outputHandler.sendStringToView("There are no mannable modules");
			} else {
				view.outputHandler.sendStringToView("Which module would you like " + crewmemberToMove.name + " to man?");
				MannableShipModule moduleToMan = view.inputHandler.getUserChoiceFromList(mannableModules, "getName");
				moduleToMan.removeCrewmember();
				moduleToMan.assignCrewmember(crewmemberToMove);
			}
		}
		printCrewAndMannedModule();
	}

	private void printCrewAndMannedModule() {
		ArrayList<Crewmember> playerCrew = p1.crew().getCrew();
		int i = 0;
		for (Crewmember member : playerCrew) {
			MannableShipModule mannedModule = p1.getModuleMannedBy(member);
			String mannedModuleName = mannedModule != null ? "Manning " + mannedModule.getName() : "Not manning a module.";
			view.outputHandler.sendStringToView(i + " - " + member.name + " (" + member.crewmemberClass._className + ") " + mannedModuleName);
			i++;
		}
	}

	private void crewSequence() {
		char decision;
		while (true) {
			view.outputHandler.sendStringToView("Would you like to view a crewmembers abilities? (Y/N)");
			decision = view.inputHandler.getCharFromUser("");
			// TODO write a function to take a range of acceptable chars to make these checks shorter
			if (decision == 'Y' || decision == 'y' || decision == 'N' || decision == 'n') break;
		}
		ArrayList<Crewmember> playerCrew = p1.crew().getCrew();
		int i = 0;
		for (Crewmember member : playerCrew) {
			String upgradeAvailable = member.availableAbilityUpgrades() > 0 ? " (Upgrades available!)" : "";
			String memberInfo = i + " - " + member.name + " LVL " + member.getLevel().getLevel() + " "
					+ member.crewmemberClass._className + ". "
					+ member.getLevel().getRemainingXpForNextLevel()
					+ " xp til level " + (member.getLevel().getLevel()+1)
					+ upgradeAvailable;
			view.outputHandler.sendStringToView(memberInfo);
			i++;
		}
		if (decision == 'Y' || decision == 'y') {
			view.outputHandler.sendStringToView("Which character would you like to view?");
			int choice = view.inputHandler.getIntInRangeFromUser(playerCrew.size());
			Crewmember crewmember = playerCrew.get(choice);
			AbilitiesConsoleTreePrinter treePrinter = new AbilitiesConsoleTreePrinter();
			treePrinter.printTree(crewmember.crewmemberClass.getSkill()._abilities.getTree());
			if (crewmember.availableAbilityUpgrades() > 0) {
				view.outputHandler.sendStringToView("Level up available!");
				view.outputHandler.sendStringToView("Would you like to level up? (Y/N)");
				char decision2;
				while (true) {
					decision2 = view.inputHandler.getCharFromUser("");
					if (decision2 == 'Y' || decision2 == 'y' || decision2 == 'N' || decision2 == 'n') break;
				}
				if (decision2 == 'Y' || decision2 == 'y') {
					ArrayList<Ability> availableUpgrades = crewmember.getUpgradeableAbilities();
					Ability chosenAbility = view.inputHandler.getUserChoiceFromList(availableUpgrades, "name");
					int indexOfAbility = availableUpgrades.indexOf(chosenAbility);
					crewmember.tryToLevelUpAbility(indexOfAbility);
				}
			}
		}

	}

	private void consoleInformation(String input) {
		view.outputHandler.sendStringToView("Command \"" + input + "\" not recognised.");
		view.outputHandler.sendStringToView("Available commands: [scan] [trade] [travel] [ship] [crew] [cargo] [fuel] [station]");
	}

	private void sleep(int seconds) {
		int milliseconds = seconds * 1000;
		try {
			Thread.sleep(milliseconds);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void printTwoRows() {
		view.outputHandler.sendStringToView("");
		view.outputHandler.sendStringToView("");
	}

}
