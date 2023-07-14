package piwords;

import static org.junit.Assert.*;

import org.junit.Test;

import java.math.BigInteger;
import java.util.Arrays;

public class PiGeneratorTest {
    @Test
    /**basicPowerModTest
     * Preconditions:
     * a >= 0, b >= 0, m >= 0
     *
     * We partition the input space as follows:
     *
     * a might be: 0, 1, int.
     * b might be: 0, 1, int.
     * m might be: 1, int.
     *
     * m = 0 is an illegal input because the mod operation involves division, and we are expected to throw an ArithmeticException.
     *
     *
     * We create a table with 3 columns indicating the value of a. And 3 rows indicating the value of b.
     * We divide every cell into 2 subcells, which indicate the value of m.
     *
     * Test strategy:
     *
     * We operate some values of a, b following the table, and we noticed that there are only 3 different possible outputs for a^b. Those being,
     * 0, 1 or some value x. Seeing this, I decided to further classify a^b mod m by whether m is 1 or int. Thus, we get only 3x2=6 tests.
     *
     * Breakdown of every test used:
     *
     * a = 0, b = int, m = int
     * assertEquals(0, PiGenerator.powerMod(0, 34, 12));
     * a = 0, b = 1, m = 1
     * assertEquals(0, PiGenerator.powerMod(0, 1, 1));
     *
     * a = int, b = int, m = int
     * assertEquals(10, PiGenerator.powerMod(100, 7, 30));
     * a = int, b = 1, m = 1
     * assertEquals(0, PiGenerator.powerMod(50, 1, 1));
     *
     * a = 1, b = 1, m = 1
     * assertEquals(0, PiGenerator.powerMod(1, 1, 1));
     * a = int, b = 0, m = int
     * assertEquals(1, PiGenerator.powerMod(81, 0, 10));
     */
    public void basicPowerModTest() {
        // 5^7 mod 23 = 17
        assertEquals(17, PiGenerator.powerMod(5, 7, 23));

        // My solution tests:
        assertEquals(0, PiGenerator.powerMod(0, 34, 12));
        assertEquals(0, PiGenerator.powerMod(0, 1, 1));
        assertEquals(10, PiGenerator.powerMod(100, 7, 30));
        assertEquals(0, PiGenerator.powerMod(50, 1, 1));
        assertEquals(0, PiGenerator.powerMod(1, 1, 1));
        assertEquals(1, PiGenerator.powerMod(81, 0, 10));

    }
    @Test
    /**computePiInHexTest
     *
     * Preconditions:
     * If precision < 0, return null.
     *
     * We partition the input space as follows:
     *
     * precision might be: 0, 1, int.
     *
     * Test strategy:
     * We just test the three cases, precision = 0, 1, int. We also test a somewhat big precision. N Tests: 4.
     */

    public void computePiInHexTest() {

        int[] zeroHexArr = new int[0];
        int[] oneHexArr = new int[]{2};
        int[] smallHexArr = new int[]{2, 4, 3, 0xf};
        int[] bigHexArr = new int[]{2, 4, 3, 0xf, 6, 0xa, 8, 8, 8, 5, 0xa, 3};

        assertTrue(Arrays.equals(zeroHexArr, PiGenerator.computePiInHex(0)));
        assertTrue(Arrays.equals(oneHexArr, PiGenerator.computePiInHex(1)));
        assertTrue(Arrays.equals(smallHexArr, PiGenerator.computePiInHex(4)));
        assertTrue(Arrays.equals(bigHexArr, PiGenerator.computePiInHex(12)));
    }
}
