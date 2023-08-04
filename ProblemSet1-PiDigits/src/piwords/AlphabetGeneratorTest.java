package piwords;

import static org.junit.Assert.*;

import org.junit.Test;

public class AlphabetGeneratorTest {
    @Test
    /**
     * Preconditions:
     *
     * base >= 0
     * trainingData might contain other characters outside the a-z range
     *
     * Special cases:
     *
     * If base < 0, return null.
     * If base >= 0, the returned array should have length equal to the size of
     * the base.
     * If a String of trainingData has any characters outside the range a-z,
     * ignore those characters and continue.
     *
     * Test strategy:
     *
     * We create trainingData and expectedOutput such that we find all the possible
     * outcomes for a given translation. Those are:
     *
     * - It contains chars outside, which a-z and must be ignored.
     * - It must contain chars which have different and equal frequency.
     *
     * Breakdown of every test used:
     * String[] trainingData2 = {"qqq", "ww", "ee", "r", "-*-"};
     * char[] expectedOutput2 = {'e','e','q','q','q','r','w','w'};
     *
     * This input data checks whether the weight are correct,
     * what happens if two characters have the same frequency,
     * whether the code ignores characters outside the a-z range "-*-".
     * and the lexicographical order.
     */

    public void generateFrequencyAlphabetTest() {

        // Expect in the training data that Pr(a) = 2/5, Pr(b) = 2/5, Pr(c) = 1/5.
        String[] trainingData = {"aa", "bbc"};
        // In the output for base 10, they should be in the same proportion.
        char[] expectedOutput = {'a', 'a', 'a', 'a',
                                 'b', 'b', 'b', 'b',
                                 'c', 'c'};
        assertArrayEquals(expectedOutput,
                AlphabetGenerator.generateFrequencyAlphabet(
                        10, trainingData));

        String[] trainingData2 = {"qqq", "ww", "ee", "r", "-*-"};
        char[] expectedOutput2 = {'e','e','q','q','q','r','w','w'};

        /**
         * Here is the expected data for my own test above. You can uncomment the print statements for every step in the AlphabetGenerator.java file.
         * total chars: 8
         * q 3
         * w 2
         * e 2
         * r 1
         * - should be ignored, that is frequency 0 and calculations should remain as below
         *
         * pdf
         * 0.375
         * 0.25
         * 0.25
         * 0.125
         *
         * cdf
         * 0.375
         * 0.625
         * 0.875
         * 1
         * multiplying
         * 3
         * 5
         * 7
         * 8
         * [q,q,q,w,w,e,e,r]
         * by lexico ordering
         * [e,e,q,q,q,r,w,w]
         */

        assertArrayEquals(expectedOutput2,
                AlphabetGenerator.generateFrequencyAlphabet(
                        8, trainingData2));
    }
    // TODO: Write more tests (Problem 5.a)
}
