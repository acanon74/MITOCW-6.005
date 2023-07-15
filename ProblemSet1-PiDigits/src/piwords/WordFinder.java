package piwords;

import java.util.HashMap;
import java.util.Map;

public class WordFinder {
    /**
     * Given a String (the haystack) and an array of Strings (the needles),
     * return a Map<String, Integer>, where keys in the map correspond to
     * elements of needles that were found as substrings of haystack, and the
     * value for each key is the lowest index of haystack at which that needle
     * was found. A needle that was not found in the haystack should not be
     * returned in the output map.
     * 
     * @param haystack The string to search into.
     * @param needles The array of strings to search for. This array is not
     *                mutated.
     * @return The list of needles that were found in the haystack.
     */

    public static Map<String, Integer> getSubstrings(String haystack,
                                                     String[] needles) { 
        // TODO: Implement (Problem 4.b)
        HashMap<String, Integer> result = new HashMap<>();

        for (String w : needles) {

            for (int i = 0; i < haystack.length(); i++) {

                String subHaystack = haystack.substring(i);

                if (searchWord(subHaystack, w)) {
                    result.put(w, i);
                    break;
                }

            }
        }


        return result;
    }

    private static boolean searchWord(String text, String word) {

        if (word.length() == 0) {
            return true;
        } else if (text.length() == 0 && word.length() != 0) {
            return false;
        } else if (text.charAt(0) == word.charAt(0)) {
            return searchWord(text.substring(1),
                    word.substring(1));
        } else {
            return false;
        }

    }

}
