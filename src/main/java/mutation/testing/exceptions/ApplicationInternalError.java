package mutation.testing.exceptions;

public class ApplicationInternalError extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4010398747459383700L;

	public ApplicationInternalError(String error) {
		super(error);
	}

	public ApplicationInternalError(Throwable e) {
		super(e);
	}
}
