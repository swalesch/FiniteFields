package crypto.polynom;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class PolynomTest {

	@Test
	public void testCreateAllPolynomes() {
		List<Polynom> allPolynomes = Polynom.createAllPolynomes(2, 2);

		assertThat(allPolynomes).isNotEmpty();
		assertThat(allPolynomes).containsOnlyElementsOf(getAllPolynomP2N2());

		allPolynomes = Polynom.createAllPolynomes(2, 3);

		assertThat(allPolynomes).isNotEmpty();
		assertThat(allPolynomes).containsOnlyElementsOf(getAllPolynomP2N3());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testNotPrim() {
		assertThat(Polynom.createAllPolynomes(4, 25));
	}

	@Test
	public void testCreateGeneratingPolynoms() {
		List<Polynom> generatingPolynomes = Polynom.createGeneratingPolynomes(2, 2);
		assertThat(generatingPolynomes).isNotEmpty();
		assertThat(generatingPolynomes).containsOnlyElementsOf(getGeneratingPolynomP2N2());

		generatingPolynomes = Polynom.createGeneratingPolynomes(2, 3);
		assertThat(generatingPolynomes).isNotEmpty();
		assertThat(generatingPolynomes).containsOnlyElementsOf(getGeneratingPolynomP2N3());
	}

	@Test
	public void testNullpoints() {
		assertThat(Polynom.createPolyFromArray(new int[] { 1, 1, 1 }, 2).getAllNullPoints()).isEmpty();
		assertThat(Polynom.createPolyFromArray(new int[] { 1, 1, 0 }, 2).getAllNullPoints()).containsOnly(0, 1);
		assertThat(Polynom.createPolyFromArray(new int[] { 1, 1, 1, 1 }, 2).getAllNullPoints()).containsOnly(1);
		assertThat(Polynom.createPolyFromArray(new int[] { 1, 1, 0 }, 3).getAllNullPoints()).containsOnly(0, 2);
		assertThat(Polynom.createPolyFromArray(new int[] { 1, 0, 1 }, 3).getAllNullPoints()).isEmpty();
		assertThat(Polynom.createPolyFromArray(new int[] { 1, 2, 1 }, 3).getAllNullPoints()).containsOnly(2);
	}

	@Test
	public void testAddPolynom() {
		assertThat(Polynom.createPolyFromArray(new int[] { 0, 1 }, 2)
				.calculateAddPolynom(Polynom.createPolyFromArray(new int[] { 1, 0 }, 2)))
						.isEqualTo(Polynom.createPolyFromArray(new int[] { 1, 1 }, 2));

		assertThat(Polynom.createPolyFromArray(new int[] { 1, 1 }, 2)
				.calculateAddPolynom(Polynom.createPolyFromArray(new int[] { 1, 1 }, 2)))
						.isEqualTo(Polynom.createPolyFromArray(new int[] { 0, 0 }, 2));

		assertThat(Polynom.createPolyFromArray(new int[] { 1, 1 }, 2)
				.calculateAddPolynom(Polynom.createPolyFromArray(new int[] { 1, 1 }, 2)))
						.isEqualTo(Polynom.createPolyFromArray(new int[] { 0 }, 2));

		assertThat(Polynom.createPolyFromArray(new int[] { 0, 0 }, 2)
				.calculateAddPolynom(Polynom.createPolyFromArray(new int[] { 1, 1 }, 2)))
						.isEqualTo(Polynom.createPolyFromArray(new int[] { 1, 1 }, 2));

		assertThat(Polynom.createPolyFromArray(new int[] { 1, 0 }, 2)
				.calculateAddPolynom(Polynom.createPolyFromArray(new int[] { 1, 0 }, 2)))
						.isEqualTo(Polynom.createPolyFromArray(new int[] { 0, 0 }, 2));

		assertThat(Polynom.createPolyFromArray(new int[] { 1, 2 }, 3)
				.calculateAddPolynom(Polynom.createPolyFromArray(new int[] { 1, 0 }, 3)))
						.isEqualTo(Polynom.createPolyFromArray(new int[] { 2, 2 }, 3));

		assertThat(Polynom.createPolyFromArray(new int[] { 1, 2 }, 3)
				.calculateAddPolynom(Polynom.createPolyFromArray(new int[] { 1, 1 }, 3)))
						.isEqualTo(Polynom.createPolyFromArray(new int[] { 2, 0 }, 3));

	}

	@Test
	public void testMultiplyPolynom() {
		assertThat(Polynom.createPolyFromArray(new int[] { 1 }, 2)
				.calculateMultiplyPolynom(Polynom.createPolyFromArray(new int[] { 0, 1 }, 2)))
						.isEqualTo(Polynom.createPolyFromArray(new int[] { 1 }, 2));

		assertThat(Polynom.createPolyFromArray(new int[] { 1 }, 2)
				.calculateMultiplyPolynom(Polynom.createPolyFromArray(new int[] { 1, 0 }, 2)))
						.isEqualTo(Polynom.createPolyFromArray(new int[] { 1, 0 }, 2));

		assertThat(Polynom.createPolyFromArray(new int[] { 1, 1 }, 2)
				.calculateMultiplyPolynom(Polynom.createPolyFromArray(new int[] { 1, 1 }, 2)))
						.isEqualTo(Polynom.createPolyFromArray(new int[] { 1, 0, 1 }, 2));

		assertThat(Polynom.createPolyFromArray(new int[] { 1, 0 }, 2)
				.calculateMultiplyPolynom(Polynom.createPolyFromArray(new int[] { 1, 1 }, 2)))
						.isEqualTo(Polynom.createPolyFromArray(new int[] { 1, 1, 0 }, 2));

		assertThat(Polynom.createPolyFromArray(new int[] { 1, 1 }, 2)
				.calculateMultiplyPolynom(Polynom.createPolyFromArray(new int[] { 1, 0, 1 }, 2)))
						.isEqualTo(Polynom.createPolyFromArray(new int[] { 1, 1, 1, 1 }, 2));

		assertThat(Polynom.createPolyFromArray(new int[] { 1, 1 }, 3)
				.calculateMultiplyPolynom(Polynom.createPolyFromArray(new int[] { 2, 1 }, 3)))
						.isEqualTo(Polynom.createPolyFromArray(new int[] { 2, 0, 1 }, 3));

	}

	@Test
	public void testEquels() {
		assertThat(Polynom.createPolyFromArray(new int[] { 0, 1 }, 2))
				.isNotEqualTo(Polynom.createPolyFromArray(new int[] { 1, 0 }, 2));

		assertThat(Polynom.createPolyFromArray(new int[] { 1 }, 2))
				.isNotEqualTo(Polynom.createPolyFromArray(new int[] { 1, 0 }, 2));

		assertThat(Polynom.createPolyFromArray(new int[] { 1, 1 }, 2))
				.isEqualTo(Polynom.createPolyFromArray(new int[] { 0, 1, 1 }, 2));

		assertThat(Polynom.createPolyFromArray(new int[] { 1, 0 }, 2))
				.isNotEqualTo(Polynom.createPolyFromArray(new int[] { 0 }, 2));

	}

	@Test
	public void testDegree() {
		assertThat(Polynom.createPolyFromArray(new int[] { 1, 1 }, 2).getDegree()).isEqualTo(1);
		assertThat(Polynom.createPolyFromArray(new int[] { 0, 1, 1 }, 2).getDegree()).isEqualTo(1);
		assertThat(Polynom.createPolyFromArray(new int[] { 1, 0, 0 }, 2).getDegree()).isEqualTo(2);
		assertThat(Polynom.createPolyFromArray(new int[] { 0, 0, 1 }, 2).getDegree()).isEqualTo(0);
	}

	@Test
	public void testDivPolynom() {
		assertThat(Polynom.createPolyFromArray(new int[] { 1, 0, 1 }, 2)
				.calculateDividePolynomRest(Polynom.createPolyFromArray(new int[] { 1, 1, 1 }, 2)))
						.isEqualTo(Polynom.createPolyFromArray(new int[] { 1, 0 }, 2));

		assertThat(Polynom.createPolyFromArray(new int[] { 1, 1 }, 2)
				.calculateDividePolynomRest(Polynom.createPolyFromArray(new int[] { 1, 1, 1 }, 2)))
						.isEqualTo(Polynom.createPolyFromArray(new int[] { 1, 1 }, 2));

		assertThat(Polynom.createPolyFromArray(new int[] { 1, 1, 1, 1 }, 2)
				.calculateDividePolynomRest(Polynom.createPolyFromArray(new int[] { 1, 0, 1, 1 }, 2)))
						.isEqualTo(Polynom.createPolyFromArray(new int[] { 1, 0, 0 }, 2));

		assertThat(Polynom.createPolyFromArray(new int[] { 2, 0, 1 }, 3)
				.calculateDividePolynomRest(Polynom.createPolyFromArray(new int[] { 1, 0, 1 }, 3)))
						.isEqualTo(Polynom.createPolyFromArray(new int[] { 2 }, 3));
	}

	private static List<Polynom> getAllPolynomP2N2() {
		List<Polynom> polys = new ArrayList<Polynom>();
		polys.add(Polynom.createPolyFromArray(new int[] { 0, 0 }, 2));
		polys.add(Polynom.createPolyFromArray(new int[] { 0, 1 }, 2));
		polys.add(Polynom.createPolyFromArray(new int[] { 1, 0 }, 2));
		polys.add(Polynom.createPolyFromArray(new int[] { 1, 1 }, 2));
		return polys;
	}

	private static List<Polynom> getGeneratingPolynomP2N2() {
		return Arrays.asList(Polynom.createPolyFromArray(new int[] { 1, 1, 1 }, 2));
	}

	private static List<Polynom> getGeneratingPolynomP2N3() {
		List<Polynom> polys = new ArrayList<Polynom>();
		polys.add(Polynom.createPolyFromArray(new int[] { 1, 0, 1, 1 }, 2));
		polys.add(Polynom.createPolyFromArray(new int[] { 1, 1, 0, 1 }, 2));
		return polys;
	}

	private static List<Polynom> getAllPolynomP2N3() {
		List<Polynom> polys = new ArrayList<Polynom>();
		polys.add(Polynom.createPolyFromArray(new int[] { 0, 0, 0 }, 2));
		polys.add(Polynom.createPolyFromArray(new int[] { 0, 0, 1 }, 2));
		polys.add(Polynom.createPolyFromArray(new int[] { 0, 1, 0 }, 2));
		polys.add(Polynom.createPolyFromArray(new int[] { 0, 1, 1 }, 2));
		polys.add(Polynom.createPolyFromArray(new int[] { 1, 0, 0 }, 2));
		polys.add(Polynom.createPolyFromArray(new int[] { 1, 0, 1 }, 2));
		polys.add(Polynom.createPolyFromArray(new int[] { 1, 1, 0 }, 2));
		polys.add(Polynom.createPolyFromArray(new int[] { 1, 1, 1 }, 2));
		return polys;
	}
}
