package piwords;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

public class WordFinderTest {
    @Test
    /**
     *
     * Special cases:
     *
     * haystack.length == 0
     * needles.length == 0
     *
     * Test strategy:
     *
     * We define a haystack and needles in which we can find all the possible
     * outcomes for a given needle. Those being:
     *
     * - It is a word.
     * - It is part of a word.
     * - It is not in the haystack.
     * - The needle can be found in two distinct places.
     *
     * Breadkdown of every test used:
     *
     * It is a word.
     * expectedOutput2.put("alphabet", 0);
     * expectedOutput2.put("generation", 9);
     *
     * It is part of a word.
     * expectedOutput2.put("alpha", 0);
     * expectedOutput2.put("bet", 5);
     *
     * It appears twice or more in the haystack
     * expectedOutput2.put("alpha", 0); //The index must be 0 rather than 20.
     *
     * It is not in the haystack.
     * needle2[n] == "software".
     *
     * haystack.length == 0.
     * assertEquals(new HashMap<String, Integer>(), WordFinder.getSubstrings(
     * new String(""), new String[] {"a", "b", "c"}));
     *
     * needles.length == 0
     * assertEquals(new HashMap<String, Integer>(), WordFinder.getSubstrings(
     * new String("abc"), new String[] {}));
     */

    public void basicGetSubstringsTest() {
        String haystack = "abcde";
        String[] needles = {"ab", "abc", "de", "fg"};

        Map<String, Integer> expectedOutput = new HashMap<String, Integer>();
        expectedOutput.put("ab", 0);
        expectedOutput.put("abc", 0);
        expectedOutput.put("de", 3);

        assertEquals(expectedOutput, WordFinder.getSubstrings(haystack, needles));

        //My solution tests:
        String haystack2 = "alphabet generation alpha";
        String[] needles2 = {"alphabet", "generation",
                "alpha", "bet", "software"};

        Map<String, Integer> expectedOutput2 = new HashMap<>();
        expectedOutput2.put("alphabet", 0);
        expectedOutput2.put("alpha", 0);
        expectedOutput2.put("bet", 5);
        expectedOutput2.put("generation", 9);

        assertEquals(expectedOutput2, WordFinder.getSubstrings(haystack2,
                needles2));

        assertEquals(new HashMap<String, Integer>(), WordFinder.getSubstrings(
                new String(""), new String[] {"a", "b", "c"}));

        assertEquals(new HashMap<String, Integer>(), WordFinder.getSubstrings(
                new String("abc"), new String[] {}));
    }
}
