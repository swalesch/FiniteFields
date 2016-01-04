package crypto.polynom;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class PolynomTest {

	@Test
	public void testCreateAllPolynomes() {
		List<Polynom> allPolynomes = Polynom.createAllPolynomes(2, 2);

		assertThat(allPolynomes).isNotEmpty();
		assertThat(allPolynomes).containsExactlyElementsOf(getAllPolynomP2N2());

		allPolynomes = Polynom.createAllPolynomes(2, 3);

		assertThat(allPolynomes).isNotEmpty();
		assertThat(allPolynomes).containsExactlyElementsOf(getAllPolynomP2N3());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testNotPrim() {
		assertThat(Polynom.createAllPolynomes(4, 25));
	}

	@Test
	public void testCreateGeneratingPolynoms() {
		List<Polynom> generatingPolynomes = Polynom.createGeneratingPolynomes(2, 2);
		assertThat(generatingPolynomes).isNotEmpty();
		assertThat(generatingPolynomes).containsExactlyElementsOf(getGeneratingPolynomP2N2());

		generatingPolynomes = Polynom.createGeneratingPolynomes(2, 3);
		assertThat(generatingPolynomes).isNotEmpty();
		assertThat(generatingPolynomes).containsExactlyElementsOf(getGeneratingPolynomP2N3());

		generatingPolynomes = Polynom.createGeneratingPolynomes(3, 3);
		assertThat(generatingPolynomes).isNotEmpty();
		assertThat(generatingPolynomes).containsExactlyElementsOf(getGeneratingPolynomP3N3());

	}
	
	@Test
	public void testZeroDivision(){
		assertThat(Polynom.createPolyFromArray(new Integer[] { 1, 1, 1 }, 2).hasZeroDivisor()).isFalse();
		assertThat(Polynom.createPolyFromArray(new Integer[] { 1, 1, 0 }, 2).hasZeroDivisor()).isTrue();
		assertThat(Polynom.createPolyFromArray(new Integer[] { 1, 0, 0 }, 2).hasZeroDivisor()).isTrue();
		assertThat(Polynom.createPolyFromArray(new Integer[] { 1, 0, 1 }, 2).hasZeroDivisor()).isTrue();
		assertThat(Polynom.createPolyFromArray(new Integer[] { 1, 1, 1 }, 3).hasZeroDivisor()).isTrue();
		assertThat(Polynom.createPolyFromArray(new Integer[] { 1, 2, 2 }, 3).hasZeroDivisor()).isFalse();
		assertThat(Polynom.createPolyFromArray(new Integer[] { 1, 0, 0, 1 }, 3).hasZeroDivisor()).isTrue();
		assertThat(Polynom.createPolyFromArray(new Integer[] { 1, 1, 0, 2 }, 3).hasZeroDivisor()).isFalse();
		assertThat(Polynom.createPolyFromArray(new Integer[] { 1, 1, 1, 2 }, 3).hasZeroDivisor()).isFalse();
		assertThat(Polynom.createPolyFromArray(new Integer[] { 1, 1, 2, 1 }, 3).hasZeroDivisor()).isFalse();
		assertThat(Polynom.createPolyFromArray(new Integer[] { 1, 2, 1, 1 }, 3).hasZeroDivisor()).isFalse();
		assertThat(Polynom.createPolyFromArray(new Integer[] { 1, 2, 2, 2 }, 3).hasZeroDivisor()).isFalse();
	}
	
	@Test
	public void testIsZero(){
		assertThat(Polynom.createPolyFromArray(new Integer[] { 0 }, 2).isZero()).isTrue();
		assertThat(Polynom.createPolyFromArray(new Integer[] { 0 , 0 }, 2).isZero()).isTrue();
		assertThat(Polynom.createPolyFromArray(new Integer[] { 0 }, 3).isZero()).isTrue();
		assertThat(Polynom.createPolyFromArray(new Integer[] { 1 }, 3).isZero()).isFalse();
		assertThat(Polynom.createPolyFromArray(new Integer[] { 0 , 1 }, 3).isZero()).isFalse();
		assertThat(Polynom.createPolyFromArray(new Integer[] { 0 , 0 , 1 }, 11).isZero()).isFalse();
	}

	@Test
	public void testNullpoints() {
		assertThat(Polynom.createPolyFromArray(new Integer[] { 1, 2, 0, 1 }, 3).getAllNullPoints()).isEmpty();
		assertThat(Polynom.createPolyFromArray(new Integer[] { 1, 1, 1 }, 2).getAllNullPoints()).isEmpty();
		assertThat(Polynom.createPolyFromArray(new Integer[] { 1, 1, 0 }, 2).getAllNullPoints()).containsOnly(0, 1);
		assertThat(Polynom.createPolyFromArray(new Integer[] { 1, 1, 1, 1 }, 2).getAllNullPoints()).containsOnly(1);
		assertThat(Polynom.createPolyFromArray(new Integer[] { 1, 1, 0 }, 3).getAllNullPoints()).containsOnly(0, 2);
		assertThat(Polynom.createPolyFromArray(new Integer[] { 1, 0, 1 }, 3).getAllNullPoints()).isEmpty();
		assertThat(Polynom.createPolyFromArray(new Integer[] { 1, 2, 1 }, 3).getAllNullPoints()).containsOnly(2);
	}

	@Test
	public void testAddPolynom() {
		assertThat(Polynom.createPolyFromArray(new Integer[] { 2, 0, 1 }, 3)
				.calculateAddPolynom(Polynom.createPolyFromArray(new Integer[] { 1, 0, 1 }, 3)))
						.isEqualTo(Polynom.createPolyFromArray(new Integer[] { 2 }, 3));

		assertThat(Polynom.createPolyFromArray(new Integer[] { 2, 0, 1 }, 3)
				.calculateAddPolynom(Polynom.createPolyFromArray(new Integer[] { 1, 0, 2 }, 3)))
						.isEqualTo(Polynom.createPolyFromArray(new Integer[] { 0 }, 3));

		assertThat(Polynom.createPolyFromArray(new Integer[] { 0, 1 }, 2)
				.calculateAddPolynom(Polynom.createPolyFromArray(new Integer[] { 1, 0 }, 2)))
						.isEqualTo(Polynom.createPolyFromArray(new Integer[] { 1, 1 }, 2));

		assertThat(Polynom.createPolyFromArray(new Integer[] { 1, 1 }, 2)
				.calculateAddPolynom(Polynom.createPolyFromArray(new Integer[] { 1, 1 }, 2)))
						.isEqualTo(Polynom.createPolyFromArray(new Integer[] { 0, 0 }, 2));

		assertThat(Polynom.createPolyFromArray(new Integer[] { 1, 1 }, 2)
				.calculateAddPolynom(Polynom.createPolyFromArray(new Integer[] { 1, 1 }, 2)))
						.isEqualTo(Polynom.createPolyFromArray(new Integer[] { 0 }, 2));

		assertThat(Polynom.createPolyFromArray(new Integer[] { 0, 0 }, 2)
				.calculateAddPolynom(Polynom.createPolyFromArray(new Integer[] { 1, 1 }, 2)))
						.isEqualTo(Polynom.createPolyFromArray(new Integer[] { 1, 1 }, 2));

		assertThat(Polynom.createPolyFromArray(new Integer[] { 1, 0 }, 2)
				.calculateAddPolynom(Polynom.createPolyFromArray(new Integer[] { 1, 0 }, 2)))
						.isEqualTo(Polynom.createPolyFromArray(new Integer[] { 0, 0 }, 2));

		assertThat(Polynom.createPolyFromArray(new Integer[] { 1, 2 }, 3)
				.calculateAddPolynom(Polynom.createPolyFromArray(new Integer[] { 1, 0 }, 3)))
						.isEqualTo(Polynom.createPolyFromArray(new Integer[] { 2, 2 }, 3));

		assertThat(Polynom.createPolyFromArray(new Integer[] { 1, 2 }, 3)
				.calculateAddPolynom(Polynom.createPolyFromArray(new Integer[] { 1, 1 }, 3)))
						.isEqualTo(Polynom.createPolyFromArray(new Integer[] { 2, 0 }, 3));

		assertThat(Polynom.createPolyFromArray(new Integer[] { 0, 0 }, 2)
				.calculateAddPolynom(Polynom.createPolyFromArray(new Integer[] { 0, 1 }, 2)))
						.isEqualTo(Polynom.createPolyFromArray(new Integer[] { 0, 1 }, 2));

	}

	@Test
	public void testMultiplyPolynom() {
		assertThat(Polynom.createPolyFromArray(new Integer[] { 1 }, 2)
				.calculateMultiplyPolynom(Polynom.createPolyFromArray(new Integer[] { 0, 1 }, 2)))
						.isEqualTo(Polynom.createPolyFromArray(new Integer[] { 1 }, 2));

		assertThat(Polynom.createPolyFromArray(new Integer[] { 1 }, 2)
				.calculateMultiplyPolynom(Polynom.createPolyFromArray(new Integer[] { 1, 0 }, 2)))
						.isEqualTo(Polynom.createPolyFromArray(new Integer[] { 1, 0 }, 2));

		assertThat(Polynom.createPolyFromArray(new Integer[] { 1, 1 }, 2)
				.calculateMultiplyPolynom(Polynom.createPolyFromArray(new Integer[] { 1, 1 }, 2)))
						.isEqualTo(Polynom.createPolyFromArray(new Integer[] { 1, 0, 1 }, 2));

		assertThat(Polynom.createPolyFromArray(new Integer[] { 1, 0 }, 2)
				.calculateMultiplyPolynom(Polynom.createPolyFromArray(new Integer[] { 1, 1 }, 2)))
						.isEqualTo(Polynom.createPolyFromArray(new Integer[] { 1, 1, 0 }, 2));

		assertThat(Polynom.createPolyFromArray(new Integer[] { 1, 1 }, 2)
				.calculateMultiplyPolynom(Polynom.createPolyFromArray(new Integer[] { 1, 0, 1 }, 2)))
						.isEqualTo(Polynom.createPolyFromArray(new Integer[] { 1, 1, 1, 1 }, 2));

		assertThat(Polynom.createPolyFromArray(new Integer[] { 1, 1 }, 3)
				.calculateMultiplyPolynom(Polynom.createPolyFromArray(new Integer[] { 2, 1 }, 3)))
						.isEqualTo(Polynom.createPolyFromArray(new Integer[] { 2, 0, 1 }, 3));

		assertThat(Polynom.createPolyFromArray(new Integer[] { 0, 1 }, 2)
				.calculateMultiplyPolynom(Polynom.createPolyFromArray(new Integer[] { 0, 1 }, 2)))
						.isEqualTo(Polynom.createPolyFromArray(new Integer[] { 1 }, 2));

		assertThat(Polynom.createPolyFromArray(new Integer[] { 1, 0 }, 2)
				.calculateMultiplyPolynom(Polynom.createPolyFromArray(new Integer[] { 1, 0 }, 2)))
						.isEqualTo(Polynom.createPolyFromArray(new Integer[] { 1, 0, 0 }, 2));

	}

	@Test
	public void testEquels() {
		assertThat(Polynom.createPolyFromArray(new Integer[] { 0, 1 }, 2))
				.isNotEqualTo(Polynom.createPolyFromArray(new Integer[] { 1, 0 }, 2));

		assertThat(Polynom.createPolyFromArray(new Integer[] { 1 }, 2))
				.isNotEqualTo(Polynom.createPolyFromArray(new Integer[] { 1, 0 }, 2));

		assertThat(Polynom.createPolyFromArray(new Integer[] { 1, 1 }, 2))
				.isEqualTo(Polynom.createPolyFromArray(new Integer[] { 0, 1, 1 }, 2));

		assertThat(Polynom.createPolyFromArray(new Integer[] { 1, 0 }, 2))
				.isNotEqualTo(Polynom.createPolyFromArray(new Integer[] { 0 }, 2));
		assertThat(Polynom.createPolyFromArray(new Integer[] { 1 }, 2))
				.isNotEqualTo(
						Polynom.createPolyFromArray(new Integer[] { 0 }, 2));
		
		assertThat(Polynom.createPolyFromArray(new Integer[] { 0 }, 2))
				.isNotEqualTo(
						Polynom.createPolyFromArray(new Integer[] { 1 }, 2));
		
		assertThat(Polynom.createPolyFromArray(new Integer[] { 1 }, 2))
				.isNotEqualTo(
						Polynom.createPolyFromArray(new Integer[] { 1 }, 3));
		
		assertThat(Polynom.createPolyFromArray(new Integer[] { 0 }, 2))
				.isEqualTo(
						Polynom.createPolyFromArray(new Integer[] { 0 , 0 }, 2));

	}

	@Test
	public void testDivPolynom() {
		
		assertThat(Polynom.createPolyFromArray(new Integer[] { 1, 2, 4, 1 }, 5)
				.calculateDividePolynomRest(Polynom.createPolyFromArray(new Integer[] { 2, 0, 1 }, 5)))
						.isEqualTo(Polynom.createPolyFromArray(new Integer[] { 1, 0 }, 5));
		
		assertThat(Polynom.createPolyFromArray(new Integer[] { 1, 0, 0, 1 }, 3)
				.calculateDividePolynomRest(Polynom.createPolyFromArray(new Integer[] { 1, 1 }, 3)))
						.isEqualTo(Polynom.createPolyFromArray(new Integer[] { 0 }, 3));
		
		assertThat(Polynom.createPolyFromArray(new Integer[] { 2, 0, 1 }, 3)
				.calculateDividePolynomRest(Polynom.createPolyFromArray(new Integer[] { 1, 0, 1 }, 3)))
						.isEqualTo(Polynom.createPolyFromArray(new Integer[] { 2 }, 3));

		assertThat(Polynom.createPolyFromArray(new Integer[] { 1, 1, 1, 1 }, 2)
				.calculateDividePolynomRest(Polynom.createPolyFromArray(new Integer[] { 1, 0, 1, 1 }, 2)))
						.isEqualTo(Polynom.createPolyFromArray(new Integer[] { 1, 0, 0 }, 2));

		assertThat(Polynom.createPolyFromArray(new Integer[] { 1, 0, 0 }, 2)
				.calculateDividePolynomRest(Polynom.createPolyFromArray(new Integer[] { 1, 1, 1 }, 2)))
						.isEqualTo(Polynom.createPolyFromArray(new Integer[] { 1, 1 }, 2));

		assertThat(Polynom.createPolyFromArray(new Integer[] { 1, 0, 1 }, 2)
				.calculateDividePolynomRest(Polynom.createPolyFromArray(new Integer[] { 1, 1, 1 }, 2)))
						.isEqualTo(Polynom.createPolyFromArray(new Integer[] { 1, 0 }, 2));

		assertThat(Polynom.createPolyFromArray(new Integer[] { 1, 1 }, 2)
				.calculateDividePolynomRest(Polynom.createPolyFromArray(new Integer[] { 1, 1, 1 }, 2)))
						.isEqualTo(Polynom.createPolyFromArray(new Integer[] { 1, 1 }, 2));
		
		assertThat(Polynom.createPolyFromArray(new Integer[] { 1, 1 , 0 }, 2)
				.calculateDividePolynomRest(Polynom.createPolyFromArray(new Integer[] { 1, 1 }, 2)))
						.isEqualTo(Polynom.createPolyFromArray(new Integer[] { 0 }, 2));
		
		assertThat(Polynom.createPolyFromArray(new Integer[] { 1, 1 , 1 }, 3)
				.calculateDividePolynomRest(Polynom.createPolyFromArray(new Integer[] { 1, 2 }, 3)))
						.isEqualTo(Polynom.createPolyFromArray(new Integer[] { 0 }, 3));

	}

	@Test
	public void testNextPoly() {
		Polynom poly = Polynom.createPolyFromArray(new Integer[] { 0, 0, 0 }, 2);
		poly.nextPoly();
		assertThat(poly).isEqualTo(Polynom.createPolyFromArray(new Integer[] { 0, 0, 1 }, 2));
		poly.nextPoly();
		assertThat(poly).isEqualTo(Polynom.createPolyFromArray(new Integer[] { 0, 1, 0 }, 2));
		poly.nextPoly();
		assertThat(poly).isEqualTo(Polynom.createPolyFromArray(new Integer[] { 0, 1, 1 }, 2));
		poly.nextPoly();
		assertThat(poly).isEqualTo(Polynom.createPolyFromArray(new Integer[] { 1, 0, 0 }, 2));
		poly.nextPoly();
		assertThat(poly).isEqualTo(Polynom.createPolyFromArray(new Integer[] { 1, 0, 1 }, 2));
		poly.nextPoly();
		assertThat(poly).isEqualTo(Polynom.createPolyFromArray(new Integer[] { 1, 1, 0 }, 2));
		poly.nextPoly();
		assertThat(poly).isEqualTo(Polynom.createPolyFromArray(new Integer[] { 1, 1, 1 }, 2));

		poly.nextPoly();
		assertThat(poly).isEqualTo(Polynom.createPolyFromArray(new Integer[] { 0, 0, 0 }, 2));
	}

	@Test
	public void testClone() {
		Polynom createPolyFromArray = Polynom.createPolyFromArray(new Integer[] { 0, 0, 0 }, 2);
		Polynom createPolynome = Polynom.createPolynome(createPolyFromArray.nextPoly());
		assertThat(createPolyFromArray).isEqualTo(createPolynome);

		Polynom createPolynome2 = Polynom.createPolynome(createPolyFromArray.nextPoly());
		assertThat(createPolynome2).isEqualTo(createPolyFromArray);
		assertThat(createPolynome).isNotEqualTo(createPolynome2);

	}

	private static List<Polynom> getAllPolynomP2N2() {
		List<Polynom> polys = new ArrayList<Polynom>();
		polys.add(Polynom.createPolyFromArray(new Integer[] { 0, 0 }, 2));
		polys.add(Polynom.createPolyFromArray(new Integer[] { 0, 1 }, 2));
		polys.add(Polynom.createPolyFromArray(new Integer[] { 1, 0 }, 2));
		polys.add(Polynom.createPolyFromArray(new Integer[] { 1, 1 }, 2));
		return polys;
	}

	private static List<Polynom> getGeneratingPolynomP2N2() {
		return Arrays.asList(Polynom.createPolyFromArray(new Integer[] { 1, 1, 1 }, 2));
	}

	private static List<Polynom> getGeneratingPolynomP2N3() {
		List<Polynom> polys = new ArrayList<Polynom>();
		polys.add(Polynom.createPolyFromArray(new Integer[] { 1, 0, 1, 1 }, 2));
		polys.add(Polynom.createPolyFromArray(new Integer[] { 1, 1, 0, 1 }, 2));
		return polys;
	}

	private static List<Polynom> getGeneratingPolynomP3N3() {
		List<Polynom> polys = new ArrayList<Polynom>();
		polys.add(Polynom.createPolyFromArray(new Integer[] { 1, 0, 2, 1 }, 3));
		polys.add(Polynom.createPolyFromArray(new Integer[] { 1, 0, 2, 2 }, 3));
		polys.add(Polynom.createPolyFromArray(new Integer[] { 1, 1, 0, 2 }, 3));
		polys.add(Polynom.createPolyFromArray(new Integer[] { 1, 1, 1, 2 }, 3));
		polys.add(Polynom.createPolyFromArray(new Integer[] { 1, 1, 2, 1 }, 3));
		polys.add(Polynom.createPolyFromArray(new Integer[] { 1, 2, 0, 1 }, 3));
		polys.add(Polynom.createPolyFromArray(new Integer[] { 1, 2, 1, 1 }, 3));
		polys.add(Polynom.createPolyFromArray(new Integer[] { 1, 2, 2, 2 }, 3));
		
		return polys;
	}

	private static List<Polynom> getAllPolynomP2N3() {
		List<Polynom> polys = new ArrayList<Polynom>();
		polys.add(Polynom.createPolyFromArray(new Integer[] { 0, 0, 0 }, 2));
		polys.add(Polynom.createPolyFromArray(new Integer[] { 0, 0, 1 }, 2));
		polys.add(Polynom.createPolyFromArray(new Integer[] { 0, 1, 0 }, 2));
		polys.add(Polynom.createPolyFromArray(new Integer[] { 0, 1, 1 }, 2));
		polys.add(Polynom.createPolyFromArray(new Integer[] { 1, 0, 0 }, 2));
		polys.add(Polynom.createPolyFromArray(new Integer[] { 1, 0, 1 }, 2));
		polys.add(Polynom.createPolyFromArray(new Integer[] { 1, 1, 0 }, 2));
		polys.add(Polynom.createPolyFromArray(new Integer[] { 1, 1, 1 }, 2));
		return polys;
	}

	@Test
	public void testGenericPolysIrreduzible() {
		int p=3,n=3;
		List<Polynom> generatingPolynomes = Polynom.createGeneratingPolynomes(p, n);
		List<Polynom> allPolynomes = Polynom.createAllPolynomes(p, n);
		for (int i = 0; i < p; i++) {
			allPolynomes.remove(0);
		}
		for (Polynom allpolynom : allPolynomes) {
			for (Polynom generating : generatingPolynomes) {
				if (generating.calculateDividePolynomRest(allpolynom).equals(Polynom.createPolyFromArray(new Integer[] { 0 }, p))) {
					fail(generating + " is a reduzible Polymon with " + allpolynom);
				}
			}
		}

	}
}
