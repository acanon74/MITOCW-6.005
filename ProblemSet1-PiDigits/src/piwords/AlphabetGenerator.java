package piwords;

import java.util.*;
import static java.util.Arrays.sort;

public class AlphabetGenerator {
    /**
     * Given a numeric base, return a char[] that maps every digit that is
     * representable in that base to a lower-case char.
     * <p>
     * This method will try to weight each character of the alphabet
     * proportional to their occurrence in words in a training set.
     * <p>
     * This method should do the following to generate an alphabet:
     * 1. Count the occurrence of each character a-z in trainingData.
     * 2. Compute the probability of each character a-z by taking
     * (occurrence / total_num_characters).
     * 3. The output generated in step (2) is a PDF of the characters in the
     * training set. Convert this PDF into a CDF for each character.
     * 4. Multiply the CDF value of each character by the base we are
     * converting into.
     * 5. For each index 0 <= i < base,
     * output[i] = (the first character whose CDF * base is > i)
     * <p>
     * A concrete example:
     * 0. Input = {"aaaaa..." (302 "a"s), "bbbbb..." (500 "b"s),
     * "ccccc..." (198 "c"s)}, base = 93
     * 1. Count(a) = 302, Count(b) = 500, Count(c) = 193
     * 2. Pr(a) = 302 / 1000 = .302, Pr(b) = 500 / 1000 = .5,
     * Pr(c) = 198 / 1000 = .198
     * 3. CDF(a) = .302, CDF(b) = .802, CDF(c) = 1
     * 4. CDF(a) * base = 28.086, CDF(b) * base = 74.586, CDF(c) * base = 93
     * 5. Output = {"a", "a", ... (28 As, indexes 0-27),
     * "b", "b", ... (47 Bs, indexes 28-74),
     * "c", "c", ... (18 Cs, indexes 75-92)}
     * <p>
     * The letters should occur in lexicographically ascending order in the
     * returned array.
     * - {"a", "b", "c", "c", "d"} is a valid output.
     * - {"b", "c", "c", "d", "a"} is not.
     * <p>
     * If base >= 0, the returned array should have length equal to the size of
     * the base.
     * <p>
     * If base < 0, return null.
     * <p>
     * If a String of trainingData has any characters outside the range a-z,
     * ignore those characters and continue.
     *
     * @param base         A numeric base to get an alphabet for.
     * @param trainingData The training data from which to generate frequency
     *                     counts. This array is not mutated.
     * @return A char[] that maps every digit of the base to a char that the
     * digit should be translated into.
     */
    public static char[] generateFrequencyAlphabet(int base,
                                                   String[] trainingData) {
        // TODO: Implement (Problem 5.b)
        String alphabet = "abcdefghijklmnopqrstuvwxyz";
        HashMap<Character, Integer> frequencyTable = new HashMap<>();

        //1.
        int charCounter = 0;

        for (String word : trainingData) {
            for (int i = 0; i < word.length(); i++) {

                if (!alphabet.contains(word.charAt(i) + "")) {
                    continue;
                }

                if (!frequencyTable.containsKey(word.charAt(i))) {
                    frequencyTable.put(word.charAt(i), 1);
                } else {
                    int value = frequencyTable.get(word.charAt(i));
                    frequencyTable.put(word.charAt(i), ++value);
                }
                charCounter++;
            }
        }
        //System.out.println(frequencyTable.toString());


        //2.
        HashMap<Character, Double> pdfTable = new HashMap<>();

        for (char character : frequencyTable.keySet()) {
            int frequency = frequencyTable.get(character);
            pdfTable.put(character, frequency / (double) charCounter);
        }
        //System.out.println(pdfTable.toString());


        //3.
        HashMap<Character, Double> cdfTable = new HashMap<>();
        HashMap<Character, Double> CopyPdfTable = new HashMap<>(pdfTable);

        double cdfTotal = 0;

        HashMap<Character, Double> indexArray = new HashMap<>();

        for (int i = 0; i < pdfTable.keySet().size(); i++) {

            Pair<Character, Double> result = getBiggest(CopyPdfTable);

            cdfTotal += result.b;

            cdfTable.put(result.a, cdfTotal);
            CopyPdfTable.remove(result.a);

            //4.
            indexArray.put(result.a, cdfTotal * base);
        }
        //System.out.println(cdfTable.toString());
        //System.out.println(indexArray.toString());


        //5.
        HashMap<Character, Double> copyIndexArray = new HashMap<>(indexArray);
        HashMap<Character, Double> sortedIndexArray = new HashMap<>(indexArray);

        //Here I couldn't find a better way of sorting a map by its value's value so I ordered using a stack.
        Stack orderedStack = new Stack();

        for (int i = 0; i < indexArray.size(); i++) {

            Pair<Character, Double> result = getBiggest(copyIndexArray);

            copyIndexArray.remove(result.a);
            orderedStack.push(result);
        }

        //Building the weighted dictionary

        char[] weightedAlphabet = new char[base];

        Pair<Character, Double> currentPair = (Pair<Character, Double>) orderedStack.pop();
        char currentChar = currentPair.a;
        Double currentDouble = currentPair.b;

        for (int i = 0; i < base; i++) {

            if (currentDouble <= i) {
                currentPair = (Pair<Character, Double>) orderedStack.pop();
                currentChar = currentPair.a;
                currentDouble = currentPair.b;
            }
            weightedAlphabet[i] = currentChar;

        }
        //System.out.println(weightedAlphabet.toString());


        sort(weightedAlphabet);

        return weightedAlphabet;
    }


    private static Pair<Character, Double> getBiggest(HashMap<Character, Double> hash) {

        char charPointer = (char) hash.keySet().toArray()[0];
        double bigPointer = hash.get(charPointer);

        for (Character character : hash.keySet()) {

            if (hash.get(character) > bigPointer) {
                bigPointer = hash.get(character);
                charPointer = character;
            }
        }

        return new Pair<Character, Double>(charPointer, bigPointer);
    }
}