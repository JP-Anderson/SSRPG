package characters;

public class Skills {

    private int pilot;
    private int navigation;
    private int weapons;
    private int engineering;
    private int trading;
    private int shields;

    public int getPilot() {
        return pilot;
    }

    public void levelUpPilot() {
        pilot++;
    }

    public int getNavigation() {
        return navigation;
    }

    public void levelUpNavigation() {
        navigation++;
    }

    public int getWeapons() {
        return weapons;
    }

    public void levelUpWeapons() {
        weapons++;
    }

    public int getEngineering() {
        return engineering;
    }

    public void levelUpEngineering() {
        engineering++;
    }

    public int getTrading() {
        return trading;
    }

    public void levelUpTrading() {
        trading++;
    }

    public int getShields() {
        return shields;
    }

    public void levelUpShields() {
        shields++;
    }

}
