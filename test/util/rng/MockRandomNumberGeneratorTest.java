package util.rng;

import org.junit.Test;

import base.SsrpgTest;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.*;

public class MockRandomNumberGeneratorTest extends SsrpgTest {

	@Test
	public void verifyMockRNGConstructedWithDoubleWillRepeatDouble() {
		MockRandomNumberGenerator mockRNG = new MockRandomNumberGenerator(0.2);

		assertEquals(0.2, mockRNG.randZeroToOne(), 0D);
		assertEquals(0.2, mockRNG.randZeroToOne(), 0D);
		assertEquals(0.2, mockRNG.randZeroToOne(), 0D);
	}

	@Test
	public void verifySingleIntegerLoadedWillRepeat() {
		MockRandomNumberGenerator mockRNG = new MockRandomNumberGenerator();
		mockRNG.loadSequenceOfIntegers(new ArrayList<>(Arrays.asList(0)));

		assertEquals(0, mockRNG.randInt(0,10));
		assertEquals(0, mockRNG.randInt(0,10));
		assertEquals(0, mockRNG.randInt(0,10));
	}

	@Test
	public void verifyLoadedIntegersRepeat() {
		MockRandomNumberGenerator mockRNG = new MockRandomNumberGenerator();
		mockRNG.loadSequenceOfIntegers(new ArrayList<>(
				Arrays.asList(1,2,3,4)
				));

		assertEquals(1, mockRNG.randInt(0,10));
		assertEquals(2, mockRNG.randInt(0,10));
		assertEquals(3, mockRNG.randInt(0,10));
		assertEquals(4, mockRNG.randInt(0,10));
		assertEquals(1, mockRNG.randInt(0,10));
		assertEquals(2, mockRNG.randInt(0,10));
		assertEquals(3, mockRNG.randInt(0,10));
		assertEquals(4, mockRNG.randInt(0,10));
		assertEquals(1, mockRNG.randInt(0,10));
		assertEquals(2, mockRNG.randInt(0,10));
		assertEquals(3, mockRNG.randInt(0,10));
		assertEquals(4, mockRNG.randInt(0,10));
	}

	@Test
	public void verifyLoadedDoublesRepeat() {
		MockRandomNumberGenerator mockRNG = new MockRandomNumberGenerator();
		mockRNG.loadSequenceOfRandZeroToOneDoubles(new ArrayList<>(
				Arrays.asList(0.1,2.0,0.5432)
		));

		assertEquals(0.1, mockRNG.randZeroToOne(), 0D);
		assertEquals(2.0, mockRNG.randZeroToOne(), 0D);
		assertEquals(0.5432, mockRNG.randZeroToOne(), 0D);
		assertEquals(0.1, mockRNG.randZeroToOne(), 0D);
		assertEquals(2.0, mockRNG.randZeroToOne(), 0D);
		assertEquals(0.5432, mockRNG.randZeroToOne(), 0D);
	}

}