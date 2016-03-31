/**
 * 
 */
package crypto.conductor.interaction;

import java.math.BigInteger;

import javax.swing.SpinnerModel;
import javax.swing.event.ChangeListener;

import crypto.ecc.Configuration;
import crypto.ecc.Functions;

/**
 * @author bluesky787
 *
 */
public class GUIInteraction {
	
	
	public static SpinnerModel modelCurvePointX = new SpinnerModel() {

        @Override
        public void setValue(Object value) {
            Configuration._ellipticCurvePointX = new BigInteger(value.toString());
        }

        @Override
        public void removeChangeListener(ChangeListener l) {
            // TODO Auto-generated method stub
        }

        @Override
        public Object getValue() {
            // TODO Auto-generated method stub
            return Configuration._ellipticCurvePointX;
        }

        @Override
        public Object getPreviousValue() {
        	
        	//Hier doch lieber die Funktion CurveGenChanged nutzen!
        	//Diese muss allerdings so angepasst werden, dass sie einen Rückgabewert hat...
        	
            BigInteger xVal = Configuration._ellipticCurvePointX.subtract(BigInteger.ONE);
            
            //if newValue equals -1, it has to be set to the maxValue of the equation
            if (xVal.compareTo(new BigInteger("-1")) == 0)
            {
                xVal = Configuration._ellipticCurveParamP.subtract(new BigInteger("-1"));
                
                //NUDECC_Curve_Gen_X.Value = (decimal)xVal;
                //constantly decrement x, until it fulfills the curve equation
                return Functions.CurveGenChanged(true, false, xVal, BigInteger.ZERO);  
            }
            else {
            		//Call for lower Value
                    return Functions.CurveGenChanged(true, false, xVal, BigInteger.ZERO);
            }
            
            //don't override _ellipticCurvePointX here, the setValue method will be invoked right after that!
        }

        @Override
        public Object getNextValue() {
        	BigInteger xVal = Configuration._ellipticCurvePointX.add(BigInteger.ONE);
            
        	//if newValue is greater than maxValue, the Value has to be realigned to FieldSize
            if (xVal.compareTo(Configuration._ellipticCurveParamP) >= 0)
                xVal = xVal.mod(Configuration._ellipticCurveParamP);
            
            //Call for greater Value
            return Functions.CurveGenChanged(true, true, xVal, BigInteger.ZERO);            
        }

        @Override
        public void addChangeListener(ChangeListener l) {
            // TODO Auto-generated method stub

        }
    };
    public static SpinnerModel modelCurvePointY = new SpinnerModel() {

        @Override
        public void setValue(Object value) {
            Configuration._ellipticCurvePointY = new BigInteger(value.toString());
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
            
            //Call for lower Value
            return Functions.CurveGenChanged(false, false, Configuration._ellipticCurvePointX, yVal);            
        }

        @Override
        public Object getNextValue() {
        	BigInteger yVal = Configuration._ellipticCurvePointY.add(BigInteger.ONE);
            
            //Call for lower Value
            return Functions.CurveGenChanged(false, false, Configuration._ellipticCurvePointX, yVal);            
        }

        @Override
        public void addChangeListener(ChangeListener l) {
            // TODO Auto-generated method stub

        }
    };
    public static SpinnerModel modelFieldParamP = new SpinnerModel(){

		@Override
		public void addChangeListener(ChangeListener l) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public Object getNextValue() {
			BigInteger nextVal = Configuration._ellipticCurveParamP.add(BigInteger.ONE);
			
			while(!nextVal.isProbablePrime(50))
				nextVal = nextVal.add(BigInteger.ONE);
			
			return nextVal;
		}

		@Override
		public Object getPreviousValue() {
			BigInteger nextVal = Configuration._ellipticCurveParamP.subtract(BigInteger.ONE);
			
			while(!nextVal.isProbablePrime(50))
				nextVal = nextVal.subtract(BigInteger.ONE);
			
			return nextVal;
		}

		@Override
		public Object getValue() {
			return Configuration._ellipticCurveParamP;
		}

		@Override
		public void removeChangeListener(ChangeListener l) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void setValue(Object value) {
			Configuration._ellipticCurveParamP = new BigInteger(value.toString());	
		}
	};
	
}
