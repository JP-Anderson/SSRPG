package characters.classes;

public class PilotClass extends CrewmemberClass {

    public PilotClass() {
        super("Pilot");
    }

    @Override
    public void buildAbilityTree() {
        /**
        // ROOT ABILITY - Reduced time to flee from combat (3 levels)
        |
        |__// ABILITY 1-1 - Increased dodge chance (2 levels)
        |  |
        |  |__// ABILITY 1-1-1 - Masterful dodging (1 level)
        |
        |__// ABILITY 1-2 - Increased chance to gain combat initiative (2 levels)
           |
           |__// ABILITY 1-2-1 - ? (1 level)

    **/
    }

}
