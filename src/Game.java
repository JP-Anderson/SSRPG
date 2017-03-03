import arch.session.ShipAndCrewCreationSession;
import arch.view.ConsoleIOHandler;
import arch.view.ConsoleInputHandler;
import ship.*;
import arch.session.MapSession;

public class Game {

	//todo: move this?
	public final static int FUEL_COST = 180;

	public static void main(String[] args) {

		ConsoleIOHandler view = new ConsoleIOHandler();
		ShipAndCrewCreationSession c = new ShipAndCrewCreationSession(view);
		c.run();
		PlayerShip player = c.generateNewShip();
		MapSession mmm = new MapSession(view, player);
		mmm.run();
	}

}
