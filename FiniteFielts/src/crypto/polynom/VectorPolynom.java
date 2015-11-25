package crypto.polynom;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import com.google.common.base.Preconditions;

public class VectorPolynom extends AbstractList<VectorPair> {
	private List<VectorPair> _vector;
	private final int LENGHT;

	public static VectorPolynom createVectorPolynom(int lenght) {
		return new VectorPolynom(lenght);
	}

	public static VectorPolynom createVectorPolynomFromList(List<Integer> vector) {
		VectorPolynom vectorPolynom = new VectorPolynom(vector.size());
		vectorPolynom.forEach(ele -> ele.setValue(vector.get(ele.getIndex())));
		return vectorPolynom;
	}

	public static VectorPolynom createVectorPolynomFromArray(Integer[] vector) {
		VectorPolynom vectorPolynom = new VectorPolynom(vector.length);
		vectorPolynom.forEach(ele -> ele.setValue(vector[ele.getIndex()]));
		return vectorPolynom;
	}

	public VectorPolynom createInverted() {
		VectorPolynom invertedVectorPolynom = createVectorPolynom(LENGHT);
		_vector.forEach(ele -> invertedVectorPolynom.set(LENGHT - 1 - ele.getIndex(), ele.getValue()));
		return invertedVectorPolynom;
	}

	public void set(int index, int value) {
		Optional<VectorPair> vectorPair = getByIndex(index);
		if (vectorPair.isPresent()) {
			vectorPair.get().setValue(value);
		} else {
			throw new IndexOutOfBoundsException("Index not in Vector.");
		}
	}

	/**
	 * returns the max VP by Length, if equal this
	 */
	public VectorPolynom max(VectorPolynom vp) {
		return (LENGHT >= vp.LENGHT) ? this : vp;
	}

	/**
	 * returns the min VP by Length, if equal this
	 */
	public VectorPolynom min(VectorPolynom vp) {
		return (LENGHT <= vp.LENGHT) ? this : vp;
	}

	public int getDegree() {
		Optional<VectorPair> min = _vector.stream()

				.filter(ele -> ele.getValue() > 0)

				.min(new Comparator<VectorPair>() {

					@Override
					public int compare(VectorPair o1, VectorPair o2) {
						if (o1.getIndex() >= o2.getIndex()) {
							return 1;
						}
						return -1;
					}
				});

		if (min.isPresent()) {
			return LENGHT - 1 - min.get().getIndex();
		} else {
			return 0;
		}
	}

	/**
	 * returns the Value of the VectorPair element
	 */
	public Integer getValue(int index) {
		Optional<VectorPair> vectorPair = getByIndex(index);
		if (vectorPair.isPresent()) {
			return vectorPair.get().getValue();
		}
		return null;
	}

	/**
	 * returns the Value of the VectorPair element, if not found 0
	 */
	public Integer getValueOrZero(int index) {
		Integer value = getValue(index);
		return (value != null) ? value : 0;
	}

	/**
	 * returns the gap between this.size - poly.size will be positive
	 */
	public Integer getGap(VectorPolynom poly) {
		int gap = size() - poly.size();
		gap = (gap < 0) ? gap * -1 : gap;
		return gap;
	}

	/**
	 * will return the VectorPair for index or Null if index not exists
	 */
	@Override
	public VectorPair get(int index) {
		Optional<VectorPair> vectorPair = getByIndex(index);
		if (vectorPair.isPresent()) {
			return vectorPair.get();
		}
		return null;
	}

	@Override
	public int size() {
		return LENGHT;
	}

	@Override
	public void sort(Comparator<? super VectorPair> c) {
		_vector.sort(c);
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof VectorPolynom) {
			VectorPolynom vp = (VectorPolynom) o;
			if (LENGHT == vp.LENGHT) {
				return !_vector.stream()

						.filter(ele -> !ele.equals(vp.get(ele.getIndex())))

						.findFirst()

						.isPresent();
			}
		}
		return false;
	}

	@Override
	public String toString() {
		String vectors = "(";
		for (int i = 0; i < _vector.size(); i++) {
			vectors += (_vector.get(i) + (((i + 1) == _vector.size()) ? "" : ","));
		}
		vectors += ")";
		return vectors;
	}

	private void initVector() {
		for (int i = 0; i < LENGHT; i++) {
			_vector.add(VectorPair.createPair(i, 0));
		}
	}

	private Optional<VectorPair> getByIndex(int index) {
		return _vector.stream()

				.filter(ele -> ele.getIndex() == index)

				.findFirst();
	}

	private VectorPolynom(int lenght) {
		Preconditions.checkArgument(lenght > 0);
		LENGHT = lenght;
		_vector = new ArrayList<VectorPair>();
		initVector();
	}

}
