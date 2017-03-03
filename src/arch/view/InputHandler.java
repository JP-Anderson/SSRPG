package arch.view;

import java.util.ArrayList;

public interface InputHandler {

	int getIntFromUser(String prompt);

	int getIntInRangeFromUser(int options);

	char getCharFromUser(String prompt);

	String getNonEmptyStringFromUser(String prompt);

	String getStringFromUser(String prompt);

	<O> O getUserChoiceFromList(ArrayList<O> objects, String objectAttributeToPrint);

}
