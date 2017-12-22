package mutation.testing.mutants.impl;

import java.util.HashMap;
import java.util.Map;

import javassist.CannotCompileException;
import javassist.CtMethod;
import javassist.CtNewMethod;
import javassist.bytecode.BadBytecode;
import javassist.bytecode.CodeIterator;
import javassist.bytecode.MethodInfo;
import javassist.bytecode.Opcode;
import mutation.testing.mutants.IMutant;
import mutation.testing.mutants.MutantTemplate;

public class OperationMutation extends MutantTemplate implements IMutant {
	private static IMutant instance;
	private Map<Integer, Integer> modification;

	private OperationMutation() {
		modification = new HashMap<>();
	}

	public static IMutant getInstance() {
		if (instance == null) {
			instance = new OperationMutation();
		}
		return instance;
	}

	@Override
	public CtMethod doMutate(CtMethod m) throws CannotCompileException, BadBytecode {
		original = CtNewMethod.copy(m, m.getDeclaringClass(), null);
		// do not process if interface
		if (!m.getDeclaringClass().isInterface()) {
			if (m.getDeclaringClass().isFrozen()) {
				m.getDeclaringClass().defrost();
			}
			MethodInfo info = m.getMethodInfo();
			if (info.getCodeAttribute() != null) {
				CodeIterator it = info.getCodeAttribute().iterator();
				int actualIndex = it.next();
				addModif(it.byteAt(actualIndex));
				if (!modification.isEmpty()) {
					it.writeByte(modification.get(actualIndex), actualIndex);
				}
			}
		}
		return m;
	}

	private void addModif(Integer position) {
		switch (position) {
		case Opcode.IADD:
			modification.put(position, Opcode.ISUB);
			break;

		case Opcode.ISUB:
			modification.put(position, Opcode.IADD);
			break;

		case Opcode.FADD:
			modification.put(position, Opcode.FSUB);
			break;

		case Opcode.FSUB:
			modification.put(position, Opcode.FADD);
			break;

		case Opcode.LADD:
			modification.put(position, Opcode.LSUB);
			break;

		case Opcode.LSUB:
			modification.put(position, Opcode.LADD);
			break;

		case Opcode.DADD:
			modification.put(position, Opcode.DSUB);
			break;

		case Opcode.DSUB:
			modification.put(position, Opcode.DADD);
			break;

		case Opcode.IMUL:
			modification.put(position, Opcode.IDIV);
			break;

		case Opcode.IDIV:
			modification.put(position, Opcode.IMUL);
			break;

		case Opcode.FDIV:
			modification.put(position, Opcode.FMUL);
			break;

		case Opcode.FMUL:
			modification.put(position, Opcode.FDIV);
			break;

		case Opcode.LMUL:
			modification.put(position, Opcode.LDIV);
			break;

		case Opcode.LDIV:
			modification.put(position, Opcode.LMUL);
			break;

		case Opcode.DMUL:
			modification.put(position, Opcode.DDIV);
			break;

		case Opcode.DDIV:
			modification.put(position, Opcode.DMUL);
			break;
		}
	}
}
