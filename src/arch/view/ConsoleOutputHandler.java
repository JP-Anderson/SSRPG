package arch.view;

public class ConsoleOutputHandler implements OutputHandler {

	@Override
	public void sendStringToView(String string) {
		System.out.println(string);
	}

}
