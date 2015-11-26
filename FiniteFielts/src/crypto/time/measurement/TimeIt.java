package crypto.time.measurement;

import java.util.concurrent.Callable;

public class TimeIt<T> {

	public T printTime(Callable<T> task) {
		long startTime = System.currentTimeMillis();
		T call = null;
		try {
			call = task.call();
			System.out.print((System.currentTimeMillis() - startTime) / 1000d + "s");
		} catch (Exception e) {
			System.err.println("Faild");
			e.printStackTrace();
		}

		return call;
	}
}
