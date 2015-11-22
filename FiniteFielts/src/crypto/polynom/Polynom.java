package crypto.polynom;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Preconditions;

public class Polynom {
	private int[] _polynom;
	private final int MODULO;

	private enum PolynomCreator {
		GENERATING_POLYNOM, ALL_POLYNOM
	}

	/**
	 * @param p
	 *            has to be a Prim number, is represending the modulo Value for
	 *            the Polynoms
	 * @param n
	 *            has to be a number, is represending the degree of the Polynoms
	 * @return a List out of all possible Polynoms to generate a modulo Field
	 *         sized p^n
	 */
	public static List<Polynom> createGeneratingPolynomes(int p, int n) {

		// getKandidates
		List<Polynom> allGeneratingPolynomes = getPolynomes(p, n, PolynomCreator.GENERATING_POLYNOM);
		// filter by Nullpoints
		return allGeneratingPolynomes.stream().filter(ele -> ele.getAllNullPoints().isEmpty())
				.collect(Collectors.toCollection(ArrayList::new));

	}

	/**
	 * @param p
	 *            has to be a Prim number, is represending the modulo Value for
	 *            the Polynoms
	 * @param n
	 *            has to be a number, is represending the degree of the Polynoms
	 * @return a List out of all possible Polynoms
	 */
	public static List<Polynom> createAllPolynomes(int p, int n) {
		return getPolynomes(p, n, PolynomCreator.ALL_POLYNOM);
	}

	/**
	 * Create a new Polynom from int Array
	 */
	public static Polynom createPolyFromArray(int[] vector, int p) {
		return new Polynom(vector, p);
	}

	/**
	 * @param p
	 *            has to be a Prim number, is represending the modulo Value for
	 *            the Polynom
	 * @return List with numbers therfor the polynome becomes Zero, will return
	 *         a empty List if there are no nullPoints
	 */
	public List<Integer> getAllNullPoints() {

		int sum = 0;
		List<Integer> nullPoints = new ArrayList<Integer>();
		for (int inputValue = 0; inputValue < MODULO; inputValue++) {
			for (int position = 0; position < _polynom.length; position++) {
				if (_polynom.length - 1 == position) {
					sum += _polynom[position];
				} else {
					sum += (int) Math.pow(_polynom[position] * inputValue, _polynom.length - 1 - position);
				}
			}
			if (sum % MODULO == 0) {
				nullPoints.add(inputValue);
			}
			sum = 0;
		}

		return nullPoints;
	}

	/**
	 * returns a new Polynom that is calculated be (Polynom1 + Polynom2) modulo
	 * p. The modulo of both Polynoms has to be equal.
	 */
	public Polynom createAddPolynom(Polynom polynom) {
		Preconditions.checkArgument(MODULO == polynom.MODULO, "The given Polynoms are in different Modulo groups");
		int maxDegree = Math.max(this.getDegree(), polynom.getDegree());
		int[] a = this.getInvertedPolynom()._polynom;
		int[] b = polynom.getInvertedPolynom()._polynom;
		int[] c = new int[maxDegree];

		// initial c
		for (int i = 0; i < c.length; i++) {
			c[i] = 0;
		}

		// a+b=c
		for (int i = 0; i < maxDegree; i++) {
			if (a.length > i && b.length > i) {
				// rechnen
				c[i] = (a[i] + b[i]) % MODULO;
			} else {
				// nur noch adden bis ende
				if (a.length > i) {
					c[i] = a[i];
				} else {
					c[i] = b[i];
				}
			}
		}
		return createPolyFromArray(c, MODULO).getInvertedPolynom();
	}

	/**
	 * returns a new Polynom that is calculated be (Polynom1 * Polynom2) modulo
	 * p. The modulo of both Polynoms has to be equal.
	 */
	public Polynom createMuliplyPolynom(Polynom polynom) {
		Preconditions.checkArgument(MODULO == polynom.MODULO, "The given Polynoms are in different Modulo groups");
		int[] a = this.getInvertedPolynom()._polynom;
		int[] b = polynom.getInvertedPolynom()._polynom;
		int[] c = new int[this.getDegree() + polynom.getDegree() + 1];

		// initial c
		for (int i = 0; i < c.length; i++) {
			c[i] = 0;
		}

		// a*b = c
		for (int i = 0; i < this.getDegree() + 1; i++) {
			for (int j = 0; j < polynom.getDegree() + 1; j++) {
				if (a[i] > 0 && b[j] > 0) {
					c[i + j] = (c[i + j] + a[i] * b[j]) % MODULO;
				} else {
					if (a[i] > 0) {
						c[i] = (c[i] + a[i] * b[j]) % MODULO;
					} else if (b[j] > 0) {
						c[j] = (c[j] + a[i] * b[j]) % MODULO;
					}
				}
			}
		}

		return createPolyFromArray(c, MODULO).getInvertedPolynom();
	}

