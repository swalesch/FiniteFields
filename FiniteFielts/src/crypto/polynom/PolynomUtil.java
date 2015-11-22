package crypto.polynom;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PolynomUtil {

	/**
	 * prints a List out of Polynomes
	 */
	public static void printPolys(List<Polynom> polynomes) {
		mapPolynomesToStrings(polynomes).forEach(System.out::println);
	}

	/**
	 * maps a List of Polynomes to a List of Strings
	 */
	public static ArrayList<String> mapPolynomesToStrings(List<Polynom> polynomes) {
		ArrayList<String> polynomesString = polynomes.stream().map(ele -> ele.toString())
				.collect(Collectors.toCollection(ArrayList::new));
		return polynomesString;
	}
}
