package crypto.polynom;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class VectorPairTest {

	@Test
	public void testCreatePairs() {

		VectorPair emptyPair = VectorPair.createEmpty();

		assertThat(emptyPair.getIndex()).isEqualTo(-1);
		assertThat(emptyPair.getValue()).isEqualTo(0);

		VectorPair vectorPair = VectorPair.createPair(4, 7);

		assertThat(vectorPair.getIndex()).isEqualTo(4);
		assertThat(vectorPair.getValue()).isEqualTo(7);

		vectorPair.setValue(2);
		assertThat(vectorPair.getValue()).isEqualTo(2);
	}

	@Test
	public void testMinMax() {
		VectorPair emptyPair = VectorPair.createEmpty();
		VectorPair vectorPair = VectorPair.createPair(4, 7);
		VectorPair vectorPair2 = VectorPair.createPair(3, 2);

		assertThat(emptyPair.max(vectorPair)).isEqualTo(vectorPair);
		assertThat(emptyPair.min(vectorPair)).isEqualTo(emptyPair);

		assertThat(vectorPair.max(emptyPair)).isEqualTo(vectorPair);
		assertThat(vectorPair.min(emptyPair)).isEqualTo(emptyPair);

		assertThat(vectorPair.max(vectorPair2)).isEqualTo(vectorPair);
		assertThat(vectorPair.min(vectorPair2)).isEqualTo(vectorPair2);

		assertThat(vectorPair2.max(vectorPair)).isEqualTo(vectorPair);
		assertThat(vectorPair2.min(vectorPair)).isEqualTo(vectorPair2);
	}

	@Test
	public void testEqual() {
		VectorPair emptyPair = VectorPair.createEmpty();
		VectorPair vectorPair = VectorPair.createPair(4, 7);
		VectorPair vectorPair2 = VectorPair.createPair(3, 0);
		VectorPair vectorPair3 = VectorPair.createPair(3, 0);

		assertThat(emptyPair).isNotEqualTo(vectorPair);
		assertThat(emptyPair).isNotEqualTo(vectorPair2);

		assertThat(vectorPair).isNotEqualTo(emptyPair);
		assertThat(vectorPair2).isNotEqualTo(emptyPair);

		assertThat(vectorPair).isNotEqualTo(vectorPair2);
		assertThat(vectorPair2).isNotEqualTo(vectorPair);

		assertThat(vectorPair2).isEqualTo(vectorPair3);
		assertThat(vectorPair3).isEqualTo(vectorPair2);
	}

	@Test
	public void testClone() {
		VectorPair vectorPair = VectorPair.createPair(4, 7);
		VectorPair createPair = VectorPair.createPair(vectorPair);
		createPair.setValue(1);
		assertThat(vectorPair.getValue()).isEqualTo(7);

	}
}
