package arch.session.interaction;

import arch.view.ConsoleIOHandler;
import arch.view.InputHandler;
import java.util.ArrayList;

public class TextInteractionDecision extends TextInteraction {

	protected ArrayList<String> userOptions;

	public static TextInteractionDecision createStartingInteraction(ConsoleIOHandler injectedView, String message, ArrayList<String> usersOptions) {
		return new TextInteractionDecision(injectedView, null, message, usersOptions);
	}

	public static TextInteractionDecision createAdditionalInteraction(Interaction previous, String message, ArrayList<String> usersOptions) {
		return new TextInteractionDecision(previous.view, previous, message, usersOptions);
	}

	protected TextInteractionDecision(ConsoleIOHandler injectedView, Interaction previous, String messageToUser, ArrayList<String> usersOptions) {
		super(injectedView, previous, messageToUser);
		userOptions = usersOptions;
	}

	@Override
	protected void interaction() {
		view.outputHandler.sendStringToView(messageToUser);
		printUserOptions();
		int option = getIntIndexFromUser(userOptions.size());
		next(option);
	}

	private void printUserOptions() {
		view.outputHandler.sendStringToView("Options:");
		for (String option : userOptions) {
			view.outputHandler.sendStringToView(option);
		}
	}

	private int getIntIndexFromUser(int maxIndex) {
		return view.inputHandler.getIntInRangeFromUser(maxIndex);
	}

}