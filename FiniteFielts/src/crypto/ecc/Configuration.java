package crypto.ecc;

import java.math.BigInteger;

public class Configuration {

	public static BigInteger _ellipticCurveParamA = BigInteger.ONE;
	public static BigInteger _ellipticCurveParamB = BigInteger.ZERO;
	public static BigInteger _ellipticCurveParamP = new BigInteger("23");
	public static BigInteger _ellipticCurvePointX = BigInteger.ZERO;
	public static BigInteger _ellipticCurvePointY = BigInteger.ZERO;

	public static BigInteger _keySecretA = BigInteger.ZERO;
	public static BigInteger _keySecretB = BigInteger.ZERO;
	public static BigPoint _keySharedA = new BigPoint(BigInteger.ZERO, BigInteger.ZERO);
	public static BigPoint _keySharedB = new BigPoint(BigInteger.ZERO, BigInteger.ZERO);
	public static BigInteger _keyShared = BigInteger.ZERO;
}
