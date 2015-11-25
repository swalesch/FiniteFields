package crypto.polynom;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.Comparator;

import org.junit.Test;

public class VectorPolynomTest {

	@Test
	public void testCreateVector() {
		VectorPolynom vp1 = VectorPolynom.createVectorPolynom(2);
		assertThat(vp1.size()).isEqualTo(2);
		assertThat(vp1.get(0).getValue()).isEqualTo(0);
		assertThat(vp1.get(1).getValue()).isEqualTo(0);
		assertThat(vp1.get(2)).isEqualTo(null);

		assertThat(vp1.getValue(0)).isEqualTo(0);
		assertThat(vp1.getValue(1)).isEqualTo(0);
		assertThat(vp1.getValue(2)).isEqualTo(null);

		vp1.set(1, 1);
		assertThat(vp1.getValue(0)).isEqualTo(0);
		assertThat(vp1.getValue(1)).isEqualTo(1);
		assertThat(vp1.getValue(2)).isEqualTo(null);

		VectorPolynom vp2 = VectorPolynom.createVectorPolynomFromList(Arrays.asList(1, 2, 3));
		assertThat(vp2.size()).isEqualTo(3);
		assertThat(vp2.getValue(0)).isEqualTo(1);
		assertThat(vp2.getValue(1)).isEqualTo(2);
		assertThat(vp2.getValue(2)).isEqualTo(3);
		assertThat(vp2.getValue(3)).isEqualTo(null);

		VectorPolynom vp3 = VectorPolynom.createVectorPolynomFromArray(new Integer[] { 1, 2 });
		assertThat(vp3.size()).isEqualTo(2);
		assertThat(vp3.getValue(0)).isEqualTo(1);
		assertThat(vp3.getValue(1)).isEqualTo(2);
		assertThat(vp3.getValue(2)).isEqualTo(null);

		VectorPolynom inverted = vp3.createInverted();
		assertThat(inverted.size()).isEqualTo(2);
		assertThat(inverted.getValue(0)).isEqualTo(2);
		assertThat(inverted.getValue(1)).isEqualTo(1);
		assertThat(inverted.getValue(2)).isEqualTo(null);

		VectorPolynom vp4 = VectorPolynom.createVectorPolynom(2);
		vp4.set(1, 1);
		VectorPolynom inverted2 = vp4.createInverted();
		assertThat(inverted2.size()).isEqualTo(2);
		assertThat(inverted2.getValue(0)).isEqualTo(1);
		assertThat(inverted2.getValue(1)).isEqualTo(0);
		assertThat(inverted2.getValue(2)).isEqualTo(null);
	}

	@Test
	public void testMaxMin() {
		VectorPolynom vp1 = VectorPolynom.createVectorPolynom(2);

		VectorPolynom vp2 = VectorPolynom.createVectorPolynomFromList(Arrays.asList(1, 2, 3));

		VectorPolynom vp3 = VectorPolynom.createVectorPolynomFromArray(new Integer[] { 1, 2 });

		assertThat(vp1.max(vp2)).isEqualTo(vp2);
		assertThat(vp2.max(vp1)).isEqualTo(vp2);

		assertThat(vp1.min(vp2)).isEqualTo(vp1);
		assertThat(vp2.min(vp1)).isEqualTo(vp1);

		assertThat(vp1.max(vp3)).isEqualTo(vp1);
		assertThat(vp3.max(vp1)).isEqualTo(vp3);

		assertThat(vp1.min(vp3)).isEqualTo(vp1);
		assertThat(vp3.min(vp1)).isEqualTo(vp3);
	}

	@Test
	public void testEqual() {
		assertThat(VectorPolynom.createVectorPolynom(2))
				.isEqualTo(VectorPolynom.createVectorPolynomFromArray(new Integer[] { 0, 0 }));
		assertThat(VectorPolynom.createVectorPolynom(2))
				.isNotEqualTo(VectorPolynom.createVectorPolynomFromArray(new Integer[] { 1, 2 }));

		assertThat(VectorPolynom.createVectorPolynomFromArray(new Integer[] { 1, 3 }))
				.isEqualTo(VectorPolynom.createVectorPolynomFromList(Arrays.asList(1, 3)));
		assertThat(VectorPolynom.createVectorPolynomFromArray(new Integer[] { 1, 3 }))
				.isNotEqualTo(VectorPolynom.createVectorPolynomFromList(Arrays.asList(1, 2, 3)));

	}

	@Test
	public void testDegree() {
		assertThat(VectorPolynom.createVectorPolynomFromArray(new Integer[] { 1, 1 }).getDegree()).isEqualTo(1);
		assertThat(VectorPolynom.createVectorPolynomFromArray(new Integer[] { 0, 1, 1 }).getDegree()).isEqualTo(1);
		assertThat(VectorPolynom.createVectorPolynomFromArray(new Integer[] { 1, 0, 0 }).getDegree()).isEqualTo(2);
		assertThat(VectorPolynom.createVectorPolynomFromArray(new Integer[] { 0, 0, 1 }).getDegree()).isEqualTo(0);
	}

	@Test
	public void testSortObsolet() {
		VectorPolynom vp1 = VectorPolynom.createVectorPolynom(10);

		vp1.set(5, 4);

		assertThat(vp1.get(0).getIndex()).isEqualTo(0);
		assertThat(vp1.get(1).getIndex()).isEqualTo(1);
		Comparator<VectorPair> comparator = new Comparator<VectorPair>() {

			@Override
			public int compare(VectorPair o1, VectorPair o2) {
				return -1;
			}
		};
		vp1.sort(comparator);
		assertThat(vp1.get(0).getIndex()).isEqualTo(0);
		assertThat(vp1.get(1).getIndex()).isEqualTo(1);
		assertThat(vp1.getValue(5)).isEqualTo(4);
	}

}
