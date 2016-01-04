package crypto.field;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import crypto.polynom.Polynom;

public class FieldTest {
	@Test
	public void testAdd() {
		Polynom generatingPolynom = Polynom.createXGeneratingPolynome(2, 2, 1).get(0);
		Field field = Field.createField(generatingPolynom);

		assertThat(field.addPoints(Polynom.createPolyFromArray(new Integer[] { 0, 1 }, 2),
				Polynom.createPolyFromArray(new Integer[] { 1, 1 }, 2)))
						.isEqualTo(Polynom.createPolyFromArray(new Integer[] { 1, 0 }, 2));

		assertThat(field.addPoints(Polynom.createPolyFromArray(new Integer[] { 1, 1 }, 2),
				Polynom.createPolyFromArray(new Integer[] { 1, 1 }, 2)))
						.isEqualTo(Polynom.createPolyFromArray(new Integer[] { 0 }, 2));

		assertThat(field.addPoints(Polynom.createPolyFromArray(new Integer[] { 0, 0 }, 2),
				Polynom.createPolyFromArray(new Integer[] { 0, 0 }, 2)))
						.isEqualTo(Polynom.createPolyFromArray(new Integer[] { 0 }, 2));

		assertThat(field.addPoints(Polynom.createPolyFromArray(new Integer[] { 0, 0 }, 2),
				Polynom.createPolyFromArray(new Integer[] { 0, 1 }, 2)))
						.isEqualTo(Polynom.createPolyFromArray(new Integer[] { 1 }, 2));
	}

	@Test
	public void testMulti() {
		Polynom generatingPolynom = Polynom.createXGeneratingPolynome(2, 2, 1).get(0);
		Field field = Field.createField(generatingPolynom);

		assertThat(field.multiplyPoints(Polynom.createPolyFromArray(new Integer[] { 0, 1 }, 2),
				Polynom.createPolyFromArray(new Integer[] { 1, 1 }, 2)))
						.isEqualTo(Polynom.createPolyFromArray(new Integer[] { 1, 1 }, 2));

		assertThat(field.multiplyPoints(Polynom.createPolyFromArray(new Integer[] { 1, 1 }, 2),
				Polynom.createPolyFromArray(new Integer[] { 1, 1 }, 2)))
						.isEqualTo(Polynom.createPolyFromArray(new Integer[] { 1, 0 }, 2));

		assertThat(field.multiplyPoints(Polynom.createPolyFromArray(new Integer[] { 1, 0 }, 2),
				Polynom.createPolyFromArray(new Integer[] { 1, 0 }, 2)))
						.isEqualTo(Polynom.createPolyFromArray(new Integer[] { 1, 1 }, 2));

		assertThat(field.multiplyPoints(Polynom.createPolyFromArray(new Integer[] { 1, 0 }, 2),
				Polynom.createPolyFromArray(new Integer[] { 1, 1 }, 2)))
						.isEqualTo(Polynom.createPolyFromArray(new Integer[] { 1 }, 2));
	}

	@Test
	public void testIsPointInField() {
		Polynom generatingPolynom = Polynom.createXGeneratingPolynome(2, 2, 1).get(0);
		Field field = Field.createField(generatingPolynom);

		assertThat(field.isPointInField(Polynom.createPolyFromArray(new Integer[] { 0, 1 }, 2))).isTrue();
		assertThat(field.isPointInField(Polynom.createPolyFromArray(new Integer[] { 1, 1 }, 2))).isTrue();
		assertThat(field.isPointInField(Polynom.createPolyFromArray(new Integer[] { 0, 0 }, 2))).isTrue();
		assertThat(field.isPointInField(Polynom.createPolyFromArray(new Integer[] { 1, 0 }, 2))).isTrue();
		assertThat(field.isPointInField(Polynom.createPolyFromArray(new Integer[] { 0 }, 2))).isTrue();
		assertThat(field.isPointInField(Polynom.createPolyFromArray(new Integer[] { 1, 0, 1 }, 2))).isFalse();
		assertThat(field.isPointInField(Polynom.createPolyFromArray(new Integer[] { 0, 1 }, 3))).isFalse();
	}
	
	@Test
	public void tesTest(){
		Polynom generatingPolynom = Polynom.createXGeneratingPolynome(3, 3, 8).get(5);
		Field field = Field.createField(generatingPolynom);
		System.out.println("Generating: "+generatingPolynom+"\n");
		System.out.println(field.toString());
	}
}
