package arch.view;

public class ConsoleIOHandler extends View {

	public ConsoleIOHandler() {
		super(new ConsoleInputHandler(), new ConsoleOutputHandler());
	}

}
