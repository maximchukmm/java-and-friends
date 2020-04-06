package edu.other;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class AnagramTest {

    @Test
    public void isAnagramVersion1Test() {
        assertTrue(isAnagramVersion1("", ""));
        assertTrue(isAnagramVersion1("anagram", "margana"));
        assertTrue(isAnagramVersion1("Hello", "hello"));
        assertFalse(isAnagramVersion1("anagramm", "marganaa"));
    }

    @Test
    public void isAnagramVersion2Test() {
        assertTrue(isAnagramVersion2("", ""));
        assertTrue(isAnagramVersion2("anagram", "margana"));
        assertTrue(isAnagramVersion2("Hello", "hello"));
        assertFalse(isAnagramVersion2("anagramm", "marganaa"));
        assertFalse(isAnagramVersion2("   ", " "));
    }

    private static boolean isAnagramVersion1(String first, String second) {
        Map<Character, Integer> firstStringLetterEntries = countLetterEntries(first.toLowerCase().toCharArray());
        Map<Character, Integer> secondStringLetterEntries = countLetterEntries(second.toLowerCase().toCharArray());

        return firstStringLetterEntries.equals(secondStringLetterEntries);
    }

    private static Map<Character, Integer> countLetterEntries(char[] chars) {
        Map<Character, Integer> counter = new HashMap<>();

        for (char ch : chars) {
            if (!counter.containsKey(ch))
                counter.put(ch, 1);
            else {
                Integer numberOfEntriesOfCharacter = counter.get(ch);
                counter.put(ch, numberOfEntriesOfCharacter + 1);
            }
        }

        return counter;
    }

    private static boolean isAnagramVersion2(String a, String b) {
        if (a.length() != b.length()) return false;

        char[] aChars = a.toLowerCase().toCharArray();
        char[] bChars = b.toLowerCase().toCharArray();

        sort(aChars);
        sort(bChars);

        for (int i = 0; i < aChars.length; i++)
            if (aChars[i] != bChars[i]) return false;

        return true;
    }

    private static void sort(char[] chars) {
        for (int i = 0; i < chars.length - 1; i++) {
            char minCh = chars[i];
            int minInd = i;

            for (int j = i + 1; j < chars.length; j++) {
                if (chars[j] < minCh) {
                    minCh = chars[j];
                    minInd = j;
                }
            }

            chars[minInd] = chars[i];
            chars[i] = minCh;
        }
    }
}