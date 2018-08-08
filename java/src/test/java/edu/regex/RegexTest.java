package edu.regex;

import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import java.util.Arrays;
import java.util.regex.Pattern;

import static org.junit.Assert.assertEquals;

@RunWith(Enclosed.class)
public class RegexTest {

    @RunWith(Parameterized.class)
    public static class IntegerRegexTest {
        private static String integerPatternString = "^[-+]?\\d+$";
        private static Pattern integerPattern = Pattern.compile(integerPatternString);

        @Parameters(name = "{index}: {0} - {1}")
        public static Iterable<Object[]> parameters() {
            return Arrays.asList(new Object[][]{
                {"-2", true},
                {"2", true},
                {"+2", true},
                {"0", true},
                {"+0", true},
                {"-0", true},
                {"0.0", false},
                {"-0.0", false},
                {"+0.0", false},
                {"-2.123", false},
                {"2.123", false},
                {"+2.123", false},
                {".123", false},
                {"1982-02-28", false},
                {"8 (956) 454-23-34", false},
                {"79505536905", true}
            });
        }

        private String matchingString;
        private boolean matchResult;

        public IntegerRegexTest(String matchingString, boolean matchResult) {
            this.matchingString = matchingString;
            this.matchResult = matchResult;
        }

        @Test
        public void integerPatternMatchTest() {
            assertEquals(matchResult, integerPattern.matcher(matchingString).matches());
        }
    }

    @RunWith(Parameterized.class)
    public static class DoubleRegexTest {
        private static String doublePatternString = "^[-+]?\\d+(\\.\\d+)$";
        private static Pattern doublePattern = Pattern.compile(doublePatternString);

        @Parameters(name = "{index}: {0} - {1}")
        public static Iterable<Object[]> parameters() {
            return Arrays.asList(new Object[][]{
                {"-1.123", true},
                {"1.123", true},
                {"+1.123", true},
                {"-0.0", true},
                {"0.0", true},
                {"+0.0", true},
                {"-123", false},
                {"123", false},
                {"+123", false},
                {"1982-02-28", false},
                {"8 (956) 454-23-34", false},
                {"79505536905", false}
            });
        }

        private String matchingString;
        private boolean matchResult;

        public DoubleRegexTest(String matchingString, boolean matchResult) {
            this.matchingString = matchingString;
            this.matchResult = matchResult;
        }

        @Test
        public void doublePatternMatchTest() {
            assertEquals(matchResult, doublePattern.matcher(matchingString).matches());
        }
    }
}
