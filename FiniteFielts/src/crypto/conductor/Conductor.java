package crypto.conductor;

import java.util.List;

import crypto.polynom.Polynom;
import crypto.polynom.Polynoms;

public class Conductor {

	public static void main(String[] args) {
		// Fi = p^n
		int p = 2, n = 3;
		// get generating Polynoms
		List<Polynom> generatingPolynoms = Polynom.createGeneratingPolynomes(p, n);
		System.out.println("Generierungspolynom:");
		Polynoms.printPolys(generatingPolynoms);
		// Zahlenmenge erzeugen
		// Rechentabelle erstellen

	}

}
