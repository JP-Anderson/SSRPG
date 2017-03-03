package arch.session;

import arch.view.ConsoleIOHandler;
import arch.view.InputHandler;

public abstract class Session {

	public final String name;
	public final ConsoleIOHandler view;

	public Session(ConsoleIOHandler injectedView, String sessionName) {
		name = sessionName;
		view = injectedView;
	}

	public abstract void run();

}
