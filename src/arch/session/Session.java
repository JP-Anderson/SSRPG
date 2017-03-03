package arch.session;

import arch.view.InputHandler;

public abstract class Session {

	public final String name;
	public final InputHandler view;

	public Session(InputHandler injectedView, String sessionName) {
		name = sessionName;
		view = injectedView;
	}

	public abstract void run();

}
