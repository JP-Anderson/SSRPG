package arch.session;

import util.ConsoleInputHandler;

import java.util.ArrayList;

public class TextInteractionDecision extends TextInteraction {

	protected ArrayList<String> userOptions;

	public TextInteractionDecision(TextInteraction previous, String messageToUser, ArrayList<String> usersOptions) {
		super(previous, messageToUser);
		userOptions = usersOptions;
	}

	@Override
	protected void interaction() {
		System.out.println(messageToUser);
		printUserOptions();
		int option = getIntIndexFromUser(userOptions.size());
		next(option);
	}

	private void printUserOptions() {
		System.out.println("Options:");
		for (String option : userOptions) {
			System.out.println(option);
		}
	}

	private int getIntIndexFromUser(int maxIndex) {
		return ConsoleInputHandler.getIntInRangeFromUser(maxIndex);
	}

}