package crypto.polynom;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Polynoms {

    /**
     * prints a List out of Polynomes
     */
    public static void printPolys(List<Polynom> polynomes) {
        mapPolynomesToStrings(polynomes).forEach(System.out::println);
    }

    public static boolean isValidPolyVektorString(String polynomString) {
        return polynomString.matches("^\\([\\d+,]+\\d+\\)");
    }

    /**
     * maps a List of Polynomes to a List of Strings
     */
    public static ArrayList<String> mapPolynomesToStrings(List<Polynom> polynomes) {
        ArrayList<String> polynomesString = polynomes.stream().map(ele -> ele.toString())
                .collect(Collectors.toCollection(ArrayList::new));
        return polynomesString;
    }

    /**
     * @param n
     *            a Number that should be tested
     * @return returns true if the given Number n is prim
     */
    public static boolean isPrime(long n) {
        if (n < 2)
            return false;
        if (n == 2 || n == 3)
            return true;
        if (n % 2 == 0 || n % 3 == 0)
            return false;
        long sqrtN = (long) Math.sqrt(n) + 1;
        for (long i = 6L; i <= sqrtN; i += 6) {
            if (n % (i - 1) == 0 || n % (i + 1) == 0)
                return false;
        }
        return true;
    }

    /**
     * returns the Invers Value of a (a^-1)
     */
    public static int getInversValue(int a, int modulo) {
        for (int i = 1; i < modulo; i++) {
            if ((i * a) % modulo == 1)
                return i;
        }
        return 0;
    }

    public static int getPositivValue(int value, int modulo) {
        int positivValue = value % modulo;
        if (positivValue == 0) {
            return positivValue;
        } else {
            return positivValue + modulo;
        }
    }
}
