package arch.session;

public class TextInteraction extends Interaction {
	
	protected String messageToUser;
	
	public TextInteraction(TextInteraction previous, String message) {
		super(previous);
		messageToUser = message;
	}
	
	@Override
	public void interact() {
		interaction();
	}
	
	protected void interaction() {
		System.out.println(messageToUser);
		next(0);
	}
	
	public String getMessage() {
		return messageToUser;
	}
	
}