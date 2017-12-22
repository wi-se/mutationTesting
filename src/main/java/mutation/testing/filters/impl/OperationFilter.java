package mutation.testing.filters.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javassist.CtClass;
import javassist.CtMethod;
import javassist.CtPrimitiveType;
import javassist.NotFoundException;
import mutation.testing.filters.IFilter;
import mutation.testing.loaders.impl.LoaderImpl;

public class OperationFilter implements IFilter {
	private static final Log LOG = LogFactory.getLog(OperationFilter.class);
	private CtClass returnType = LoaderImpl.getInstance().getClass("java.lang.Boolean");
	private static OperationFilter instance;

	private OperationFilter() {

	}

	public static IFilter getInstance() {
		if (instance == null) {
			instance = new OperationFilter();
		}
		return instance;
	}

	@Override
	public List<CtMethod> doFilter(List<CtMethod> methods) {
		return methods.stream().filter(m -> {
			try {
				CtClass t = m.getReturnType();
				return (!t.equals(returnType) && !t.equals(CtPrimitiveType.booleanType));
			} catch (NotFoundException e) {
				LOG.warn("The method was not found, skiping it ", e);
			}
			return false;
		}).collect(Collectors.toList());
	}

}
