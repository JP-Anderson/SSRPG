package arch.session;

import characters.Crewmember;
import characters.Skills;
import characters.classes.*;
import ship.Ship;
import util.ConsoleInputHandler;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ShipAndCrewCreationSession extends Session {

    ArrayList<Crewmember> crew = null;
    private ArrayList<CrewmemberClass> availableClasses;
    private String newName;

    public ShipAndCrewCreationSession() {
        super("ShipAndCrewCreationSession");
        availableClasses = new ArrayList<>();
        availableClasses.add(new PilotClass());
        availableClasses.add(new ScoundrelClass());
        availableClasses.add(new WeaponsExpertClass());
    }

    @Override
    public void run() {
        newName = ConsoleInputHandler.getStringFromUser("What would you like to call your ship?");
        crew = new ArrayList<>();
        int crewCount = 1;
        final int numberOfStartingCrewmembers = 2;
        for (int i = 0; i < numberOfStartingCrewmembers; i++) {
            System.out.println("You have " + (numberOfStartingCrewmembers-i) + " crewmembers left to pick");
            System.out.println("Available crew:");
            printAvailableClasses();
            System.out.println("Which class do you pick for crewmember #"+crewCount+"?");
            int chosenIndex = ConsoleInputHandler.getIntInRangeFromUser(availableClasses.size());
            CrewmemberClass chosenClass = availableClasses.get(chosenIndex);
            availableClasses.remove(chosenIndex);
            String chosenName = ConsoleInputHandler.getStringFromUser("What will you call this " + chosenClass.className+"?");
            crew.add(new Crewmember(chosenName, new Skills(), chosenClass));
            crewCount++;
            System.out.println();
        }
    }

    public Ship generateNewShip() {
        Ship p1 = new Ship(newName,100,3);
        p1.initialiseCrew(crew);
        return p1;
    }

    private void printAvailableClasses() {
        for (int i = 0; i < availableClasses.size(); i++) {
            System.out.println(i + " : " + availableClasses.get(i).className);
        }
    }
}