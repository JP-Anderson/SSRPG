package util.rng;

import java.util.ArrayList;

public class MockRandomNumberGenerator implements RandomNumberGenerator {

	private ArrayList<Integer> randIntValues;
	private ArrayList<Double> randZeroToOneValues;

	private int randIntValueIndex;
	private int randDoubleValueIndex;

	public MockRandomNumberGenerator() {
		randIntValues = new ArrayList<>();
		randZeroToOneValues = new ArrayList<>();
	}

	public MockRandomNumberGenerator(double randomZeroToOne) {
		this();
		randZeroToOneValues.add(randomZeroToOne);
	}

	public MockRandomNumberGenerator(int integer) {
		this();
		randIntValues.add(integer);
	}

	public void loadSequenceOfIntegers(ArrayList<Integer> intsToLoad) {
		randIntValues = intsToLoad;
	}

	public void loadSequenceOfRandZeroToOneDoubles(ArrayList<Double> doublesToLoad) {
		randZeroToOneValues = doublesToLoad;
	}

	public void loadSingleInteger(int integer) {
		randIntValues.add(integer);
	}

	public void loadSingleRandZeroToOneDouble(double zeroToOneDouble) {
		randZeroToOneValues.add(zeroToOneDouble);
	}

	@Override
	public int randInt(int min, int max) {
		randIntValueIndex = randIntValueIndex >= randIntValues.size() ? 0 : randIntValueIndex;
		Integer fakeRandomInt = randIntValues.get(randIntValueIndex);
		randIntValueIndex++;
		return fakeRandomInt;
	}

	@Override
	public double randZeroToOne() {
		randDoubleValueIndex = randDoubleValueIndex >= randZeroToOneValues.size() ? 0 : randDoubleValueIndex;
		Double fakeRandomDouble = randZeroToOneValues.get(randDoubleValueIndex);
		randDoubleValueIndex++;
		return fakeRandomDouble;
	}
}
