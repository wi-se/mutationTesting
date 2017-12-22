package mutation.testing.facade;

import mutation.testing.processor.IProcessor;
import mutation.testing.processor.impl.BooleanProcessor;
import mutation.testing.processor.impl.OperationProcessor;

public class Facade {
	private static Facade instance;

	private Facade() {
	}

	public void exec(String pathToJar) {
		IProcessor opProcess = new OperationProcessor();
		IProcessor bProcess = new BooleanProcessor();
		opProcess.processAll(pathToJar);
		bProcess.processAll(pathToJar);
	}

	public static Facade getInstance() {
		if (instance == null) {
			instance = new Facade();
		}
		return instance;
	}
}
