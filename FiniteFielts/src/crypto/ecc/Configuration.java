package crypto.ecc;

import java.math.BigInteger;
import java.util.AbstractMap;
import java.util.List;

public class Configuration {
	public static AbstractMap<BigInteger, List<BigInteger>> _ECC_Curve_Punktliste;

	public static BigInteger _ellipticCurveParamA = BigInteger.ONE;
	public static BigInteger _ellipticCurveParamB = BigInteger.ZERO;
	public static BigInteger _ellipticCurveParamP = new BigInteger("23");
	public static BigInteger _ellipticCurvePointX = BigInteger.ZERO;
	public static BigInteger _ellipticCurvePointY = BigInteger.ZERO;
}
