package arch.session;

import characters.Crewmember;
import characters.Skills;
import characters.classes.*;
import ship.PlayerShip;
import util.ConsoleInputHandler;

import java.util.ArrayList;

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
        newName = ConsoleInputHandler.getNonEmptyStringFromUser("What would you like to call your ship?");
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
            String chosenName = ConsoleInputHandler.getNonEmptyStringFromUser("What will you call this " + chosenClass._className +"?");
            crew.add(new Crewmember(chosenName, new Skills(), chosenClass));
            crewCount++;
            System.out.println();
        }
    }

    public PlayerShip generateNewShip() {
        PlayerShip p1 = new PlayerShip(newName,100,3);
        p1.initialiseCrew(crew);
        return p1;
    }

    private void printAvailableClasses() {
        for (int i = 0; i < availableClasses.size(); i++) {
            System.out.println(i + " : " + availableClasses.get(i)._className);
        }
    }
}
