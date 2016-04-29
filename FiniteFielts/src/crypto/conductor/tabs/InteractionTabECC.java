package crypto.conductor.tabs;

import java.math.BigInteger;

import javax.swing.SpinnerNumberModel;

import crypto.ecc.Configuration;
import crypto.ecc.ECCField;
import crypto.ecc.ECCFieldFunctions;

/**
 * Provides functionalities for GUI-Interaction in ECC-Tab
 * 
 * @author Marcus Schilling
 */
public class InteractionTabECC {

	public static SpinnerNumberModel modelCurveParamA = new SpinnerNumberModel() {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public void setValue(Object value) {
			System.out.println("ModelCurveParamA.setValue " + value.toString());
			BigInteger newVal = new BigInteger(value.toString());

			if (newVal.compareTo(Configuration._ellipticCurveParamA) == 0) {
				super.setValue(newVal);
			}
			else {

				if (newVal.compareTo(BigInteger.ONE) < 0) {
					newVal = BigInteger.ONE;
					value = 1;
				}

				Configuration._ellipticCurveParamA = newVal;
				ECCField.resetECCField();
				ECCField.recalculateGeneratorPoint();
				GeneratingECCTab.updateCurveEquationInGUI();
				GeneratingECCTab.SetSpinnerValues();
			}
		}
	};

	public static SpinnerNumberModel modelCurveParamB = new SpinnerNumberModel() {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public void setValue(Object value) {
			System.out.println("ModelCurveParamB.setValue " + value.toString());
			BigInteger newVal = new BigInteger(value.toString());

			if (newVal.compareTo(Configuration._ellipticCurveParamB) == 0) {
				super.setValue(newVal);
			}
			else {

				if (newVal.compareTo(BigInteger.ZERO) < 0) {
					value = 0;
					newVal = BigInteger.ZERO;
				}

				Configuration._ellipticCurveParamB = newVal;
				ECCField.resetECCField();
				ECCField.recalculateGeneratorPoint();
				GeneratingECCTab.updateCurveEquationInGUI();
				GeneratingECCTab.SetSpinnerValues();
			}
		}
	};

