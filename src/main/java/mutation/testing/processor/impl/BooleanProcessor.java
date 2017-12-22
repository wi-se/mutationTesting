package mutation.testing.processor.impl;

import mutation.testing.filters.impl.BooleanFilter;
import mutation.testing.mutants.impl.BooleanMutation;
import mutation.testing.processor.IProcessor;
import mutation.testing.processor.TemplateProcess;

public class BooleanProcessor extends TemplateProcess implements IProcessor {
	public BooleanProcessor() {
		this.mutant = BooleanMutation.getInstance();
		this.filter = BooleanFilter.getInstance();
	}
}
