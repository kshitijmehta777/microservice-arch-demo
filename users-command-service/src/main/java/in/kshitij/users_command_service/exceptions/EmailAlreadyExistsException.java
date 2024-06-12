package in.kshitij.users_command_service.exceptions;

public class EmailAlreadyExistsException extends RuntimeException{
	private static final long serialVersionUID = 1L;
	public EmailAlreadyExistsException(String string) {
		super(string);
	}
}
