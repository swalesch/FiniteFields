/**
 * 
 */
package crypto.ecc;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Marcus Schilling
 */
public class Functions {

	/*
	 * public static void CurveGenChanged(boolean KeepX, boolean
	 * riseKeepingValue) { CurveGenChanged(KeepX, riseKeepingValue,
	 * Configuration._ellipticCurvePointX, Configuration._ellipticCurvePointY);
	 * }
	 */

	public static BigInteger CurveGenChanged(boolean keepX, boolean riseKeepingValue, BigInteger x, BigInteger y) {
		// y^2 mod p = x^3 + ax + b mod p
		// → print all values, which fulfills this equation

		// checks if actual curve can create Field (deprecated)
		// boolean curveCanFormGroup = true;

		// checks if element was already calculated once before
		boolean foundAssociation = false;

		try {
			for (int i = 0; i < eccField._ECC_Curve_Punktliste.size(); i++)
				if (eccField.containsKey(x)) {
					foundAssociation = true;
				}
		}
		catch (Exception e) {

		}

		if (!foundAssociation) {
			// do calculation, if not found:
			// if (curveCanFormGroup)
			// {
			// calculate all y-Values to given x-Value
			if (keepX) {
				BigInteger rightSide = x.pow(3).add(Configuration._ellipticCurveParamA.multiply(x)).add(Configuration._ellipticCurveParamB).mod(Configuration._ellipticCurveParamP);
				List<BigInteger> posPoints = new ArrayList<BigInteger>();
				List<BigInteger> negPoints = new ArrayList<BigInteger>();

				for (BigInteger i = BigInteger.ZERO; i.compareTo(Configuration._ellipticCurveParamP) < 0; i = i.add(BigInteger.ONE)) {
					BigInteger leftSide = i.modPow(new BigInteger("2"), Configuration._ellipticCurveParamP);
					if (leftSide.equals(rightSide)) {
						posPoints.add(i);
						// there's a y-value' for any y-value with y-value' =
						// -y-value!
						// obviously no need to add -0
						if (i.compareTo(BigInteger.ZERO) != 0)
							negPoints.add(i.negate());
					}
				}

				// checks if this function has called no recursive instances of
				// itself
				// if true, this function can later add the calculated values to
				// the List
				boolean lastInstance = true;

				if (posPoints.size() > 0) {
					// get first positive value and use as y-Value
					Configuration._ellipticCurvePointX = x;
					Configuration._ellipticCurvePointY = posPoints.get(0);
				}
				else {
					// recalculate for other x-Value, if there does not exist
					// any value
					if (riseKeepingValue)
						x = x.add(BigInteger.ONE);
					else
						x = x.subtract(BigInteger.ONE);

					// x has to be between 0 and _ellipticCurveParamP
					x = x.mod(Configuration._ellipticCurveParamP);

					lastInstance = false;

					try {
						CurveGenChanged(keepX, riseKeepingValue, x, y);
					}
					catch (StackOverflowError e) {
						// do nothing, works anyway...
					}
				}

				if (lastInstance) {
					/**
					 * contains all negative and positive values of y for this
					 * specific x-Value in ascending order
					 */
					List<BigInteger> allPoints = new ArrayList<BigInteger>();

					for (int i = negPoints.size() - 1; i >= 0; i--)
						allPoints.add(negPoints.get(i));

					for (int i = 0; i < posPoints.size(); i++)
						allPoints.add(posPoints.get(i));

					eccField.putIfAbsent(x, allPoints);
					return x;
				}
			}
			else {
				/**
				 * calculate all y-values for given x-Value, if there is
				 * currently no existing list
				 */
				boolean found = false;

				if (eccField.containsKey(x)) {
					found = true;
				}

				// if x-Value exists in the list, the next (higher or lower)
				// value can be extracted from the list
				if (found) {
					if (riseKeepingValue) {
						for (int i = 0; i < eccField.getValueToKey(x).size(); i++)
							if (eccField.getValueToKey(x).get(i).compareTo(y) > 0) {
								Configuration._ellipticCurvePointX = x;
								Configuration._ellipticCurvePointY = eccField.getValueToKey(x).get(i);
								return Configuration._ellipticCurvePointY;
							}
					}
					else {
						for (int i = eccField.getValueToKey(x).size() - 1; i >= 0; i--) {
							if (eccField.getValueToKey(x).get(i).compareTo(y) < 0) {
								Configuration._ellipticCurvePointX = x;
								Configuration._ellipticCurvePointY = eccField.getValueToKey(x).get(i);
								return Configuration._ellipticCurvePointY;
							}
						}
					}
				}
				else {
					try {
						// calculate y-Values to current x-Value first
						CurveGenChanged(true, true, x, y);

						// then try to get the next y-Value again
						CurveGenChanged(keepX, riseKeepingValue, x, y);
					}
					catch (StackOverflowError e) {
						// do nothing, works anyway
					}
				}
			}
			// }
			// else
			// MessageBox.Show("Die angegebene Kurvengleichung ist nicht geeignet, da sie nicht irreduzibel ist!");
		}
		else {
			// calculation already done for all points

			int index = 0;
			boolean found = false;

			if (riseKeepingValue) {
				for (int i = 0; i < eccField.getValueToKey(x).size(); i++) {
					if (eccField.getValueToKey(x).get(i).compareTo(Configuration._ellipticCurvePointY) > 0) {
						index = i;
						found = true;
						break;
					}
				}

				// if there is no number greater than the current value, take
				// the lowest possible number (has always index 0)
				if (!found)
					index = 0;
			}
			else {
				for (int i = eccField.getValueToKey(x).size() - 1; i >= 0; i--) {
					if (eccField.getValueToKey(x).get(i).compareTo(Configuration._ellipticCurvePointY) < 0) {
						index = i;
						found = true;
						break;
					}
				}

				// if there is no number lower than the current value, take the
				// highest possible number (has always the last index)
				if (!found)
					index = eccField.getValueToKey(x).size() - 1;
			}

			Configuration._ellipticCurvePointX = x;
			Configuration._ellipticCurvePointY = eccField.getValueToKey(x).get(index);
			if (keepX)
				return x;
			else
				return Configuration._ellipticCurvePointY;
		}
		return null;
	}
}
