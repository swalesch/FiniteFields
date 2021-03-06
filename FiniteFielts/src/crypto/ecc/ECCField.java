package crypto.ecc;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import crypto.conductor.tabs.InteractionTabECC;

public class ECCField {

	public static List<Dictionary<BigInteger, List<BigInteger>>> _ECC_Curve_Punktliste = new ArrayList<Dictionary<BigInteger, List<BigInteger>>>();

	/**
	 * Gets index of Entry within the List which has a matching keyValue to
	 * given argument
	 * 
	 * @param keyValue
	 *            Value of matching entry.key
	 * @return Index of matching entry
	 */
	public static int indexOf(BigInteger keyValue) {
		for (int i = 0; i < _ECC_Curve_Punktliste.size(); i++) {
			if (_ECC_Curve_Punktliste.get(i).getKey().compareTo(keyValue) == 0)
				return i;
		}
		return -1;
	}

	/**
	 * Returns the BigIntegerList at given index
	 * 
	 * @param index
	 * @return
	 */
	public static List<BigInteger> getValue(int index) {
		return _ECC_Curve_Punktliste.get(index).getValue();
	}

	/**
	 * Specifies if given key is present or not
	 * 
	 * @param key
	 * @return
	 */
	public static boolean containsKey(BigInteger key) {
		return (indexOf(key) != -1);
	}

	/**
	 * Returns the BigIntegerList-Value to given Key
	 * 
	 * @param key
	 * @return
	 */
	public static List<BigInteger> getValueToKey(BigInteger key) {
		if (indexOf(key) == -1) {
			return null;
		}
		else {
			return getValue(indexOf(key));
		}

	}

	/**
	 * Puts an entry in the list, if not already present
	 * 
	 * @param key
	 * @param values
	 */
	public static void putIfAbsent(BigInteger key, List<BigInteger> values) {
		if (!containsKey(key)) {
			_ECC_Curve_Punktliste.add(new Dictionary<BigInteger, List<BigInteger>>(key, values));
		}
	}

	/**
	 * Gets the index of the entry in the list, which has the next higher
	 * keyValue
	 * 
	 * @param key
	 * @return
	 */
	public static int getNextKeyIndex(BigInteger key) {
		// return 0, when empty
		if (_ECC_Curve_Punktliste.size() == 0) {
			return 0;
		}

		// return lastIndex, when key > lastKey (all keys are smaller then new
		// key)
		if (_ECC_Curve_Punktliste.get(_ECC_Curve_Punktliste.size() - 1).getKey().compareTo(key) >= 0) {
			return -1;
		}

		BigInteger nextKey = key;
		while (indexOf(nextKey) == -1) {
			nextKey = nextKey.add(BigInteger.ONE);
		}
		return indexOf(nextKey);
	}

	/**
	 * Returns the inverse element of given value within the actual field
	 * 
	 * @param Value
	 * @param Koerper
	 * @return
	 */
	public static BigInteger GetInverseValue(BigInteger Value, BigInteger Koerper) {
		while (Value.compareTo(BigInteger.ZERO) < 0)
			Value = Value.add(Koerper);

		for (BigInteger i = BigInteger.ONE; i.compareTo(Koerper) < 0; i = i.add(BigInteger.ONE)) {
			if (i.multiply(Value).mod(Koerper).compareTo(BigInteger.ONE) == 0)
				return i;
		}
		return BigInteger.ZERO;
	}

	/**
	 * Resets all previously calculated values of the field.
	 */
	public static void resetECCField() {
		_ECC_Curve_Punktliste.clear();
	}

	/**
	 * Tries to find new x- and y-value of generator point with minimal
	 * difference to old values for new ECC-Field
	 */
	public static void recalculateGeneratorPoint() {
		InteractionTabECC.modelCurvePointX.setValue(Configuration._ellipticCurvePointX);
	}
}
