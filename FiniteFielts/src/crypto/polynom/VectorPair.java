package crypto.polynom;

import com.google.common.base.Preconditions;

public class VectorPair {
	private final int INDEX;
	private int _value;

	public static VectorPair createPair(int index, int value) {
		return new VectorPair(index, value);
	}

	public static VectorPair createPair(VectorPair vp) {
		return new VectorPair(vp);
	}

	public static VectorPair createEmpty() {
		return new VectorPair();
	}

	public int getIndex() {
		return INDEX;
	}

	public int getValue() {
		return _value;
	}

	public void setValue(int value) {
		_value = value;
	}

	/**
	 * return max VP by Index, if equal it returns this.
	 */
	public VectorPair max(VectorPair vp) {
		return (this.getIndex() >= vp.getIndex()) ? this : vp;
	}

	/**
	 * return mim VP by Index, if equal it returns this.
	 */
	public VectorPair min(VectorPair vp) {
		return (this.getIndex() <= vp.getIndex()) ? this : vp;
	}

	/**
	 * returns true if Values and Index are equal
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof VectorPair) {
			VectorPair pair = (VectorPair) obj;

			return _value == pair._value && INDEX == pair.INDEX;
		}
		return false;
	}

	@Override
	public String toString() {
		return "(" + INDEX + "," + _value + ")";
	}

	private VectorPair(VectorPair vp) {
		INDEX = vp.INDEX;
		_value = vp._value;
	}

	private VectorPair(int index, int value) {
		Preconditions.checkArgument(index >= 0, "Index musst be 0 or greater!");
		INDEX = index;
		_value = value;
	}

	private VectorPair() {
		INDEX = -1;
		_value = 0;
	}
}
