package util.rng;

import java.util.Random;

public class RNG implements RandomNumberGenerator {
	/**
	 * Returns a pseudo-random number between min and max, inclusive.
	 * The difference between min and max can be at most
	 * <code>Integer.MAX_VALUE - 1</code>.
	 *
	 * @param min Minimum value
	 * @param max Maximum value.  Must be greater than min.
	 * @return Integer between min and max, inclusive.
	 * @see java.util.Random#nextInt(int)
	 */

	private Random random = new Random();

	public int randInt(int min, int max) {
		// nextInt is normally exclusive of the top value,
		// so add 1 to make it inclusive
		int randomNum = random.nextInt((max - min) + 1) + min;

		return randomNum;
	}

	public double randZeroToOne() {
		return random.nextDouble();
	}
}
