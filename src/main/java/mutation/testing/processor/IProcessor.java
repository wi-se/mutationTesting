package mutation.testing.processor;

import javassist.CtMethod;
import mutation.testing.loaders.impl.Pair;

public interface IProcessor {
	void processPair(Pair pair);

	void processAll(String pathTojar);

	CtMethod processMethod(CtMethod m);

	void exec(String[] args);
}