	public static SpinnerNumberModel modelCurvePointX = new SpinnerNumberModel() {

		/**
		 * Compiler was complaining, so I've added this
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public void setValue(Object value) {
			System.out.println("ModelCurvePointX.setValue " + value.toString());
			BigInteger oldValue = (BigInteger) getValue();
			BigInteger newValue = new BigInteger(value.toString());

			if (oldValue.equals(newValue) && ECCField.containsKey(oldValue)) {
				super.setValue(value);
				return;
			}

			boolean newValueIsGreaterThanBefore = false;
			if (oldValue.compareTo(newValue) <= 0) {
				newValueIsGreaterThanBefore = true;
			}

			Configuration._ellipticCurvePointX = newValue;

			ECCFieldFunctions.getGeneratorPointX(newValueIsGreaterThanBefore, newValue);
			GeneratingECCTab.SetSpinnerValues();
		}

		@Override
		public Object getValue() {
			System.out.println("ModelCurvePointX.getValue");
			return Configuration._ellipticCurvePointX;
		}

		@Override
		/**
		 * Gets the previous possible x-Value that has corresponding y-values fulfilling the curve equation
		 */
		public Object getPreviousValue() {
			System.out.println("ModelCurvePointX.getPreviousValue");
			BigInteger newVal = Configuration._ellipticCurvePointX.subtract(BigInteger.ONE);

			// if newValue equals -1, it has to be set to the maxValue of the
			// equation
			if (newVal.compareTo(new BigInteger("-1")) == 0) {
				newVal = Configuration._ellipticCurveParamP.subtract(BigInteger.ONE);

				// Call for higher value
				newVal = ECCFieldFunctions.getGeneratorPointX(true, newVal);
			}
			else {
				// Call for lower value
				newVal = ECCFieldFunctions.getGeneratorPointX(false, newVal);
			}

			// don't override _ellipticCurvePointX here, the setValue method
			// will be invoked right after that!
			GeneratingECCTab.SetSpinnerValues();
			return newVal;
		}

		@Override
		/**
		 * Gets the next possible x-Value that has corresponding y-values fulfilling the curve equation
		 */
		public Object getNextValue() {
			System.out.println("ModelCurvePointX.getNextValue");
			BigInteger newVal = Configuration._ellipticCurvePointX.add(BigInteger.ONE);

			// if newValue is greater than maxValue, the Value has to be
			// realigned to FieldSize
			if (newVal.compareTo(Configuration._ellipticCurveParamP) >= 0) {
				newVal = newVal.mod(Configuration._ellipticCurveParamP);
			}

			// Call for greater Value
			newVal = ECCFieldFunctions.getGeneratorPointX(true, newVal);
			GeneratingECCTab.SetSpinnerValues();
			return newVal;
		}
	};
	public static SpinnerNumberModel modelCurvePointY = new SpinnerNumberModel() {

		/**
		 * Compiler was complaining, so I've added this
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public void setValue(Object value) {
			System.out.println("ModelCurvePointY.setValue " + value.toString());
			BigInteger newYVal = new BigInteger(value.toString());
			BigInteger oldYVal = new BigInteger(getValue().toString());
			if (oldYVal.equals(newYVal)) {
				super.setValue(value);
				return;
			}

			boolean newValueIsGreaterThanBefore;
			if (oldYVal.compareTo(newYVal) > 0) {
				newValueIsGreaterThanBefore = false;
			}
			else {
				newValueIsGreaterThanBefore = true;
			}

			Configuration._ellipticCurvePointY = newYVal;

			newYVal = ECCFieldFunctions.getGeneratorPointY(newValueIsGreaterThanBefore, Configuration._ellipticCurvePointY);

			Configuration._ellipticCurvePointY = newYVal;
			GeneratingECCTab.SetSpinnerValues();
		}

		@Override
		public Object getValue() {
			System.out.println("ModelCurvePointY.getValue");
			return Configuration._ellipticCurvePointY;
		}

		@Override
		public Object getPreviousValue() {
			System.out.println("ModelCurvePointY.getPreviousValue");
			BigInteger yVal = Configuration._ellipticCurvePointY.subtract(BigInteger.ONE);

			// Call for lower Value
			return ECCFieldFunctions.getGeneratorPointY(false, yVal);
		}

		@Override
		public Object getNextValue() {
			System.out.println("ModelCurvePointY.getNextValue");
			BigInteger yVal = Configuration._ellipticCurvePointY.add(BigInteger.ONE);

			// Call for higher Value
			return ECCFieldFunctions.getGeneratorPointY(true, yVal);
		}
	};

	public static SpinnerNumberModel modelFieldParamP = new SpinnerNumberModel() {

		/**
		 * Compiler is complaining , so I added this...
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public Object getValue() {
			System.out.println("ModelCurveParamP.getValue");
			return Configuration._ellipticCurveParamP;
		}

		@Override
		public void setValue(Object value) {
			System.out.println("ModelCurveParamP.setValue " + value.toString());
			BigInteger newVal = new BigInteger(value.toString());
			BigInteger oldVal = new BigInteger(getValue().toString());

			if (oldVal.equals(newVal)) {
				super.setValue(value);
				return;
			}

			// If Number isn't prime, get the next prime-Number
			if (!newVal.isProbablePrime(25)) {
				boolean newValueIsGreaterThanBefore;

				if (oldVal.compareTo(newVal) > 0) {
					newValueIsGreaterThanBefore = false;
				}
				else {
					newValueIsGreaterThanBefore = true;
				}

				Configuration._ellipticCurveParamP = newVal;

				if (newValueIsGreaterThanBefore) {
					newVal = new BigInteger(getNextValue().toString());
				}
				else {
					newVal = new BigInteger(getPreviousValue().toString());
				}
			}

			Configuration._ellipticCurveParamP = newVal;
			ECCField.resetECCField();
			ECCField.recalculateGeneratorPoint();
			GeneratingECCTab.updateCurveEquationInGUI();
		}

		@Override
		public Object getNextValue() {
			System.out.println("ModelCurveParamP.getNextValue");
			BigInteger nextVal = Configuration._ellipticCurveParamP.add(BigInteger.ONE);

			while (!nextVal.isProbablePrime(20)) {
				nextVal = nextVal.add(BigInteger.ONE);
			}

			return nextVal;
		}

		@Override
		public Object getPreviousValue() {
			System.out.println("ModelCurveParamP.getPreviousValue");
			if (new BigInteger(getValue().toString()).equals(new BigInteger("-2"))) {
				return null;
			}

			BigInteger nextVal = Configuration._ellipticCurveParamP.subtract(BigInteger.ONE);

			while (!nextVal.isProbablePrime(25))
				nextVal = nextVal.subtract(BigInteger.ONE);

			return nextVal;
		}
	};
}
