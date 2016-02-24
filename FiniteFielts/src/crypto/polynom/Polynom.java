package crypto.polynom;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.google.common.annotations.VisibleForTesting;
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
		List<Polynom> allGeneratingPolynomes = new ArrayList<Polynom>();

		VectorPolynom firstCandidate = VectorPolynom.createVectorPolynom(n + 1);
		firstCandidate.set(0, 1);
		Polynom startingPoly = Polynom.createPolyFromVectorPolynom(firstCandidate, p);

		while (startingPoly.getVector().getDegree() == n && startingPoly.getVector().getValue(0) == 1) {
			if (!startingPoly.hasZeroDivisor()) {
				allGeneratingPolynomes.add(Polynom.createPolynome(startingPoly));
			}
			startingPoly.nextPoly();
		}

		return allGeneratingPolynomes;
	}

	/**
	 * @param p
	 *            has to be a Prim number, is represending the modulo Value for
	 *            the Polynoms
	 * @param n
	 *            has to be a number, is represending the degree of the Polynoms
	 * @param x
	 *            represents the max number of created polynoms it always
	 *            returns only as much as existing
	 * @return a List out of X or all possible Polynoms to generate a modulo
	 *         Field sized p^n
	 */
	public static List<Polynom> createXGeneratingPolynome(int p, int n, int x) {
		List<Polynom> allGeneratingPolynomes = new ArrayList<Polynom>();

		VectorPolynom firstCandidate = VectorPolynom.createVectorPolynom(n + 1);
		firstCandidate.set(0, 1);
		Polynom startingPoly = Polynom.createPolyFromVectorPolynom(firstCandidate, p);

		while (startingPoly.getVector().getDegree() == n && startingPoly.getVector().getValue(0) == 1) {
			if (!startingPoly.hasZeroDivisor()) {
				allGeneratingPolynomes.add(Polynom.createPolynome(startingPoly));
				if (allGeneratingPolynomes.size() == x) {
					return allGeneratingPolynomes;
				}
			}
			startingPoly.nextPoly();
		}

		return allGeneratingPolynomes;
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

	public void reducePolynom() {
		_polynom = _polynom.createReducedVectorPolynom();
	}

	public VectorPolynom getVector() {
		return _polynom;
	}

	public int getModulo() {
		return MODULO;
	}

	/**
	 * returns a new Polynom that is calculated be (Polynom1 + Polynom2) modulo
	 * p. The modulo of both Polynoms has to be equal.
	 */
	public Polynom calculateAddPolynom(Polynom polynom) {
		Preconditions.checkArgument(MODULO == polynom.MODULO, "The given Polynoms are in different Modulo groups");

		int maxDegree = Math.max(_polynom.getDegree(), polynom._polynom.getDegree()) + 1;
		VectorPolynom vp3 = VectorPolynom.createVectorPolynom(maxDegree);
		VectorPolynom a = _polynom.createInverted();
		VectorPolynom b = polynom._polynom.createInverted();
		vp3.forEach(ele -> {
			int index = ele.getIndex();
			ele.setValue((a.getValueOrZero(index) + b.getValueOrZero(index)) % MODULO);
		});

		return createPolyFromVectorPolynom(vp3.createInverted().createReducedVectorPolynom(), MODULO);
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

		return createPolyFromVectorPolynom(vp3.createInverted().createReducedVectorPolynom(), MODULO);
	}

	/**
	 * Returns the Rest as an Polynom of Polynom1/Polynom2
	 */
	public Polynom calculateDividePolynomRest(Polynom polynom) {
		Preconditions.checkArgument(MODULO == polynom.MODULO, "The given Polynoms are in different Modulo groups");
		Preconditions.checkArgument(polynom._polynom.getDegree() > 0, "The given Polynom is just a Number.");

		Polynom p0 = polynom;
		int max = Math.max(this._polynom.getDegree(), polynom._polynom.getDegree());
		int min = Math.min(this._polynom.getDegree(), polynom._polynom.getDegree());
		Polynom rest = this;
		Polynom f = new Polynom((max - min) + 1, MODULO);

		// f*p0+rest == this
		int p0degree = p0._polynom.getDegree();
		int restdegree = rest._polynom.getDegree();
		while (restdegree >= p0degree) {
			// m * Rest(x) / n * P0(x) = k * f(x)
			// m + n*k = 0 -> -m / n = k
			// k = -m*nInvers
			int m = Polynoms.getPositivValue(-rest._polynom.getValue(rest._polynom.size() - (restdegree + 1)), MODULO);
			int nInvers = Polynoms.getInversValue((p0._polynom.getValue(p0._polynom.size() - (p0degree + 1))), MODULO);
			f._polynom.set(restdegree - p0degree, m * nInvers);
			rest = rest.calculateAddPolynom(f.getInvertedPolynom().calculateMultiplyPolynom(p0));

			// reset f
			f._polynom.set(restdegree - p0degree, 0);

			restdegree = rest._polynom.getDegree();
		}

		return createPolyFromVectorPolynom(rest._polynom.createReducedVectorPolynom(), MODULO);
	}

	public boolean hasNullpoints() {
		return !getNullPoints(NullPoints.FIRST).isEmpty();
	}

	public List<Integer> getAllNullPoints() {
		return getNullPoints(NullPoints.ALL);
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

	@VisibleForTesting
	Polynom nextPoly() {
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

	public boolean isZero() {
		Polynom createPolyFromArray = Polynom.createPolyFromArray(new Integer[] { 0 }, MODULO);
		return equals(createPolyFromArray);
	}

	public boolean hasZeroDivisor() {
		int degree = _polynom.getDegree();
		int maxToTest = degree / 2;
		int size = getVector().size();
		VectorPolynom createVectorPolynom = VectorPolynom.createVectorPolynom(size);
		createVectorPolynom.set(size - 2, 1);
		Polynom startingPoly = Polynom.createPolyFromVectorPolynom(createVectorPolynom, MODULO);

		while (startingPoly.getVector().getDegree() <= maxToTest) {
			Polynom calculateDividePolynomRest = calculateDividePolynomRest(startingPoly);
			if (calculateDividePolynomRest.isZero()) {
				return true;
			}
			startingPoly.nextPoly();
		}
		return false;
	}

	private List<Integer> getNullPoints(NullPoints nulls) {
		List<Integer> nullPoints = new ArrayList<Integer>();
		for (int i = 0; i < MODULO; i++) {
			final int input = i;
			int lenght = _polynom.size();
			int sum = _polynom.stream()

					.filter(ele -> ele.getValue() != 0)

					.map(ele -> ele.getValue() * (int) Math.pow(input, lenght - 1 - ele.getIndex()))

					.reduce(0, (total, ele) -> total + ele);

			if (sum % MODULO == 0) {
				nullPoints.add(input);
				if (nulls == NullPoints.FIRST) {
					return nullPoints;
				}
			}
			sum = 0;
		}
		return nullPoints;
	}

	private Polynom getInvertedPolynom() {
		return new Polynom(_polynom.createInverted(), MODULO);
	}

	private static List<Polynom> getPolynomes(int p, int n, PolynomCreator polynomCreator) {
		List<Polynom> polynomes = new ArrayList<Polynom>();
		int polynomCount;
		Polynom startPoly;
		switch (polynomCreator) {
		case GENERATING_POLYNOM:
			startPoly = new Polynom(n + 1, p, 1);
			polynomCount = (int) Math.pow(p, n - 1) + 1;
			break;
		case ALL_POLYNOM:
			startPoly = new Polynom(n, p);
			polynomCount = (int) Math.pow(p, n) - 1;
			break;
		default:
			throw new IllegalArgumentException("The following Enum is not Implemented: " + polynomCreator.name());
		}

		polynomes.add(new Polynom(startPoly));
		for (int i = 0; i < polynomCount; i++) {
			polynomes.add(startPoly.nextPoly());
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

	public String toHtmlString() {

		String polynom = "";
		for (int i = 0; i < _polynom.size(); i++) {

			Integer wert = _polynom.getValue(i);
			if (wert != 0) {

				if ((i + 1) != _polynom.size()) {

					if (wert != 1) {

						polynom += wert;
					}
					polynom += "x<sup>" + (_polynom.size() - (i + 1)) + "</sup>+";
				} else {
					polynom += wert;
				}
			}

		}
		return polynom;
	}
}
