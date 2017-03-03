package arch.session.interaction;

import arch.session.Session;
import arch.view.ConsoleIOHandler;
import arch.view.InputHandler;

import java.util.ArrayList;

public abstract class Interaction extends Session {

	protected Interaction previous;
	protected ArrayList<Interaction> nextInteractions;

	protected Interaction(ConsoleIOHandler injectedView, Interaction previousInteraction) {
		super(injectedView, "Interaction");
		if (previousInteraction != null) {
			previousInteraction.setNextInteractions(this);
			previous = previousInteraction;
		}
		nextInteractions = new ArrayList<Interaction>();
	}

	public void setNextInteractions(Interaction nextInteraction) {
		nextInteractions.clear();
		nextInteractions.add(nextInteraction);
	}

	public void setNextInteractions(ArrayList<Interaction> newInteractions) {
		nextInteractions = newInteractions;
	}

	public ArrayList<Interaction> getNextInteractions() {
		return nextInteractions;
	}

	protected void next(int index) {
		if (hasNext()) {
			nextInteractions.get(index).run();
		}
	}

	public boolean hasNext() {
		return nextInteractions.size() > 0 ? true : false;
	}

}