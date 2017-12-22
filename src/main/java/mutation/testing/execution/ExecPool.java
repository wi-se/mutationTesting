package mutation.testing.execution;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.maven.shared.invoker.InvocationResult;

public class ExecPool {
	private static ExecPool instance;
	private ExecutorService pool = Executors.newFixedThreadPool(2);

	private ExecPool() {

	}

	public static ExecPool getInstance() {
		if (instance == null) {
			instance = new ExecPool();
		}

		return instance;
	}

	public void exec(Callable<InvocationResult> callable) {
		pool.submit(callable);
	}

}
