package mutation.testing.processor.impl;

import mutation.testing.filters.impl.OperationFilter;
import mutation.testing.mutants.impl.OperationMutation;
import mutation.testing.processor.IProcessor;
import mutation.testing.processor.TemplateProcess;

public class OperationProcessor extends TemplateProcess implements IProcessor {

	public OperationProcessor() {
		this.mutant = OperationMutation.getInstance();
		this.filter = OperationFilter.getInstance();
	}

}
