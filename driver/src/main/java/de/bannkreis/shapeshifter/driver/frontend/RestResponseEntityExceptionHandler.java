package de.bannkreis.shapeshifter.driver.frontend;

import de.bannkreis.shapeshifter.driver.InternalErrorException;
import de.bannkreis.shapeshifter.driver.frontend.entities.JobStartResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger LOG = LoggerFactory.getLogger(RestResponseEntityExceptionHandler.class);

    @ExceptionHandler({Exception.class})
    public ResponseEntity<JobStartResponse> handleBasicException(Exception ex, WebRequest request) {
        LOG.error("Cancelling request", ex);
        return new ResponseEntity<JobStartResponse>(
                new JobStartResponse().error(ex.getClass().getName(), ex.getMessage()),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

    @ExceptionHandler({InternalErrorException.class})
    public ResponseEntity<JobStartResponse> handleInternalErrorException(InternalErrorException ex, WebRequest request) {
        LOG.error("Cancelling request", ex);
        return new ResponseEntity<JobStartResponse>(
                new JobStartResponse().error(ex.getClass().getName(), ex.getMessage()),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }


}
