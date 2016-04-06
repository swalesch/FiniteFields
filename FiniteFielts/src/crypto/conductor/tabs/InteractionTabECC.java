package crypto.conductor.tabs;

import java.math.BigInteger;

import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeListener;

import crypto.ecc.Configuration;
import crypto.ecc.Functions;

/**
 * Provides functionalities for GUI-Interaction in ECC-Tab
 * 
 * @author Marcus Schilling
 */
public class InteractionTabECC {

	public static SpinnerNumberModel modelCurvePointX = new SpinnerNumberModel() {

		/**
		 * Compiler was complaining, so I've added this
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public void setValue(Object value) {
			if (((BigInteger) getValue()).equals(new BigInteger(value.toString())))
				return;

			boolean newValueIsGreaterThanBefore;
			BigInteger newValue = new BigInteger(value.toString());
			if (((BigInteger) getValue()).compareTo(newValue) > 0)
				newValueIsGreaterThanBefore = false;
			else {
				newValueIsGreaterThanBefore = true;
			}

			Configuration._ellipticCurvePointX = newValue;

			if (newValueIsGreaterThanBefore)
				newValue = new BigInteger(getNextValue().toString());
			else
				newValue = new BigInteger(getPreviousValue().toString());

			super.setValue(newValue);
		}

		@Override
		public Object getValue() {
			// TODO Auto-generated method stub
			return Configuration._ellipticCurvePointX;
		}

		@Override
		/**
		 * Gets the previous possible x-Value that has corresponding y-values fulfilling the curve equation
		 */
		public Object getPreviousValue() {

			BigInteger xVal = Configuration._ellipticCurvePointX.subtract(BigInteger.ONE);

			// if newValue equals -1, it has to be set to the maxValue of the
			// equation
			if (xVal.compareTo(new BigInteger("-1")) == 0) {
				xVal = Configuration._ellipticCurveParamP.subtract(new BigInteger("-1"));

				// NUDECC_Curve_Gen_X.Value = (decimal)xVal; constantly
				// decrement x, until it fulfills the curve equation
				return Functions.CurveGenChanged(true, false, xVal, BigInteger.ZERO);
			}
			else {
				// Call for lower value
				return Functions.CurveGenChanged(true, false, xVal, BigInteger.ZERO);
			}

			// don't override _ellipticCurvePointX here, the setValue method
			// will be invoked right after that!
		}

		@Override
		/**
		 * Gets the next possible x-Value that has corresponding y-values fulfilling the curve equation
		 */
		public Object getNextValue() {
			BigInteger xVal = Configuration._ellipticCurvePointX.add(BigInteger.ONE);

			// if newValue is greater than maxValue, the Value has to be
			// realigned to FieldSize
			if (xVal.compareTo(Configuration._ellipticCurveParamP) >= 0)
				xVal = xVal.mod(Configuration._ellipticCurveParamP);

			// Call for greater Value
			return Functions.CurveGenChanged(true, true, xVal, BigInteger.ZERO);
		}

		@Override
		public void addChangeListener(ChangeListener l) {
			// TODO Auto-generated method stub
		}
	};
	public static SpinnerNumberModel modelCurvePointY = new SpinnerNumberModel() {

		/**
		 * Compiler was complaining, so I've added this
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public void setValue(Object value) {
			if (((BigInteger) getValue()).equals(new BigInteger(value.toString())))
				return;

			boolean newValueIsGreaterThanBefore;
			BigInteger newValue = new BigInteger(value.toString());
			if (((BigInteger) getValue()).compareTo(newValue) > 0)
				newValueIsGreaterThanBefore = false;
			else {
				newValueIsGreaterThanBefore = true;
			}

			Configuration._ellipticCurvePointY = newValue;

			if (newValueIsGreaterThanBefore)
				newValue = new BigInteger(getNextValue().toString());
			else
				newValue = new BigInteger(getPreviousValue().toString());

			super.setValue(newValue);
		}

		@Override
		public void removeChangeListener(ChangeListener l) {
			// TODO Auto-generated method stub
		}

		@Override
		public Object getValue() {
			// TODO Auto-generated method stub
			return Configuration._ellipticCurvePointY;
		}

		@Override
		public Object getPreviousValue() {
			BigInteger yVal = Configuration._ellipticCurvePointY.subtract(BigInteger.ONE);

			// Call for lower Value
			return Functions.CurveGenChanged(false, false, Configuration._ellipticCurvePointX, yVal);
		}

		@Override
		public Object getNextValue() {
			BigInteger yVal = Configuration._ellipticCurvePointY.add(BigInteger.ONE);

			// Call for lower Value
			return Functions.CurveGenChanged(false, false, Configuration._ellipticCurvePointX, yVal);
		}

		@Override
		public void addChangeListener(ChangeListener l) {
			// TODO Auto-generated method stub

		}
	};

	public static SpinnerNumberModel modelFieldParamP = new SpinnerNumberModel() {

		/**
		 * Compiler is complaining , so I added this...
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public Object getValue() {
			return Configuration._ellipticCurveParamP;
		}

		@Override
		public void setValue(Object value) {

			if (((BigInteger) getValue()).equals(new BigInteger(value.toString())))
				return;

			boolean newValueIsGreaterThanBefore;
			BigInteger newValue = new BigInteger(value.toString());
			if (((BigInteger) getValue()).compareTo(newValue) > 0)
				newValueIsGreaterThanBefore = false;
			else {
				newValueIsGreaterThanBefore = true;
			}

			Configuration._ellipticCurveParamP = newValue;

			if (newValueIsGreaterThanBefore)
				newValue = new BigInteger(getNextValue().toString());
			else
				newValue = new BigInteger(getPreviousValue().toString());

			super.setValue(newValue);
		}

		@Override
		public Object getNextValue() {
			BigInteger nextVal = Configuration._ellipticCurveParamP.add(BigInteger.ONE);

			while (!nextVal.isProbablePrime(20))
				nextVal = nextVal.add(BigInteger.ONE);

			return nextVal;
		}

		@Override
		public Object getPreviousValue() {
			if (new BigInteger(getValue().toString()).equals(new BigInteger("-2")))
				return null;

			BigInteger nextVal = Configuration._ellipticCurveParamP.subtract(BigInteger.ONE);

			while (!nextVal.isProbablePrime(50))
				nextVal = nextVal.subtract(BigInteger.ONE);

			return nextVal;
		}

		@Override
		public Number getNumber() {
			return null;
		}
	};
}
