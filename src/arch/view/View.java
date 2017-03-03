package arch.view;

public abstract class View {

	public final InputHandler inputHandler;
	public final OutputHandler outputHandler;

	protected View(InputHandler input, OutputHandler output) {
		inputHandler = input;
		outputHandler = output;
	}

}
