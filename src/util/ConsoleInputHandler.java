package util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

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
		return readFromBufferedReader(promptMessage);
	}

	/*
		Get non empty String from user.
	 */
	public static String getNonEmptyStringFromUser(String promptMessage) {
		while (true) {
			String input = readFromBufferedReader(promptMessage);
			if (input.equals("")) {
				System.out.println("Please enter a value.");
				continue;
			}
			return input;
		}
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
			} else return specifiedChar;
		}
	}

	/*
		Only accepts an integer from the user.
	*/
	public static Integer getIntFromUser(String promptMessage) {
		while (true) {
			String input = readFromBufferedReader(promptMessage);
			try {
				return Integer.parseInt(input);
			} catch (NumberFormatException e) {
				System.out.println("Enter a number");
			}
		}
	}

	/*
		Takes an ArrayList of generic objects, and a String attribute name. If the objects in the list
		have the provided attribute name, an indexed list will be printed, using the specified attribute value.
		Then takes an int from the user and returns the item at that index.
	*/
	public static <O> O getUserChoiceFromList(ArrayList<O> objects, String objectAttributeToPrint) {
		printOptionsAsIndexedList(objects, objectAttributeToPrint);
		int selection = getIntInRangeFromUser(objects.size());
		return objects.get(selection);
	}

	private static <O> void printOptionsAsIndexedList(ArrayList<O> objects, String objectAttributeToPrint) {
		MemberType memberType = checkObjectContainsValue(objects.get(0), objectAttributeToPrint);
		if (memberType == MemberType.NULL) return;
		ArrayList<String> stringsToPrint = new ArrayList<>();
		if (memberType == MemberType.FIELD) {
			stringsToPrint = getFieldListFromObjects(objects, objectAttributeToPrint);
		} else if (memberType == MemberType.METHOD) {
			stringsToPrint = getMethodResultsFromObjects(objects, objectAttributeToPrint);
		}
		for (String s : stringsToPrint) System.out.println(s);
	}

	private enum MemberType {FIELD, METHOD, NULL}

	private static <O> MemberType checkObjectContainsValue(O object, String objectAttributeToPrint) {
		Field f = getDesiredField(object, objectAttributeToPrint);
		if (f != null) return MemberType.FIELD;
		Method m = getDesiredMethod(object, objectAttributeToPrint);
		if (m != null) return MemberType.METHOD;
		return MemberType.NULL;
	}

	private static <O> Field getDesiredField(O object, String objectAttributeToPrint) {
		Field[] fields = object.getClass().getFields();
		for (Field f : fields) {
			if (f.getName().equals(objectAttributeToPrint)) return f;
		}
		return null;
	}

	private static <O> Method getDesiredMethod(O object, String objectMethodToUse) {
		Method[] methods = object.getClass().getMethods();
		for (Method m : methods) {
			if (m.getName().equals(objectMethodToUse) && m.getReturnType().equals(String.class)) {
				return m;
			}
		}
		return null;
	}

	private static <O> ArrayList<String> getFieldListFromObjects(ArrayList<O> objects, String objectAttributeToPrint) {
		ArrayList<String> stringsToPrint = new ArrayList<>();
		for (int i = 0; i < objects.size(); i++) {
			O o = objects.get(i);
			Field field = getDesiredField(o, objectAttributeToPrint);
			if (field != null) {
				try {
					stringsToPrint.add(i + " - " + field.get(o));
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
			} else return null;
		}
		return stringsToPrint;
	}

	private static <O> ArrayList<String> getMethodResultsFromObjects(ArrayList<O> objects, String objectAttributeToPrint) {
		ArrayList<String> stringsToPrint = new ArrayList<>();
		for (int i = 0; i < objects.size(); i++) {
			O o = objects.get(i);
			Method method = getDesiredMethod(o, objectAttributeToPrint);
			if (method != null) {
				try {
					stringsToPrint.add(i + " - " + method.invoke(o));
				} catch (InvocationTargetException | IllegalAccessException e) {
					e.printStackTrace();
				}
			} else return null;
		}
		return stringsToPrint;
	}

	public static int getIntInRangeFromUser(int options) {
		while (true) {
			int input = getIntFromUser("");
			if (input >= 0 && input < options) return input;
			else if (input == 0) return input;
			else System.out.println("Number out of range!");
		}
	}

}
