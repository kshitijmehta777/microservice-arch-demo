package in.kshitij.users_command_service.exceptions;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@RestControllerAdvice
public class ExceptionHandlerAdvice {
	
	@ExceptionHandler(EmailAlreadyExistsException.class)
	public ProblemDetail handleEmailAlreadyExistsException(Exception e) {
		return ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(403), e.getLocalizedMessage());
	}

}
