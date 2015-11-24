package crypto.polynom;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;

public class Polynom {
	private List<Integer> _polynom;
	private final int LENGHT;
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
	public static Polynom createPolyFromArray(Integer[] vector, int p) {
		return new Polynom(vector, p);
	}

	/**
	 * Create a new Polynom from List<Integer>
	 */
	public static Polynom createPolyFromList(List<Integer> vector, int p) {
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
			for (int position = 0; position < LENGHT; position++) {
				if (LENGHT - 1 == position) {
					sum += _polynom.get(position);
				} else {
					sum += (int) Math.pow(_polynom.get(position) * inputValue, LENGHT - 1 - position);
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
	public Polynom calculateAddPolynom(Polynom polynom) {
		Preconditions.checkArgument(MODULO == polynom.MODULO, "The given Polynoms are in different Modulo groups");
		int maxDegree = Math.max(this.getDegree(), polynom.getDegree()) + 1;

		this.getInvertedPolynom()._polynom.toArray(new Integer[] {});

		Integer[] a = this.getInvertedPolynom()._polynom.toArray(new Integer[] {});
		Integer[] b = polynom.getInvertedPolynom()._polynom.toArray(new Integer[] {});
		Integer[] c = new Integer[maxDegree];

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
	public Polynom calculateMultiplyPolynom(Polynom polynom) {
		Preconditions.checkArgument(MODULO == polynom.MODULO, "The given Polynoms are in different Modulo groups");
		Integer[] a = this.getInvertedPolynom()._polynom.toArray(new Integer[] {});
		Integer[] b = polynom.getInvertedPolynom()._polynom.toArray(new Integer[] {});
		Integer[] c = new Integer[this.getDegree() + polynom.getDegree() + 1];

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

	/**
	 * Returns the Rest as an Polynom of Polynom1/Polynom2
	 */
	public Polynom calculateDividePolynomRest(Polynom polynom) {
		Preconditions.checkArgument(MODULO == polynom.MODULO, "The given Polynoms are in different Modulo groups");
		Polynom p0 = polynom;
		int max = Math.max(this.getDegree(), polynom.getDegree());
		int min = Math.min(this.getDegree(), polynom.getDegree());
		Polynom rest = this;
		Polynom f = new Polynom((max - min + 1), MODULO);
		// f*p0+rest == this
		int p0degree = p0.getDegree();
		int restdegree = rest.getDegree();
		while (restdegree >= p0degree) {
			f._polynom.set(restdegree - p0degree,
					p0._polynom.get(p0degree) * PolynomUtil.getInversValue(rest._polynom.get(restdegree), p0.MODULO));
			rest = this.calculateAddPolynom(f.getInvertedPolynom().calculateMultiplyPolynom(p0));
			restdegree = rest.getDegree();
		}

		return rest;
	}

	private Polynom getInvertedPolynom() {
		return new Polynom(Lists.reverse(_polynom), MODULO);
	}

	@VisibleForTesting
	int getDegree() {
		for (int i = 0; i < LENGHT; i++) {
			if (_polynom.get(i) > 0) {
				return LENGHT - 1 - i;
			}
		}
		return 0;
	}

	@VisibleForTesting
	Polynom(int size, int p) {
		Preconditions.checkArgument(PolynomUtil.isPrime(p), "p has to be Prim");
		_polynom = new ArrayList<Integer>();
		LENGHT = size;
		initPoly();
		MODULO = p;
	}

	private Polynom(List<Integer> polynom, int p) {
		Preconditions.checkArgument(PolynomUtil.isPrime(p), "p has to be Prim");
		_polynom = polynom;
		MODULO = p;
		LENGHT = polynom.size();
	}

	private Polynom(Integer[] vector, int p) {
		Preconditions.checkArgument(PolynomUtil.isPrime(p), "p has to be Prim");
		_polynom = Arrays.asList(vector);
		LENGHT = vector.length;
		MODULO = p;
	}

	private Polynom(int size, int startValue, int p) {
		Preconditions.checkArgument(PolynomUtil.isPrime(p), "p has to be Prim");
		_polynom = new ArrayList<Integer>();
		LENGHT = size;
		initPoly();
		_polynom.set(0, startValue);
		MODULO = p;
	}

	private void initPoly() {
		for (int i = 0; i < LENGHT; i++) {
			_polynom.add(0);
		}
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
				polynomes.get(positionListe)._polynom.set(positionArray - help, value);
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
		for (int i = 0; i < LENGHT; i++) {
			polynom += (_polynom.get(i) + (((i + 1) == LENGHT) ? "" : ","));
		}
		polynom += ")";
		return polynom;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Polynom) {
			Polynom poly = (Polynom) obj;
			if (MODULO == poly.MODULO) {
				if (LENGHT == poly.LENGHT) {
					return _polynom.equals(poly._polynom);
				} else {
					Integer[] a = this.getInvertedPolynom()._polynom.toArray(new Integer[] {});
					Integer[] b = poly.getInvertedPolynom()._polynom.toArray(new Integer[] {});
					int maxDegree = Math.max(this.getDegree(), poly.getDegree()) + 1;
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
