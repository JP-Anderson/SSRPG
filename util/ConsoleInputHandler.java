package util;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/*
    This class can be used to take user input through the consoles. It has a
    number of functions for validating user input of various types.
 */
public class ConsoleInputHandler {

    /*
        Internal function which passes user input into the public functions
    */
    private static String readFromBufferedReader(String promptMessage) {
        prompt(promptMessage);
        String line = "";
        try {
            BufferedReader commandReader = new BufferedReader(new InputStreamReader(System.in));
            line = commandReader.readLine();
        } catch (Exception io) {
            io.printStackTrace();
        }
        return line;
    }

    /*
        Prints a provided String prompt to the user.
        This function is called before taking any input.
    */
    private static void prompt(String promptMessage) {
        System.out.print(promptMessage + ">> ");
    }

    /*
        Returns the user input as a String.
     */
    public static String getStringFromUser(String promptMessage) {
        String input = readFromBufferedReader(promptMessage);
        return input;
    }

    /*
        Only accepts a single lower or uppercase letter from the user.
    */
    public static char getCharFromUser(String promptMessage) {
        while (true) {
            String input = readFromBufferedReader(promptMessage);
            if (input.equals("")) {
                System.out.println("Enter a letter.");
                continue;
            }
            String upperInput = input.toUpperCase();
            char specifiedChar = upperInput.charAt(0);
            String allowedChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
            if (allowedChars.indexOf(specifiedChar) < 0) {
                System.out.println("Enter a letter");
                continue;
            }  else return specifiedChar;
        }
    }

    /*
        Only accepts an integer from the user.
    */
    public static Integer getIntFromUser(String promptMessage) {
        while (true) {
            String input = readFromBufferedReader(promptMessage);
            try {
                Integer inputInteger = Integer.parseInt(input);
                return inputInteger;
            } catch (NumberFormatException e) {
                System.out.println("Enter a number");
                continue;
            }
        }
    }

    /*
        Only accepts an int in the range of 0 to the specified 'options' argument.
    */
    public static int getIntInRangeFromUser(int options) {
        while (true) {
            int input = getIntFromUser("");
            if (input >= 0 && input < options) return input;
            else if (input == 0) return input;
            else System.out.println("Number out of range!");
        }
    }

}
