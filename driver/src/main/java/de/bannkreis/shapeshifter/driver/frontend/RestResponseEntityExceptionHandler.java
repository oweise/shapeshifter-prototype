package de.bannkreis.shapeshifter.driver.frontend;

import de.bannkreis.shapeshifter.driver.frontend.entities.JobStartResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({Exception.class})
    public ResponseEntity<JobStartResponse> handleBasicException(Exception ex, WebRequest request) {
        ex.printStackTrace();
        return new ResponseEntity<JobStartResponse>(
                new JobStartResponse().error(ex.getClass().getName(), ex.getMessage()),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }


}
