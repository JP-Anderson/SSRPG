package arch.session.interaction;

import arch.view.InputHandler;
import java.util.ArrayList;

public class TextInteractionDecision extends TextInteraction {

	protected ArrayList<String> userOptions;

	public static TextInteraction createStartingInteraction(InputHandler injectedView, String message, ArrayList<String> usersOptions) {
		return new TextInteractionDecision(injectedView, null, message, usersOptions);
	}

	public static TextInteraction createAdditionalInteraction(Interaction previous, String message, ArrayList<String> usersOptions) {
		return new TextInteractionDecision(previous.view, previous, message, usersOptions);
	}

	protected TextInteractionDecision(InputHandler injectedView, Interaction previous, String messageToUser, ArrayList<String> usersOptions) {
		super(injectedView, previous, messageToUser);
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
		return view.getIntInRangeFromUser(maxIndex);
	}

}