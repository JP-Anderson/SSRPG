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

public class Game {

    //todo: move this?
    public final static int FUEL_COST = 180;

    public static void main(String[] args) {

        MapSession mmm = new MapSession();
        mmm.run();
        
    }

}
