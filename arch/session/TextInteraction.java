package arch.session;

public class TextInteraction extends Interaction {
	
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
	
	protected void next(int index) {
		if (hasNext()) {
			next.get(index).interact();
		} else {
			System.out.println("END");
		}
	}
	
	protected String messageToUser;
	
	public String getMessage() {
		return messageToUser;
	}
	
}