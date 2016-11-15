package arch.session;

import ship.AbstractShip;

public class ShipBattleSession extends Session {

    private final AbstractShip ship1;
    private final AbstractShip ship2;

    private ShipBattleView view;

    public ShipBattleSession(AbstractShip newShip1, AbstractShip newShip2) {
        super("ShipBattleSession");
        ship1 = newShip1;
        ship2 = newShip2;
    }

    @Override
    public void run() {
        //TODO need to replace System calls with output handler class
        System.out.println("Battle starting.");

        // determine which ship runs first
        // -- engine modules? weapon modules? pilot/gunner skill?

        // or will it be real time battles, similar to FTL?

        //
    }
}
