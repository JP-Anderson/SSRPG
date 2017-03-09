package characters;

import characters.classes.ScoundrelClass;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import util.dataload.csv.CSV;
import util.dataload.csv.CSVReader;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class CrewmemberTest {

	static ArrayList<Integer> xpForLevels;

	@BeforeAll
	public static void loadXpForLevels() {
		CSV levelsCSV = CSVReader.readCSV("levels");
		xpForLevels = (ArrayList<Integer>) levelsCSV.getZeroIndexedRowAsInts(0);
	}

	@Test
	public void availableAbilityUpgradesWorksForSingleLevelUp() {
		Crewmember han = getCrewmember();
		han.gainExperience(xpForLevels.get(0));
		assertEquals(1, han.availableAbilityUpgrades());
	}

	@Test
	public void availableAbilityUpgradesReturnsZeroAfterUpgrade() {
		Crewmember han = getCrewmember();
		han.gainExperience(xpForLevels.get(0));
		han.tryToLevelUpAbility(0);
		assertEquals(0, han.availableAbilityUpgrades());
	}

	@Test
	public void availableAbilityUpgradesReturnsTwoAfterTwoLevelUps() {
		Crewmember han = getCrewmember();
		han.gainExperience(xpForLevels.get(0));
		han.gainExperience(xpForLevels.get(1));

		// We expect one ability to actually be upgradeable, still just the root ability
		assertEquals(1, han.getUpgradeableAbilities().size());
		// But we expect the character to have two points to invest after two level ups
		assertEquals(2, han.availableAbilityUpgrades());

		han.tryToLevelUpAbility(0);

		// We now expect to upgrade 3 abilities: the root ability to level 2, and have unlocked
		// the next 2 abilities after the root ability
		assertEquals(3, han.getUpgradeableAbilities().size());
		// But we only have one more point to invest
		assertEquals(1, han.availableAbilityUpgrades());

		han.tryToLevelUpAbility(0);

		assertEquals(3, han.getUpgradeableAbilities().size());
		assertEquals(0, han.availableAbilityUpgrades());
	}

	private Crewmember getCrewmember() {
		return new Crewmember("Han", new ScoundrelClass(), 1);
	}

}