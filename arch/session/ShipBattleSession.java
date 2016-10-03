package arch.session;

public class ShipBattleSession extends Session {

    public ShipBattleSession() {
        super("ShipBattleSession");
    }

    @Override
    public void run() {
        System.out.println("Battle starting.");
    }
}
