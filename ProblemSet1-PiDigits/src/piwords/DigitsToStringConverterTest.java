package piwords;

import static org.junit.Assert.*;

import org.junit.Test;

public class DigitsToStringConverterTest {
    @Test
    /**basicNumberSerializerTest
     * Preconditions:
     * digits[n] < base for every n
     * digits[n] >= 0 for every n
     * alphabet.length == base
     *
     * Special cases:
     * If digits[i] >= base or digits[i] < 0 for any i, return null.
     * If alphabet.length != base, return null.
     *
     * Test strategy:
     * We just have to test the whole input space, and put special care into
     * the special cases.
     *
     * Breakdown of every test used:
     *
     * Special cases:
     *
     * digits[1] > base
     * assertEquals(null, DigitsToStringConverter.convertDigitsToString(
     * new int[]{2, 55, 3}, 4, alphabet));
     * digits[1] == base
     * assertEquals(null, DigitsToStringConverter.convertDigitsToString(
     * new int[]{1, 4, 1}, 4, alphabet));
     *
     * digits[1] < 0
     * assertEquals(null, DigitsToStringConverter.convertDigitsToString(
     * new int[]{0, -3, 2}, 4, alphabet));
     *
     * In the test alphabet.length = 4, so here alphabet.length != 10
     * assertEquals(null, DigitsToStringConverter.convertDigitsToString(
     * new int[]{1, 2, 3}, 10, alphabet));
     *
     * Rest of the input space:
     *
     * We use an inverse int array to reverse the alphabet, which is qwerty.
     * assertEquals(new String("ytrewq"), DigitsToStringConverter.convertDigitsToString(
     * new int[]{5,4,3,2,1,0}, 6, new char[]{'q', 'w', 'e', 'r', 't', 'y'}));
     */



    public void basicNumberSerializerTest() {
        // Input is a 4 digit number, 0.123 represented in base 4
        int[] input = {0, 1, 2, 3};

        // Want to map 0 -> "d", 1 -> "c", 2 -> "b", 3 -> "a"
        char[] alphabet = {'d', 'c', 'b', 'a'};

        String expectedOutput = "dcba";
        assertEquals(expectedOutput, DigitsToStringConverter.convertDigitsToString(
                input, 4, alphabet));


        assertEquals(null, DigitsToStringConverter.convertDigitsToString(
                new int[]{2, 55, 3}, 4, alphabet));
        assertEquals(null, DigitsToStringConverter.convertDigitsToString(
                new int[]{1, 4, 1}, 4, alphabet));

        assertEquals(null, DigitsToStringConverter.convertDigitsToString(
                new int[]{0, -3, 2}, 4, alphabet));

        assertEquals(null, DigitsToStringConverter.convertDigitsToString(
                new int[]{1, 2, 3}, 10, alphabet));

        assertEquals(new String("ytrewq"), DigitsToStringConverter.convertDigitsToString(
                new int[]{5,4,3,2,1,0}, 6, new char[]{'q', 'w', 'e', 'r', 't', 'y'}));


    }

    // TODO: Write more tests (Problem 3.a)
}