	private Polynom getInvertedPolynom() {
		int[] invertedPoly = _polynom.clone();

		for (int i = 0; i < invertedPoly.length / 2; i++) {
			int temp = invertedPoly[i];
			invertedPoly[i] = invertedPoly[invertedPoly.length - i - 1];
			invertedPoly[invertedPoly.length - i - 1] = temp;
		}
		return Polynom.createPolyFromArray(invertedPoly, MODULO);
	}

	@VisibleForTesting
	int getDegree() {
		for (int i = 0; i < _polynom.length; i++) {
			if (_polynom[i] > 0) {
				return _polynom.length - 1 - i;
			}
		}
		return 0;
	}

	@VisibleForTesting
	Polynom(int size, int p) {
		Preconditions.checkArgument(PolynomUtil.isPrime(p), "p has to be Prim");
		_polynom = new int[size];
		MODULO = p;
	}

	private Polynom(int[] vector, int p) {
		Preconditions.checkArgument(PolynomUtil.isPrime(p), "p has to be Prim");
		_polynom = vector;
		MODULO = p;
	}

	private Polynom(int size, int startValue, int p) {
		Preconditions.checkArgument(PolynomUtil.isPrime(p), "p has to be Prim");
		_polynom = new int[size];
		_polynom[0] = startValue;
		MODULO = p;
	}

	private static List<Polynom> getPolynomes(int p, int n, PolynomCreator polynomCreator) {
		Preconditions.checkNotNull(polynomCreator);

		List<Polynom> polynomes = new ArrayList<Polynom>();
		int polynomCount = (int) Math.pow(p, n);
		int help = 0;
		for (int i = 0; i < polynomCount; i++) {
			switch (polynomCreator) {
			case GENERATING_POLYNOM:
				polynomes.add(new Polynom(n + 1, 1, p));
				break;
			case ALL_POLYNOM:
				polynomes.add(new Polynom(n, p));
				help = 1;
				break;
			default:
				throw new IllegalArgumentException("The following Enum is not Implemented: " + polynomCreator.name());
			}
		}

		int value = 0;
		int minorCycle = 0;
		int majorCycle = 0;
		for (int positionArray = 1; positionArray <= n; positionArray++) {

			value = 0;
			minorCycle = 0;
			majorCycle = 0;
			for (int positionListe = 0; positionListe < polynomCount; positionListe++) {
				// je prime +1
				polynomes.get(positionListe)._polynom[positionArray - help] = value;
				minorCycle++;
				majorCycle++;
				if (minorCycle == Math.pow(p, n - positionArray)) {
					value++;
					minorCycle = 0;
					if (positionArray > 1 && majorCycle == Math.pow(p, n - positionArray + 1)) {
						value = 0;
						majorCycle = 0;
					}
				}
			}
		}
		return polynomes;
	}

	@Override
	public String toString() {
		String polynom = "(";
		for (int i = 0; i < _polynom.length; i++) {
			polynom += (_polynom[i] + (((i + 1) == _polynom.length) ? "" : ","));
		}
		polynom += ")";
		return polynom;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Polynom) {
			Polynom poly = (Polynom) obj;
			if (MODULO == poly.MODULO) {
				if (_polynom.length == poly._polynom.length) {
					return Arrays.equals(_polynom, poly._polynom);
				} else {
					int[] a = this.getInvertedPolynom()._polynom;
					int[] b = poly.getInvertedPolynom()._polynom;
					int maxDegree = Math.max(this.getDegree(), poly.getDegree());
					for (int i = 0; i < maxDegree; i++) {
						if (a.length > i && b.length > i) {
							if (a[i] != b[i]) {
								return false;
							}
						} else {
							if (a.length > i) {
								if (a[i] != 0) {
									return false;
								}
							} else {
								if (b[i] != 0) {
									return false;
								}
							}
						}
					}
					return true;
				}
			}
		}
		return false;
	}
}
