package arch.session;

import arch.view.ConsoleIOHandler;
import arch.view.InputHandler;
import util.rng.RNG;

/*
 * THIS SESSION PACKAGE IS OUTDATED
 * SEE SESSIONS PACKAGE FOR INTERFACES AND CLASSES NEEDED FOR USE IN GDX VERSION OF GAME
 */

@Deprecated
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
