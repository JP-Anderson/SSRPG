package arch.view;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.ArrayList;

public interface InputHandler {

	static int getIntFromUser() {
		throw new NotImplementedException();
	};

	static int getIntInRangeFromUser(int options) {
		throw new NotImplementedException();
	};

	static char getCharFromUser() {
		throw new NotImplementedException();
	};

	static String getNonEmptyStringFromUser() {
		throw new NotImplementedException();
	}

	static String getStringFromUser() {
		throw new NotImplementedException();
	};

	static <O> O getUserChoiceFromList(ArrayList<O> objects, String objectAttributeToPrint) {
		throw new NotImplementedException();
	}

}
