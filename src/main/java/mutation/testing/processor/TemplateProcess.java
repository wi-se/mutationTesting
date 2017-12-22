package mutation.testing.processor;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.Callable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.maven.shared.invoker.InvocationResult;

import javassist.CannotCompileException;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.bytecode.BadBytecode;
import mutation.testing.execution.ExecPool;
import mutation.testing.execution.MvnHelper;
import mutation.testing.filters.IFilter;
import mutation.testing.loaders.ILoader;
import mutation.testing.loaders.impl.LoaderImpl;
import mutation.testing.loaders.impl.Pair;
import mutation.testing.mutants.IMutant;
import mutation.testing.parsing.DirectoryLoader;

public abstract class TemplateProcess implements IProcessor {
	protected IMutant mutant;
	protected ILoader loader;
	protected IFilter filter;
	private static final Log LOG = LogFactory.getLog(TemplateProcess.class);
	private MvnHelper mvn = new MvnHelper();
	private ExecPool execPool = ExecPool.getInstance();

	protected void write(Pair pair, CtMethod m) {
		try {
			LOG.info("Writting the file " + pair.getFile().getAbsolutePath() + " muted method : " + m.getName());
			m.getDeclaringClass().writeFile(DirectoryLoader.getInstance().getTargetDir());
		} catch (CannotCompileException | IOException e) {
			LOG.error("Unable to re write the class : " + pair.getAttachedPath(), e);
		}
	}

	@Override
	public void processAll(String pathToJar) {
		if (loader == null) {
			loader = LoaderImpl.getInstance();
			loader.load(pathToJar);
		}
		Pair pair;
		while ((pair = loader.get()) != null) {
			processPair(pair);
			LOG.info("exec the maven goal clean test -Dtest=\"");
			String[] args = new String[] { loader.getPom(), "surfire:test",
					" -Dmaven.test.failure.ignore=true  -Dtest=" + pair.getAttachedClassTest() };
			exec(args);
		}

	}

	@Override
	public void processPair(Pair pair) {
		LOG.info("Processing the file : " + pair.getAttachedPath());
		CtClass cc = loader.getClass(pair.getAttachedPath());
		if (cc != null) {
			List<CtMethod> cms = loader.getMethods(cc, filter);
			if (cms != null) {
				cms.stream().forEach(m -> {
					CtMethod mutedMethod = processMethod(m);
					if (mutedMethod != null) {
						write(pair, mutedMethod);
					}
				});
			}
		}
	}

	@Override
	public CtMethod processMethod(CtMethod m) {
		try {
			CtMethod mutedMethod = mutant.doMutate(m);
			return mutedMethod;
		} catch (CannotCompileException | BadBytecode e) {
			LOG.error("Unable to perfom the mutation in this method : " + m.getName(), e);
		}
		return null;
	}

	@Override
	public void exec(String[] args) {
		execPool.exec(new Callable<InvocationResult>() {

			@Override
			public InvocationResult call() throws Exception {
				return mvn.run(args);
			}
		});
	}
}
