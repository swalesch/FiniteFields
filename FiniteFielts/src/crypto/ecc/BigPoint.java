package crypto.ecc;

import java.math.BigInteger;

public class BigPoint {
	public BigInteger X, Y;

	public BigPoint(BigInteger X, BigInteger Y) {
		this.X = X;
		this.Y = Y;
	}

	public static boolean equals(BigPoint left, BigPoint right) {
		return ((left.X.compareTo(right.X) == 0) && (left.Y.abs().compareTo(right.Y.abs())) == 0);
	}

	public String ToString() {
		return String.format("({0}, {1})", X, Y);
	}

	public void AddPoint(BigPoint PAdd) {
		if (this == PAdd.GetNegativePoint())
			throw new IllegalArgumentException("Für Punktaddition von P und Q muss P != -Q sein!");

		BigInteger s, xR, yR;

		s = Y.subtract(PAdd.Y).divide(X.subtract(PAdd.X)).mod(Configuration._ellipticCurveParamP);
		xR = s.modPow(new BigInteger("2"), Configuration._ellipticCurveParamP).subtract(X).subtract(PAdd.X).mod(Configuration._ellipticCurveParamP);
		yR = Y.negate().add(s.multiply(X.subtract(xR))).mod(Configuration._ellipticCurveParamP);

		X = xR;
		Y = yR;
	}

	public static BigPoint AddPoint(BigPoint left, BigPoint right) {
		if (left == right.GetNegativePoint())
			throw new IllegalArgumentException("Für Punktaddition von P und Q muss P != -Q sein!");

		BigInteger s = BigInteger.ZERO, xR, yR;

		// s = (Y_P - Y_Q) / (X_P - X_Q) mod p = (Y_P - Y_Q) * (X_P - X_Q)^-1
		// mod p

		s = left.Y.subtract(right.Y).multiply(ECCField.GetInverseValue(left.X.subtract(right.X), Configuration._ellipticCurveParamP)).mod(Configuration._ellipticCurveParamP);

		// -> Berechnungen aufgedröselt:
		// BigInteger s1, s2, s3, s4, s5;
		// s1 = BigInteger.Subtract(left.Y, right.Y);
		// s2 = BigInteger.Subtract(left.X, right.X);
		// s3 = GetInverseValue(s2, Configuration._ellipticCurveParamP);
		// s4 = BigInteger.Multiply(s1, s3);
		// s5 = BigInteger.ModPow(s4, 1, Configuration._ellipticCurveParamP);
		// s = s5;

		// Normalisieren
		if (s.compareTo(BigInteger.ZERO) < 0)
			s = s.add(Configuration._ellipticCurveParamP);

		xR = s.modPow(new BigInteger("2"), Configuration._ellipticCurveParamP).subtract(left.X).subtract(right.X).mod(Configuration._ellipticCurveParamP);

		// Normalisieren
		if (xR.compareTo(BigInteger.ZERO) < 0)
			xR = xR.add(Configuration._ellipticCurveParamP);

		yR = left.Y.negate().add(s.multiply(left.X.subtract(xR))).mod(Configuration._ellipticCurveParamP);

		// Normalisieren
		if (yR.compareTo(BigInteger.ZERO) < 0)
			yR = yR.add(Configuration._ellipticCurveParamP);

		return new BigPoint(xR, yR);
	}

	public BigPoint GetNegativePoint() {
		return new BigPoint(X, Y.negate());
	}

	public BigPoint Multiply(BigInteger Factor) {
		// Punkt mit sich selbst addieren

		if (Factor.compareTo(BigInteger.ONE) == 0)
			return this;

		if (Configuration._ellipticCurvePointY.compareTo(BigInteger.ZERO) == 0)
			throw new IllegalArgumentException("Der Y-Wert des Generatorpunktes darf für eine Punktverdopplung nicht 0 sein!");

		BigInteger s, xR, yR;
		// ((3x^2 + a) * 2y_p^-1 mod p
		s = X.pow(2).multiply(new BigInteger("3")).add(Configuration._ellipticCurveParamA).multiply(ECCField.GetInverseValue(Y.multiply(new BigInteger("2")), Configuration._ellipticCurveParamP)).mod(Configuration._ellipticCurveParamP);

		// Normalisieren
		if (s.compareTo(BigInteger.ZERO) < 0)
			s = s.add(Configuration._ellipticCurveParamP);

		xR = s.modPow(new BigInteger("2"), Configuration._ellipticCurveParamP).subtract(X.multiply(new BigInteger("2"))).mod(Configuration._ellipticCurveParamP);

		// Normalisieren
		if (xR.compareTo(BigInteger.ZERO) < 0)
			xR = xR.add(Configuration._ellipticCurveParamP);

		yR = Y.negate().add(s.multiply(X.subtract(xR))).mod(Configuration._ellipticCurveParamP);

		// Normalisieren
		if (yR.compareTo(BigInteger.ZERO) < 0)
			yR = yR.add(Configuration._ellipticCurveParamP);

		Factor = Factor.subtract(BigInteger.ONE);

		BigPoint res = new BigPoint(xR, yR);

		// 3P = 2P + P
		// x * P = 2P + P + P + ... + P

		while (Factor.compareTo(BigInteger.ONE) > 0) {
			res = AddPoint(this, res);
			Factor = Factor.subtract(BigInteger.ONE);
		}
		return res;
	}
}