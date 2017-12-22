package mutation.testing.filters;

import java.util.List;

import javassist.CtMethod;

public interface IFilter {
	List<CtMethod> doFilter(List<CtMethod> methods);
}
