public class TooManyException extends Exception{
	public TooManyException() {

    }

    public TooManyException(String message) {
        super (message);
    }

    public TooManyException(Throwable cause) {
        super (cause);
    }

    public TooManyException(String message, Throwable cause) {
        super (message, cause);
    }
}
