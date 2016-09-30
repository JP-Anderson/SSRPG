package util;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class ConsoleInputHandler {

    public static String getStringFromUser(String promptMessage) {
        String input = readFromBufferedReader(promptMessage);
        return input;
    }

    public static char getCharFromUser(String promptMessage) {
        while (true) {
            String input = readFromBufferedReader(promptMessage);
            System.out.println(input);
            String upperInput = input.toUpperCase();
            System.out.println(upperInput);
            char specifiedChar = upperInput.charAt(0);
            String allowedChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
            if (allowedChars.indexOf(specifiedChar) < 0) {
                System.out.println("Enter a letter");
                continue;
            }  else return specifiedChar;
        }
    }

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

    private static void prompt(String promptMessage) {
        System.out.print(promptMessage + ">> ");
    }

}
