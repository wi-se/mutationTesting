package mutation.testing.execution;

import java.io.File;
import java.util.Arrays;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.maven.shared.invoker.DefaultInvocationRequest;
import org.apache.maven.shared.invoker.DefaultInvoker;
import org.apache.maven.shared.invoker.InvocationRequest;
import org.apache.maven.shared.invoker.InvocationResult;
import org.apache.maven.shared.invoker.Invoker;
import org.apache.maven.shared.invoker.MavenInvocationException;

import mutation.testing.exceptions.ApplicationInternalError;
import mutation.testing.helper.PropertyLoader;

public class MvnHelper {
	private static final Log LOG = LogFactory.getLog(MvnHelper.class);
	private Invoker invoker = new DefaultInvoker();

	public MvnHelper() {
		this.invoker.setMavenHome(new File(PropertyLoader.getInstance().getProps("maven.home")));
	}

	/**
	 * <p>
	 * <ul>
	 * <li><u>First args</u>: The pom location</li>
	 * <li><u>second args</u>: Command to execute</li>
	 * <li><u>third args</u> : Option</li>
	 * </ul>
	 * 
	 * @param args
	 * @return
	 */
	public InvocationResult run(String args[]) {
		if (args.length != 3) {
			throw new ApplicationInternalError("Expected only three paramters { Pom path, mvn command, mvn options}");
		}
		InvocationRequest request = new DefaultInvocationRequest();
		request.setPomFile(new File(args[0]));
		request.setGoals(Arrays.asList(args[1]));
		request.setMavenOpts(args[2]);
		try {
			return invoker.execute(request);
		} catch (MavenInvocationException e) {
			LOG.error("An error occur during maven execution refers to stack trace", e);
			return null;
		}
	}

}
