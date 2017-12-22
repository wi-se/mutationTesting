package mutation.testing.mutants;

import javassist.CannotCompileException;
import javassist.CtMethod;
import javassist.bytecode.BadBytecode;

public interface IMutant {
	CtMethod doMutate(CtMethod m) throws CannotCompileException, BadBytecode;

	CtMethod getOriginal();

	void rollback(CtMethod m) throws CannotCompileException;
}
