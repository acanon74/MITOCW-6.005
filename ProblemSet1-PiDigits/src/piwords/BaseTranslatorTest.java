package piwords;

import static org.junit.Assert.*;

import org.junit.Test;

import java.util.ArrayList;

public class BaseTranslatorTest {
    @Test
    /**basicBaseTranslatorTest
     *
     * Preconditions:
     * baseA >= 2, baseB >= 2, precisionB >= 1
     *
     * digits[n] >= 0 for every n
     * digits[n] < baseA for every n
     *
     * Special cases:
     * If digits[i] < 0 or digits[i] >= baseA for any i, return null
     * If baseA < 2, baseB < 2, or precisionB < 1, return null
     *
     * Test strategy:
     *
     * We test the whole input space and make sure we include the special
     * cases.
     *
     * Breakdown of every test used:
     *
     * Special cases:
     *
     * digits[1] < 0
     * assertArrayEquals(null, BaseTranslator.convertBase(new int[]{8, -1, 7}, 20, 7, 3));
     * digits[0] >= baseA
     * assertArrayEquals(null, BaseTranslator.convertBase(new int[]{7, 6}, 2, 14, 2));
     *
     * baseA <= 2
     * assertArrayEquals(null, BaseTranslator.convertBase(new int[]{1, 10}, 1, 7, 2));
     * baseB <= 2
     * assertArrayEquals(null, BaseTranslator.convertBase(new int[]{2, 3}, 5, 1, 2));
     * precisionB < 1
     * assertArrayEquals(null, BaseTranslator.convertBase(new int[]{6, 4, 5, 7}, 8, 7, -5));
     *
     * Rest of the input space:
     * A case that should not trigger any special case
     * assertArrayEquals(new int[]{0, 0, 1, 1, 2}, BaseTranslator.convertBase(new int[]{1, 2, 3, 4, 5}, 45, 18, 5));
     */

    public void basicBaseTranslatorTest() {
        // Expect that .01 in base-2 is .25 in base-10
        // (0 * 1/2^1 + 1 * 1/2^2 = .25)
        assertArrayEquals(new int[]{2, 5},
                BaseTranslator.convertBase(new int[]{0, 1}, 2, 10, 2));

        //My solution tests:
        assertArrayEquals(null,
                BaseTranslator.convertBase(new int[]{8, -1, 7}, 20, 7, 3));
        assertArrayEquals(null,
                BaseTranslator.convertBase(new int[]{7, 1}, 2, 14, 2));

        assertArrayEquals(null,
                BaseTranslator.convertBase(new int[]{1, 10}, 1, 7, 2));
        assertArrayEquals(null,
                BaseTranslator.convertBase(new int[]{2, 3}, 5, 1, 2));
        assertArrayEquals(null,
                BaseTranslator.convertBase(new int[]{6, 4, 5, 7}, 8, 7, -5));

        assertArrayEquals(new int[]{0, 0, 1, 1, 2},
                BaseTranslator.convertBase(new int[]{1, 2, 3, 4, 5}, 45, 18, 5));
    }

    // TODO: Write more tests (Problem 2.a)
}
