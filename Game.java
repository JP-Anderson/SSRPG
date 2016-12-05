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

        MapSession mmm = new MapSession(c.generateNewShip());
        mmm.run();

        //Ship s1 = new Ship("1", 100, 3, GridMap.generateGridMap(11,7), new GridPoint(0,1));
        //EnemyShip s2 = new EnemyShip("2");

        //ShipBattleSession sbs = new ShipBattleSession(s1,s2);
        //sbs.run();

    }

}
