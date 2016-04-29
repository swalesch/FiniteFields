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
public class ECCFieldFunctions {

	/*
	 * public static void CurveGenChanged(boolean KeepX, boolean
	 * riseKeepingValue) { CurveGenChanged(KeepX, riseKeepingValue,
	 * Configuration._ellipticCurvePointX, Configuration._ellipticCurvePointY);
	 * }
	 */

	public static BigInteger getGeneratorPointX(boolean risedXValue, BigInteger newXValue) {
		// y^2 mod p = x^3 + ax + b mod p
		// → print all values, which fulfills this equation

		// checks if actual curve can create Field (deprecated)
		// boolean curveCanFormGroup = true;

		// checks if element was already calculated once before
		boolean foundAssociation = false;

		try {
			for (int i = 0; i < ECCField._ECC_Curve_Punktliste.size(); i++)
				if (ECCField.containsKey(newXValue)) {
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
			BigInteger rightSide = newXValue.pow(3).add(Configuration._ellipticCurveParamA.multiply(newXValue)).add(Configuration._ellipticCurveParamB).mod(Configuration._ellipticCurveParamP);
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
				Configuration._ellipticCurvePointX = newXValue;
				Configuration._ellipticCurvePointY = posPoints.get(0);
			}
			else {
				// recalculate for other x-Value, if there does not exist
				// any value
				if (risedXValue)
					newXValue = newXValue.add(BigInteger.ONE);
				else
					newXValue = newXValue.subtract(BigInteger.ONE);

				// x has to be between 0 and _ellipticCurveParamP
				newXValue = newXValue.mod(Configuration._ellipticCurveParamP);

				lastInstance = false;

				try {
					getGeneratorPointX(risedXValue, newXValue);
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

				ECCField.putIfAbsent(newXValue, allPoints);
				return newXValue;
			}
		}
		else {
			// calculation already done for all points
			int index = 0;
			boolean found = false;

			if (risedXValue) {
				for (int i = 0; i < ECCField.getValueToKey(newXValue).size(); i++) {
					if (ECCField.getValueToKey(newXValue).get(i).compareTo(Configuration._ellipticCurvePointY) > 0) {
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
				for (int i = ECCField.getValueToKey(newXValue).size() - 1; i >= 0; i--) {
					if (ECCField.getValueToKey(newXValue).get(i).compareTo(Configuration._ellipticCurvePointY) < 0) {
						index = i;
						found = true;
						break;
					}
				}

				// if there is no number lower than the current value, take the
				// highest possible number (has always the last index)
				if (!found)
					index = ECCField.getValueToKey(newXValue).size() - 1;
			}

			Configuration._ellipticCurvePointX = newXValue;
			Configuration._ellipticCurvePointY = ECCField.getValueToKey(newXValue).get(index);
			return newXValue;
		}
		return null;
	}

	public static BigInteger getGeneratorPointY(boolean risedYValue, BigInteger newYValue) {
		/**
		 * calculate all y-values for given x-Value, if there is currently no
		 * existing list
		 */
		boolean found = false;

		if (ECCField.containsKey(Configuration._ellipticCurvePointX)) {
			found = true;
		}

		// if x-Value exists in the list, the next (higher or lower)
		// value can be extracted from the list
		if (found) {
			int fieldSize = ECCField.getValueToKey(Configuration._ellipticCurvePointX).size();
			if (risedYValue) {
				for (int i = 0; i < fieldSize; i++) {
					if (ECCField.getValueToKey(Configuration._ellipticCurvePointX).get(i).compareTo(newYValue) > 0) {
						Configuration._ellipticCurvePointY = ECCField.getValueToKey(Configuration._ellipticCurvePointX).get(i);
						return Configuration._ellipticCurvePointY;
					}
				}

				// no value greater than actual value found ->
				// return lowest value
				Configuration._ellipticCurvePointY = ECCField.getValueToKey(Configuration._ellipticCurvePointX).get(0);
				return Configuration._ellipticCurvePointY;
			}
			else {
				for (int i = fieldSize - 1; i >= 0; i--) {
					if (ECCField.getValueToKey(Configuration._ellipticCurvePointX).get(i).compareTo(newYValue) < 0) {
						Configuration._ellipticCurvePointY = ECCField.getValueToKey(Configuration._ellipticCurvePointX).get(i);
						return Configuration._ellipticCurvePointY;
					}
				}
				// no value lower than actual value found
				// -> return greatest value instead
				Configuration._ellipticCurvePointY = ECCField.getValueToKey(Configuration._ellipticCurvePointX).get(fieldSize - 1);
			}
			return Configuration._ellipticCurvePointY;
		}
		else {
			try {
				// calculate y-Values to current x-Value first
				getGeneratorPointX(true, Configuration._ellipticCurvePointX);

				// then try to get the next y-Value again
				getGeneratorPointY(risedYValue, newYValue);
			}
			catch (StackOverflowError e) {
				// do nothing, works anyway
			}
			return Configuration._ellipticCurvePointY;
		}
	}

	public static void generateKeys() {
		// WOANDERS hin verschieben!
		// delete old values
		/*
		 * TBECC_Key_Secret_A.Text = ""; TBECC_Key_Secret_B.Text = "";
		 * TBECC_Key_Shared_A.Text = ""; TBECC_Key_Shared_B.Text = "";
		 * TBECC_Key_Shared.Text = ""; this.Refresh();
		 */

		BigInteger keySecretA = BigInteger.ZERO, keySecretB = BigInteger.ZERO;
		BigPoint keySharedA, keySharedB;
		BigInteger keyShared = BigInteger.ZERO;
		BigPoint pointGen;

		// keySecretA = GetSecretKey();
		// TBECC_Key_Secret_A.Text = KeySecretA.ToString();
		// this.Refresh();

		// keySecretB = GetSecretKey();
		// TBECC_Key_Secret_B.Text = KeySecretB.ToString();
		// this.Refresh();

		// pointGen = new BigPoint((BigInteger) NUDECC_Curve_Gen_X.Value,
		// (BigInteger) NUDECC_Curve_Gen_Y.Value);

		// keySharedA = GetSharedKeyPoint(pointGen, keySecretA);
		// TBECC_Key_Shared_A.Text = KeySharedA.ToString();
		// this.Refresh();

		// keySharedB = GetSharedKeyPoint(pointGen, keySecretB);
		// TBECC_Key_Shared_B.Text = keySharedB.toString();
		// this.Refresh();

		boolean exceptionThrown = false;

		try {
			// keyShared = keySharedB.Multiply(keySecretA).X;
		}
		catch (IllegalArgumentException ex) {
			exceptionThrown = true;
			// BECC_Encrypt_Encrypt_Click(sender, e);
		}

		if (!exceptionThrown) {
			BigInteger keySymTest = BigInteger.ZERO;
			try {
				// keySymTest = keySharedB.Multiply(keySecretA).X;
			}
			catch (IllegalArgumentException ex) {
				exceptionThrown = true;
				// BECC_Encrypt_Encrypt_Click(sender, e);
			}

			if (!exceptionThrown && keyShared.equals(keySymTest)) {

				/*
				 * TBECC_Key_Secret_A.Text = KeySecretA.ToString();
				 * TBECC_Key_Secret_B.Text = KeySecretB.ToString();
				 * 
				 * TBECC_Key_Shared_A.Text = KeySharedA.ToString();
				 * TBECC_Key_Shared_B.Text = KeySharedB.ToString();
				 * 
				 * TBECC_Key_Shared.Text = KeySym.ToString();
				 */
				// MessageBox.Show(string.Format("Zur Kontrolle: PubKeyA * PrivKeyB = {0}",
				// KeySharedA.Multiply(KeySecretB).X));
			}
			else {
				// shared-Key wahrscheinlich nicht geeignet, noch einmal
				// versuchen
				// BECC_Keys_Calc_Click(sender, e);
			}
		}
	}
}
