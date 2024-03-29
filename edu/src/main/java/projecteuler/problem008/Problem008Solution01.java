package projecteuler.problem008;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class Problem008Solution01 {
    private static final int ADJACENT_SIZE = 13;
    private static final String NUM =
            """
                    73167176531330624919225119674426574742355349194934
                    96983520312774506326239578318016984801869478851843
                    85861560789112949495459501737958331952853208805511
                    12540698747158523863050715693290963295227443043557
                    66896648950445244523161731856403098711121722383113
                    62229893423380308135336276614282806444486645238749
                    30358907296290491560440772390713810515859307960866
                    70172427121883998797908792274921901699720888093776
                    65727333001053367881220235421809751254540594752243
                    52584907711670556013604839586446706324415722155397
                    53697817977846174064955149290862569321978468622482
                    83972241375657056057490261407972968652414535100474
                    82166370484403199890008895243450658541227588666881
                    16427171479924442928230863465674813919123162824586
                    17866458359124566529476545682848912883142607690042
                    24219022671055626321111109370544217506941658960408
                    07198403850962455444362981230987879927244284909188
                    84580156166097919133875499200524063689912560717606
                    05886116467109405077541002256983155200055935729725
                    71636269561882670428252483600823257530420752963450"""
                    .replaceAll(System.lineSeparator(), "");

    public static void main(String[] args) {
        List<String> numbers = adjacentNumbers();

        String numberWithMaxDigits = numberWithMaxDigits(numbers);

        System.out.println(digitsMultiplication(numberWithMaxDigits));
    }

    private static BigInteger digitsMultiplication(String digits) {
        BigInteger r = BigInteger.ONE;

        char[] chars = digits.toCharArray();
        for (char c : chars) {
            r = r.multiply(BigInteger.valueOf(Character.digit(c, 10)));
        }

        return r;
    }

    private static String numberWithMaxDigits(List<String> numbers) {
        return numbers
                .stream()
                .filter(n -> !n.contains("0"))
                .map(Problem008Solution01::sort)
                .max(Comparator.naturalOrder())
                .orElseThrow();
    }

    private static List<String> adjacentNumbers() {
        List<String> numbers = new ArrayList<>();

        for (int i = 0; i < NUM.length() - ADJACENT_SIZE; i++) {
            numbers.add(NUM.substring(i, i + ADJACENT_SIZE));
        }
        return numbers;
    }

    private static String sort(String s) {
        char[] chars = s.toCharArray();
        Arrays.sort(chars);

        return String.valueOf(chars);
    }
}
