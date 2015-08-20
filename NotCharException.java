public class NotCharException extends Exception{
	public NotCharException() {

    }

    public NotCharException(String message) {
        super (message);
    }

    public NotCharException(Throwable cause) {
        super (cause);
    }

    public NotCharException(String message, Throwable cause) {
        super (message, cause);
    }
}
