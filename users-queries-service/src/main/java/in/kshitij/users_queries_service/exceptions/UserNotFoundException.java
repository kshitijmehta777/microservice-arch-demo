package in.kshitij.users_queries_service.exceptions;

public class UserNotFoundException extends RuntimeException{
	private static final long serialVersionUID = 1L;
	public UserNotFoundException(String string) {
		super(string);
	}
}
