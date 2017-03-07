package characters;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import util.rng.RNG;
import util.dataload.csv.CSV;
import util.dataload.csv.CSVReader;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class LevelTest {

	static ArrayList<Integer> xpForLevels;
	RNG rand = new RNG();

	@BeforeAll
	public static void loadXpForLevels() {
		CSV levelsCSV = CSVReader.readCSV("levels");
		xpForLevels = (ArrayList<Integer>) levelsCSV.getZeroIndexedRowAsInts(0);
	}

	@Test
	public void ensureLevelUpWorksWithExactXp() {
		Level level = newLevel();
		assertEquals(1, level.getLevel());
		assertEquals(0, level.getXp());

		level.gainExperience(100);

		assertEquals(2, level.getLevel());
		assertEquals(0, level.getXp());
	}

	@Test
	public void ensureFinalLevelCharacterCannotGainExperience() {
		Level level = newLevel();

		for (int i = 0; i < xpForLevels.size(); i++) {
			int xpForNextLevel = xpForLevels.get(i);
			level.gainExperience(xpForNextLevel);
			assertEquals(i+2, level.getLevel());
		}

		level.gainExperience(1000000);

		int maxLevel = xpForLevels.size() + 1;
		assertEquals(maxLevel, level.getLevel());
		assertEquals(0, level.getXp());
		assertEquals(0, level.getRemainingXpForNextLevel());
	}

	@Test
	public void ensureCorrectAmountOfXpCarriedOverWithRemainder() {
		Level level = newLevel();

		int xpRequiredForNextLevel = xpForLevels.get(level.getLevel() - 1);
		int moreThanOneXpRequired = xpRequiredForNextLevel + 1;
		level.gainExperience(moreThanOneXpRequired);

		assertEquals(2, level.getLevel());
		assertEquals(1, level.getXp());

		xpRequiredForNextLevel = xpForLevels.get(level.getLevel() - 1);
		assertEquals(xpRequiredForNextLevel-1, level.getRemainingXpForNextLevel());

		xpRequiredForNextLevel = level.getRemainingXpForNextLevel();
		int gainedExperience = xpRequiredForNextLevel + (xpRequiredForNextLevel/2);
		int remainder = gainedExperience - xpRequiredForNextLevel;
		level.gainExperience(gainedExperience);

		assertEquals(3, level.getLevel());
		assertEquals(remainder, level.getXp());
	}

	@Test
	public void ensureCrewmemberLevelsUpMultipleTimesOnGainingEnoughXP() {
		Level level = newLevel();

		int startingLevel = level.getLevel();
		int numberOfLevelsToIncrease = rand.randInt(1,2);
		int targetLevel = startingLevel + numberOfLevelsToIncrease;
		int requiredXpToAdvance = getRequiredXpFromLevelXToLevelY(startingLevel, targetLevel);
		level.gainExperience(requiredXpToAdvance);
		startingLevel = level.getLevel();

		assertEquals(targetLevel, startingLevel);

		numberOfLevelsToIncrease = rand.randInt(1,2);
		targetLevel = startingLevel + numberOfLevelsToIncrease;
		requiredXpToAdvance = getRequiredXpFromLevelXToLevelY(startingLevel, targetLevel);
		level.gainExperience(requiredXpToAdvance);
		startingLevel = level.getLevel();

		assertEquals(targetLevel, startingLevel);

		numberOfLevelsToIncrease = rand.randInt(1,2);
		targetLevel = startingLevel + numberOfLevelsToIncrease;
		requiredXpToAdvance = getRequiredXpFromLevelXToLevelY(startingLevel, targetLevel);
		level.gainExperience(requiredXpToAdvance);
		startingLevel = level.getLevel();

		assertEquals(targetLevel, startingLevel);

		numberOfLevelsToIncrease = rand.randInt(1,4);
		targetLevel = startingLevel + numberOfLevelsToIncrease;
		requiredXpToAdvance = getRequiredXpFromLevelXToLevelY(startingLevel, targetLevel);
		level.gainExperience(requiredXpToAdvance);
		startingLevel = level.getLevel();

		assertEquals(targetLevel, startingLevel);
	}

	@Test
	public void ensureNoLevelUpWithBoundaryXp() {
		Level level = newLevel();

		int xpRequiredForNextLevel = xpForLevels.get(level.getLevel() -1);
		int lessThanOneXpRequired = xpRequiredForNextLevel - 1;
		level.gainExperience(lessThanOneXpRequired);

		assertEquals(1, level.getLevel());
		assertEquals(lessThanOneXpRequired, level.getXp());

		level.gainExperience(1);

		assertEquals(2, level.getLevel());
		assertEquals(0, level.getXp());

		xpRequiredForNextLevel = xpForLevels.get(level.getLevel() -1);
		lessThanOneXpRequired = xpRequiredForNextLevel - 1;
		level.gainExperience(lessThanOneXpRequired);

		assertEquals(2, level.getLevel());
		assertEquals(lessThanOneXpRequired, level.getXp());

		level.gainExperience(1);

		assertEquals(3, level.getLevel());
		assertEquals(0, level.getXp());
	}

	private Level newLevel() {
		return new Level(1);
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