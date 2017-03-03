package arch.session.interaction;

import arch.view.InputHandler;

public class TextInteraction extends Interaction {

	protected String messageToUser;

	public static TextInteraction createStartingInteraction(InputHandler injectedView, String message) {
		return new TextInteraction(injectedView, null, message);
	}

	public static TextInteraction createAdditionalInteraction(Interaction previous, String message) {
		return new TextInteraction(previous.view, previous, message);
	}

	protected TextInteraction(InputHandler injectedView, Interaction previous, String message) {
		super(injectedView, previous);
		messageToUser = message;
	}

	protected void interaction() {
		System.out.println(messageToUser);
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