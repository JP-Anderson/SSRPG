package arch.session;

public abstract class Session {

	public final String name;
	//private View view;

	public Session(String sessionName) {
		name = sessionName;
	}

	protected abstract void run();

}
