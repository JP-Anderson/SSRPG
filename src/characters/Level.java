package characters;

import util.dataload.csv.CSV;
import util.dataload.csv.CSVReader;

import java.util.ArrayList;

public class Level {

	private int level;
	private static int maxLevel;
	private int xp;
	private static ArrayList<Integer> xpForLevels;

	static {
		CSV levelsCSV = CSVReader.readCSV("levels");
		xpForLevels = (ArrayList<Integer>) levelsCSV.getZeroIndexedRowAsInts(0);
		maxLevel = xpForLevels.size()+1;
	}

	public Level(int startingLevel) {
		level = startingLevel;
		xp = 0;
	}

	public void gainExperience(int xpGained) {
		if (level < maxLevel) {
			checkForLevelUp(xpGained);
		}
	}

	private void checkForLevelUp(int xpGained) {
		int xpForNextLevel = xpForLevels.get(level-1);
		if (xp + xpGained >= xpForNextLevel) {
			levelUp(xpGained, xpForNextLevel);
		} else {
			xp += xpGained;
		}
	}

	private void levelUp(int xpGained, int xpForNextLevel) {
		int remainder = (xp + xpGained) - xpForNextLevel;
		level++;
		xp = 0;
		if (remainder > 0) gainExperience(remainder);
	}

	public int getLevel() {
		return level;
	}

	public int getXp() {
		return xp;
	}

	public int getRemainingXpForNextLevel() {
		return level < maxLevel ? xpForLevels.get(level-1) - xp
				: 0;
	}

}
