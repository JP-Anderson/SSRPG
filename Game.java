import arch.session.ShipAndCrewCreationSession;
import ship.*;
import map.*;
import arch.session.MapSession;
import arch.session.ShipBattleSession;

public class Game {

    //todo: move this?
    public final static int FUEL_COST = 180;

    public static void main(String[] args) {

        ShipAndCrewCreationSession c = new ShipAndCrewCreationSession();
        c.run();
        Ship player = c.generateNewShip();
        MapSession mmm = new MapSession(player);
        mmm.run();
    }

}
