package mutation.testing.mutants.impl;

import java.util.Random;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javassist.CannotCompileException;
import javassist.CtMethod;
import javassist.CtNewMethod;
import mutation.testing.mutants.IMutant;
import mutation.testing.mutants.MutantTemplate;

public class BooleanMutation extends MutantTemplate implements IMutant {
	private static IMutant instance;
	private static final Log LOG = LogFactory.getLog(BooleanMutation.class);

	private BooleanMutation() {

	}

	public static IMutant getInstance() {
		if (instance == null) {
			instance = new BooleanMutation();
		}
		return instance;
	}

	@Override
	public CtMethod doMutate(CtMethod m) {
		String expression = "return " + generateBool() + ";";
		try {
			original = CtNewMethod.copy(m, m.getDeclaringClass(), null);
			// do not process if interface
			if (!m.getDeclaringClass().isInterface()) {
				if (m.getDeclaringClass().isFrozen()) {
					m.getDeclaringClass().defrost();
				}
				m.insertBefore(expression);
				return m;
			}
		} catch (CannotCompileException e) {
			LOG.error("Unable to compile code. Refer to stack trace", e);
		}
		return m;
	}

	private String generateBool() {
		Random r = new Random();
		int generated = r.nextInt(1) + 1;
		if (generated == 0) {
			return "false";
		} else {
			return "true";
		}
	}

}
