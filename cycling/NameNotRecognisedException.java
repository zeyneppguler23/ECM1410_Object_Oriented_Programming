package cycling;

/**
 * Thrown when attempting to use a name that does not exit in the
 * system.
 * 
 * @author 730055520
 * @version 0.1
 *
 */
public class NameNotRecognisedException extends Exception {

	/**
	 * Constructs an instance of the exception with no message
	 */
	public NameNotRecognisedException() {
		// do nothing
	}

	/**
	 * Constructs an instance of the exception containing the message argument
	 * 
	 * @param message message containing details regarding the exception cause
	 */
	public NameNotRecognisedException(String message) {
		super(message);
	}

}
