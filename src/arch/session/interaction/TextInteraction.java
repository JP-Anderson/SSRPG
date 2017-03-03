package arch.session.interaction;

import arch.view.ConsoleIOHandler;
import arch.view.InputHandler;

public class TextInteraction extends Interaction {

	protected String messageToUser;

	public static TextInteraction createStartingInteraction(ConsoleIOHandler injectedView, String message) {
		return new TextInteraction(injectedView, null, message);
	}

	public static TextInteraction createAdditionalInteraction(Interaction previous, String message) {
		return new TextInteraction(previous.view, previous, message);
	}

	protected TextInteraction(ConsoleIOHandler injectedView, Interaction previous, String message) {
		super(injectedView, previous);
		messageToUser = message;
	}

	protected void interaction() {
		view.outputHandler.sendStringToView(messageToUser);
		next(0);
	}

	public String getMessage() {
		return messageToUser;
	}

	@Override
	public void run() {
		interaction();
	}
}