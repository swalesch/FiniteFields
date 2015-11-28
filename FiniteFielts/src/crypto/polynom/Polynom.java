package crypto.polynom;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.google.common.base.Preconditions;

public class Polynom {
	private VectorPolynom _polynom;
	private final int MODULO;

	private enum PolynomCreator {
		GENERATING_POLYNOM, ALL_POLYNOM
	}

	private enum NullPoints {
		ALL, FIRST
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
		List<Polynom> allGeneratingPolynomes = getPolynomes(p, n, PolynomCreator.GENERATING_POLYNOM);

		return allGeneratingPolynomes.stream()

				.filter(ele -> ele.hasNullpoints())

				.collect(Collectors.toList());
	}

	public static List<Polynom> createGeneratingPolynomes2(int p, int n) {
		List<Polynom> allGeneratingPolynomes = getPolynomes2(p, n, PolynomCreator.GENERATING_POLYNOM);

		return allGeneratingPolynomes.stream()

				.filter(ele -> ele.hasNullpoints())

				.collect(Collectors.toList());
	}

	public static List<Polynom> createGeneratingPolynomes3(int p, int n) {
		List<Polynom> allGeneratingPolynomes = new ArrayList<Polynom>();

		Polynom startPoly = new Polynom(n + 1, p, 1);
		int polynomCount = (int) Math.pow(p, n - 1);

		for (int i = 0; i <= polynomCount + 1; i++) {
			if (startPoly.hasNullpoints()) {
				allGeneratingPolynomes.add(new Polynom(startPoly));
			}
			startPoly.nextPoly();
		}

		return allGeneratingPolynomes;
	}

	public static Optional<Polynom> createOneGeneratingPolynomes(int p, int n) {
		List<Polynom> allGeneratingPolynomes = getPolynomes(p, n, PolynomCreator.GENERATING_POLYNOM);

		return allGeneratingPolynomes.stream()

				.filter(ele -> ele.hasNullpoints())

				.findFirst();

	}

	public static Polynom createPolynome(Polynom poly) {
		return new Polynom(poly);
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
		return new Polynom(VectorPolynom.createVectorPolynomFromArray(vector), p);
	}

	/**
	 * Create a new Polynom from List<Integer>
	 */
	public static Polynom createPolyFromList(List<Integer> vector, int p) {
		return new Polynom(VectorPolynom.createVectorPolynomFromList(vector), p);
	}

	/**
	 * Create a new Polynom from List<Integer>
	 */
	public static Polynom createPolyFromVectorPolynom(VectorPolynom vp, int p) {
		return new Polynom(vp, p);
	}

	/**
	 * returns a new Polynom that is calculated be (Polynom1 + Polynom2) modulo
	 * p. The modulo of both Polynoms has to be equal.
	 */
	public Polynom calculateAddPolynom(Polynom polynom) {
		Preconditions.checkArgument(MODULO == polynom.MODULO, "The given Polynoms are in different Modulo groups");

		int maxDegree = Math.max(_polynom.getDegree(), polynom._polynom.getDegree()) + 1;
		VectorPolynom vp3 = VectorPolynom.createVectorPolynom(maxDegree);
		vp3.forEach(ele -> {
			int index = ele.getIndex();
			ele.setValue((_polynom.getValueOrZero(index) + polynom._polynom.getValueOrZero(index)) % MODULO);
		});

		return createPolyFromVectorPolynom(vp3, MODULO);
	}

	/**
	 * returns a new Polynom that is calculated be (Polynom1 * Polynom2) modulo
	 * p. The modulo of both Polynoms has to be equal.
	 */
	public Polynom calculateMultiplyPolynom(Polynom polynom) {
		Preconditions.checkArgument(MODULO == polynom.MODULO, "The given Polynoms are in different Modulo groups");

		int maxDegree = _polynom.getDegree() + polynom._polynom.getDegree() + 1;
		VectorPolynom vp3 = VectorPolynom.createVectorPolynom(maxDegree);
		VectorPolynom vp2 = polynom._polynom.createInverted();
		_polynom.createInverted().stream()

				.filter(ele -> ele.getValue() != 0).forEach(ele -> {
					vp2.stream().filter(ele2 -> ele2.getValue() != 0).forEach(ele2 -> {
						int index = ele2.getIndex() + ele.getIndex();
						vp3.set(index, (vp3.getValue(index) + ele.getValue() * ele2.getValue()) % MODULO);
					});
				});

		return createPolyFromVectorPolynom(vp3.createInverted(), MODULO);
	}

	/**
	 * Returns the Rest as an Polynom of Polynom1/Polynom2
	 */
	public Polynom calculateDividePolynomRest(Polynom polynom) {
		Preconditions.checkArgument(MODULO == polynom.MODULO, "The given Polynoms are in different Modulo groups");

		Polynom p0 = polynom;
		int max = Math.max(this._polynom.getDegree(), polynom._polynom.getDegree());
		int min = Math.min(this._polynom.getDegree(), polynom._polynom.getDegree());
		Polynom rest = this;
		Polynom f = new Polynom((max - min + 1), MODULO);

		// f*p0+rest == this
		int p0degree = p0._polynom.getDegree();
		int restdegree = rest._polynom.getDegree();
		while (restdegree >= p0degree) {
			f._polynom.set(restdegree - p0degree, p0._polynom.getValue(p0degree)
					* Polynoms.getInversValue(rest._polynom.getValue(restdegree), p0.MODULO));
			rest = this.calculateAddPolynom(f.getInvertedPolynom().calculateMultiplyPolynom(p0));
			restdegree = rest._polynom.getDegree();
		}

		return rest;
	}

