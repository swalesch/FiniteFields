package crypto.polynom;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class PolynomTest {

	@Test
	public void testCreateAllPolynomes() {
		List<Polynom> allPolynomes = Polynom.getAllPolynomes(2, 2);

		assertThat(allPolynomes).isNotEmpty();
		assertThat(allPolynomes).containsOnlyElementsOf(getAllPolynomp2n2());

		allPolynomes = Polynom.getAllPolynomes(2, 3);

		assertThat(allPolynomes).isNotEmpty();
		assertThat(allPolynomes).containsOnlyElementsOf(getAllPolynomp2n3());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testNotPrim() {
		Polynom.getAllPolynomes(4, 2);
	}

	@Test
	public void testCreateGeneratingPolynoms() {
		List<Polynom> generatingPolynomes = Polynom.getGeneratingPolynomes(2, 2);
		assertThat(generatingPolynomes).isNotEmpty();
		assertThat(generatingPolynomes).containsOnlyElementsOf(getGeneratingPolynomp2n2());

		generatingPolynomes = Polynom.getGeneratingPolynomes(2, 3);
		assertThat(generatingPolynomes).isNotEmpty();
		assertThat(generatingPolynomes).containsOnlyElementsOf(getGeneratingPolynomp2n3());
	}

	@Test
	public void testNullpoints() {
		assertThat(createPolyFromArray(new int[] { 1, 1, 1 }).getAllNullPoints(2)).isEmpty();
		assertThat(createPolyFromArray(new int[] { 1, 1, 0 }).getAllNullPoints(2)).containsOnly(0, 1);
		assertThat(createPolyFromArray(new int[] { 1, 1, 1, 1 }).getAllNullPoints(2)).containsOnly(1);
		assertThat(createPolyFromArray(new int[] { 1, 1, 0 }).getAllNullPoints(3)).containsOnly(0, 2);
		assertThat(createPolyFromArray(new int[] { 1, 0, 1 }).getAllNullPoints(3)).isEmpty();
		assertThat(createPolyFromArray(new int[] { 1, 2, 1 }).getAllNullPoints(3)).containsOnly(2);
	}

	private static List<Polynom> getAllPolynomp2n2() {
		List<Polynom> polys = new ArrayList<Polynom>();
		polys.add(createPolyFromArray(new int[] { 0, 0 }));
		polys.add(createPolyFromArray(new int[] { 0, 1 }));
		polys.add(createPolyFromArray(new int[] { 1, 0 }));
		polys.add(createPolyFromArray(new int[] { 1, 1 }));
		return polys;
	}

	private static List<Polynom> getGeneratingPolynomp2n2() {
		return Arrays.asList(createPolyFromArray(new int[] { 1, 1, 1 }));
	}

	private static List<Polynom> getGeneratingPolynomp2n3() {
		List<Polynom> polys = new ArrayList<Polynom>();
		polys.add(createPolyFromArray(new int[] { 1, 0, 1, 1 }));
		polys.add(createPolyFromArray(new int[] { 1, 1, 0, 1 }));
		return polys;
	}

	private static List<Polynom> getAllPolynomp2n3() {
		List<Polynom> polys = new ArrayList<Polynom>();
		polys.add(createPolyFromArray(new int[] { 0, 0, 0 }));
		polys.add(createPolyFromArray(new int[] { 0, 0, 1 }));
		polys.add(createPolyFromArray(new int[] { 0, 1, 0 }));
		polys.add(createPolyFromArray(new int[] { 0, 1, 1 }));
		polys.add(createPolyFromArray(new int[] { 1, 0, 0 }));
		polys.add(createPolyFromArray(new int[] { 1, 0, 1 }));
		polys.add(createPolyFromArray(new int[] { 1, 1, 0 }));
		polys.add(createPolyFromArray(new int[] { 1, 1, 1 }));
		return polys;
	}

	private static Polynom createPolyFromArray(int[] vector) {
		Polynom polynom = new Polynom(0);
		polynom._polynom = vector;
		return polynom;
	}
}
