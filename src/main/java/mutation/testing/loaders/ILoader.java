package mutation.testing.loaders;

import java.util.List;

import javassist.CtClass;
import javassist.CtMethod;
import mutation.testing.filters.IFilter;
import mutation.testing.loaders.impl.Pair;

public interface ILoader {

	void load(String pathToJar);

	Pair get();

	List<CtMethod> getMethods(CtClass cc, IFilter filter);

	CtClass getClass(String className);

	String getPom();

	void add(Pair p);
}
