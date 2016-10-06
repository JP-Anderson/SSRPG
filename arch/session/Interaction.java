package arch.session;

import java.util.ArrayList;

public abstract class Interaction {
	
	public Interaction(Interaction previousInteraction) {
		previous = previousInteraction;
		next = new ArrayList<Interaction>();
	}
	
	public void setNextInteractions(ArrayList<Interaction> nextInteractions) {
		next = nextInteractions;
	}
	
	public ArrayList<Interaction> getNext() {
		return next;
	}
	
	public abstract void interact();
	
	protected void next(int index) {
		if (hasNext()) {
			next.get(index).interact();
		} else {
			System.out.println("END");
		}
	}
	
	public boolean hasNext() {
		return next.size() > 0 ? true: false;
	}
	
	protected Interaction previous;
	protected ArrayList<Interaction> next;
	
}