package arch.session.interaction;

import arch.view.ConsoleIOHandler;
import arch.view.ConsoleInputHandler;
import arch.view.InputHandler;

import java.util.ArrayList;

public class InteractionTest {

	public static void main(String[] args) {
		ConsoleIOHandler inputHandler = new ConsoleIOHandler();
		TextInteraction i1 = new TextInteraction(inputHandler,null, "This is the first interaction.");

		ArrayList<String> i2Options = new ArrayList<String>();
		i2Options.add("Option 0");
		i2Options.add("Option 1");
		TextInteractionDecision i2 = new TextInteractionDecision(inputHandler, i1, "This is the second interaction.", i2Options);

		ArrayList<Interaction> next1 = new ArrayList<Interaction>();
		next1.add(i2);
		//i1.setNextInteractions(next1);


		TextInteraction i3 = new TextInteraction(inputHandler, i2, "This is selected by Option 0.");
		TextInteraction i4 = new TextInteraction(inputHandler, i2, "This is selected by Option 1.");

		ArrayList<Interaction> next2 = new ArrayList<Interaction>();
		next2.add(i3);
		next2.add(i4);

		i2.setNextInteractions(next2);

		i1.run();
	}

}