	@Override
	public String toString() {
		String polynom = "(";
		for (int i = 0; i < _polynom.size(); i++) {
			polynom += (_polynom.getValue(i) + (((i + 1) == _polynom.size()) ? "" : ","));
		}
		polynom += ")";
		return polynom;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Polynom) {
			Polynom poly = (Polynom) obj;
			if (MODULO == poly.MODULO) {
				VectorPolynom max = _polynom.max(poly._polynom).createInverted();
				VectorPolynom min = poly._polynom.min(_polynom).createInverted();
				Optional<VectorPair> isNotEq = max.stream()

						.filter(ele -> ele.getValue() != min.getValueOrZero(ele.getIndex()))

						.findFirst();

				if (isNotEq.isPresent()) {
					return false;
				}
				return true;
			}
		}
		return false;
	}

	public boolean hasNullpoints() {
		return getNullPoints(NullPoints.FIRST).isEmpty();
	}

	public List<Integer> getAllNullPoints() {
		return getNullPoints(NullPoints.ALL);
	}

	/**
	 * @param p
	 *            has to be a Prim number, is represending the modulo Value for
	 *            the Polynom
	 * @return List with numbers therfor the polynome becomes Zero, will return
	 *         a empty List if there are no nullPoints
	 */
	List<Integer> getNullPoints(NullPoints nulls) {
		List<Integer> nullPoints = new ArrayList<Integer>();
		for (int i = 0; i < MODULO; i++) {
			final int input = i;
			int lenght = _polynom.size();
			int sum = _polynom.stream()

					.filter(ele -> lenght - 1 != ele.getIndex())

					.map(ele -> (int) Math.pow(ele.getValue() * input, lenght - 1 - ele.getIndex()))

					.reduce(0, (total, ele) -> total + ele);

			sum += _polynom.getValue(lenght - 1);

			if (sum % MODULO == 0) {
				nullPoints.add(input);
				if (nulls == NullPoints.FIRST) {
					return nullPoints;
				}
			}
			sum = 0;
		}
		return nullPoints;
		// return nullPoints;
	}

	private Polynom getInvertedPolynom() {
		return new Polynom(_polynom.createInverted(), MODULO);
	}

	private static List<Polynom> getPolynomes2(int p, int n, PolynomCreator polynomCreator) {
		List<Polynom> polynomes = new ArrayList<Polynom>();
		int polynomCount;
		Polynom startPoly;
		switch (polynomCreator) {
		case GENERATING_POLYNOM:
			startPoly = new Polynom(n + 1, p, 1);
			polynomCount = (int) Math.pow(p, n - 1);
			break;
		case ALL_POLYNOM:
			startPoly = new Polynom(n, p);
			polynomCount = (int) Math.pow(p, n);
			break;
		default:
			throw new IllegalArgumentException("The following Enum is not Implemented: " + polynomCreator.name());
		}

		polynomes.add(new Polynom(startPoly));
		for (int i = 0; i <= polynomCount; i++) {
			polynomes.add(startPoly.nextPoly());
		}

		return polynomes;
	}

	public Polynom nextPoly() {
		for (int i = _polynom.size() - 1; i >= 0; i--) {
			Integer value = _polynom.getValue(i);
			if ((value + 1) % MODULO == 0) {
				_polynom.set(i, 0);
			} else {
				_polynom.set(i, value + 1);
				break;
			}
		}
		return new Polynom(this);
	}

	// neue idee, Polynom +1 mit übertrag bei Modulo!! schrittweise
	// zurückgeben
	// und
	// nullstelle berechnen zum schnelleren generieren, besser eine 2.
	// getPolynoms funktion schreiben zum testen
	private static List<Polynom> getPolynomes(int p, int n, PolynomCreator polynomCreator) {
		Preconditions.checkNotNull(polynomCreator);

		List<Polynom> polynomes = new ArrayList<Polynom>();
		int polynomCount = (int) Math.pow(p, n);
		int help = 0;
		for (int i = 0; i < polynomCount; i++) {
			switch (polynomCreator) {
			case GENERATING_POLYNOM:
				polynomes.add(new Polynom(n + 1, p, 1));
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

	private Polynom(int size, int p, int startValue) {
		Preconditions.checkArgument(Polynoms.isPrime(p), "p has to be Prim");
		_polynom = VectorPolynom.createVectorPolynom(size);
		_polynom.set(0, startValue);
		MODULO = p;
	}

	private Polynom(VectorPolynom vp, int p) {
		Preconditions.checkArgument(Polynoms.isPrime(p), "p has to be Prim");
		_polynom = vp;
		MODULO = p;
	}

	private Polynom(int size, int p) {
		Preconditions.checkArgument(Polynoms.isPrime(p), "p has to be Prim");
		_polynom = VectorPolynom.createVectorPolynom(size);
		MODULO = p;
	}

	private Polynom(Polynom poly) {
		_polynom = VectorPolynom.createVectorPolynom(poly._polynom);
		MODULO = poly.MODULO;
	}
}
