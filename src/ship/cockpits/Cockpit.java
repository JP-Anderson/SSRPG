package ship.cockpits;

public class Cockpit {

	public final String name;
	public final int powerRequirement;
	public final int baseCost;
	public final double baseDodgeChance;

	public Cockpit(String name, int powerRequirement, int baseCost, double baseDodgeChance) {
		this.name = name;
		this.powerRequirement = powerRequirement;
		this.baseCost = baseCost;
		this.baseDodgeChance = baseDodgeChance;
	}

}
