package mutation.testing.mutants;

import javassist.CannotCompileException;
import javassist.CtMethod;

public abstract class MutantTemplate implements IMutant {
	protected CtMethod original;

	@Override
	public CtMethod getOriginal() {
		return original;
	}

	@Override
	public void rollback(CtMethod m) throws CannotCompileException {
		if (m.getDeclaringClass().isFrozen()) {
			m.getDeclaringClass().defrost();
		}
		m.setBody(original, null);
	}
}
