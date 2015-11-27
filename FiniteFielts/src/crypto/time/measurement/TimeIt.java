package crypto.time.measurement;

import java.util.concurrent.Callable;
import java.util.function.Function;
import java.util.stream.IntStream;

public class TimeIt {

	public static <T> T printTime2(Callable<T> task) {
		long startTime = System.currentTimeMillis();
		T call = null;
		try {
			call = task.call();
			System.out.print((System.currentTimeMillis() - startTime) / 1000d
					+ "s");
		} catch (Exception e) {
			System.err.println("Faild");
			e.printStackTrace();
		}
		return call;
	}

	public static <T, R> Function<T, R> printTime(Function<T, R> task) {
		return (t) -> {
			long startTime = System.currentTimeMillis();
			R apply = task.apply(t);
			System.out.print((System.currentTimeMillis() - startTime) / 1000d
					+ "s");
			return apply;
		};
	}

	public static void main(String[] args) {

		Function<Integer, Integer> sum = (n) -> {
			return IntStream.range(0, n).reduce(0, (a, b) -> a + b);
		};

		printTime(sum).apply(10000);
	}
}
