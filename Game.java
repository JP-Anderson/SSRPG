import ship.*;
import map.*;
import goods.*;
import util.csv.*;
import util.*;
import events.*;
import characters.*;
import java.util.ArrayList;
import java.util.List;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import arch.session.MapSession;
import arch.session.ShipBattleSession;

public class Game {

    //todo: move this?
    public final static int FUEL_COST = 180;

    public static void main(String[] args) {

        Ship s1 = new Ship("1", 100, 3, GridMap.generateGridMap(11,7), new GridPoint(0,1));
        EnemyShip s2 = new EnemyShip("2");

        ShipBattleSession sbs = new ShipBattleSession(s1,s2);
        sbs.run();

        MapSession mmm = new MapSession();
        //mmm.run();
    }

}
