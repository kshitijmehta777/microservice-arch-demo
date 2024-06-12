package in.kshitij.users_queries_service.exceptions;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@RestControllerAdvice
public class ExceptionHandlerAdvice {
	
	@ExceptionHandler(UserNotFoundException.class)
	public ProblemDetail handleUserNotFoundException(Exception e) {
		return ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(404), e.getLocalizedMessage());
	}

}
