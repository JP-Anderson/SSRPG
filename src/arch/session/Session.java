package arch.session;

import arch.view.ConsoleIOHandler;
import arch.view.InputHandler;
import util.rng.RNG;

public abstract class Session {

	public final String name;
	public final ConsoleIOHandler view;
	protected RNG rand;

	public Session(ConsoleIOHandler injectedView, String sessionName) {
		name = sessionName;
		view = injectedView;
		rand = new RNG();
	}

	public abstract void run();

}
