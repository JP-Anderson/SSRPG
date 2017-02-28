package characters;

import characters.classes.PilotClass;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import util.RNG;
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
	public void ensureLevelUpWorksWithExactXp() {
		Crewmember crewmember = newCrewmember();
		assertEquals(1, crewmember.getLevel());
		assertEquals(0, crewmember.getXp());

		crewmember.gainExperience(100);

		assertEquals(2, crewmember.getLevel());
		assertEquals(0, crewmember.getXp());
	}

	@Test
	public void ensureFinalLevelCharacterCannotGainExperience() {
		Crewmember crewmember = newCrewmember();

		for (int i = 0; i < xpForLevels.size(); i++) {
			int xpForNextLevel = xpForLevels.get(i);
			crewmember.gainExperience(xpForNextLevel);
			assertEquals(i+2, crewmember.getLevel());
		}

		crewmember.gainExperience(1000000);

		int maxLevel = xpForLevels.size() + 1;
		assertEquals(maxLevel, crewmember.getLevel());
		assertEquals(0, crewmember.getXp());
		assertEquals(0, crewmember.getRemainingXpForNextLevel());
	}
	
	@Test
	public void ensureCorrectAmountOfXpCarriedOverWithRemainder() {
		Crewmember crewmember = newCrewmember();

		int xpRequiredForNextLevel = xpForLevels.get(crewmember.getLevel() - 1);
		int moreThanOneXpRequired = xpRequiredForNextLevel + 1;
		crewmember.gainExperience(moreThanOneXpRequired);

		assertEquals(2, crewmember.getLevel());
		assertEquals(1, crewmember.getXp());

		xpRequiredForNextLevel = xpForLevels.get(crewmember.getLevel() - 1);
		assertEquals(xpRequiredForNextLevel-1, crewmember.getRemainingXpForNextLevel());

		xpRequiredForNextLevel = crewmember.getRemainingXpForNextLevel();
		int gainedExperience = xpRequiredForNextLevel + (xpRequiredForNextLevel/2);
		int remainder = gainedExperience - xpRequiredForNextLevel;
		crewmember.gainExperience(gainedExperience);

		assertEquals(3, crewmember.getLevel());
		assertEquals(remainder, crewmember.getXp());
	}

	@Test
	public void ensureCrewmemberLevelsUpMultipleTimesOnGainingEnoughXP() {
		Crewmember crewmember = newCrewmember();

		int level = crewmember.getLevel();
		int numberOfLevelsToIncrease = RNG.randInt(1,2);
		int targetLevel = level + numberOfLevelsToIncrease;
		int requiredXpToAdvance = getRequiredXpFromLevelXToLevelY(level, targetLevel);
		crewmember.gainExperience(requiredXpToAdvance);
		level = crewmember.getLevel();

		assertEquals(targetLevel, level);

		numberOfLevelsToIncrease = RNG.randInt(1,3);
		targetLevel = level + numberOfLevelsToIncrease;
		requiredXpToAdvance = getRequiredXpFromLevelXToLevelY(level, targetLevel);
		crewmember.gainExperience(requiredXpToAdvance);
		level = crewmember.getLevel();

		assertEquals(targetLevel, level);

		numberOfLevelsToIncrease = RNG.randInt(1,3);
		targetLevel = level + numberOfLevelsToIncrease;
		requiredXpToAdvance = getRequiredXpFromLevelXToLevelY(level, targetLevel);
		crewmember.gainExperience(requiredXpToAdvance);
		level = crewmember.getLevel();

		assertEquals(targetLevel, level);

		numberOfLevelsToIncrease = RNG.randInt(1,4);
		targetLevel = level + numberOfLevelsToIncrease;
		requiredXpToAdvance = getRequiredXpFromLevelXToLevelY(level, targetLevel);
		crewmember.gainExperience(requiredXpToAdvance);
		level = crewmember.getLevel();

		assertEquals(targetLevel, level);
	}

	@Test
	public void ensureNoLevelUpWithBoundaryXp() {
		Crewmember crewmember = newCrewmember();

		int xpRequiredForNextLevel = xpForLevels.get(crewmember.getLevel() -1);
		int lessThanOneXpRequired = xpRequiredForNextLevel - 1;
		crewmember.gainExperience(lessThanOneXpRequired);

		assertEquals(1, crewmember.getLevel());
		assertEquals(lessThanOneXpRequired, crewmember.getXp());

		crewmember.gainExperience(1);

		assertEquals(2, crewmember.getLevel());
		assertEquals(0, crewmember.getXp());

		xpRequiredForNextLevel = xpForLevels.get(crewmember.getLevel() -1);
		lessThanOneXpRequired = xpRequiredForNextLevel - 1;
		crewmember.gainExperience(lessThanOneXpRequired);

		assertEquals(2, crewmember.getLevel());
		assertEquals(lessThanOneXpRequired, crewmember.getXp());

		crewmember.gainExperience(1);

		assertEquals(3, crewmember.getLevel());
		assertEquals(0, crewmember.getXp());
	}

	private Crewmember newCrewmember() {
		return new Crewmember("Luke", new PilotClass());
	}

	private int getRequiredXpFromLevelXToLevelY(int x, int y) {
		int indexForLevelX = x-1;
		int indexForLevelY = y-1;
		int reqXp = 0;
		for (int i = indexForLevelX; i <= indexForLevelY-1; i++) {
			reqXp += xpForLevels.get(i);
		}
		return reqXp;
	}

}