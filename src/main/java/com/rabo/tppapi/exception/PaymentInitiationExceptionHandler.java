package com.rabo.tppapi.exception;

import java.time.LocalDateTime;
import java.time.ZoneId;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.rabo.tppapi.constants.ApplicationConstant;
import com.rabo.tppapi.model.PaymentInitiationExceptionResponse;

/**
 * Exception handler all the exceptions
 * @author Nishchal
 *
 */
@ControllerAdvice
public class PaymentInitiationExceptionHandler extends ResponseEntityExceptionHandler{
	
	/**
	 * This method handles the custom exception thrown in the application
	 * @param exception PaymentInitiationException- exception to be thrown
	 * @return ResponseEntity<PaymentInitiationExceptionResponse>
	 */
	@ExceptionHandler(PaymentInitiationException.class)
	public ResponseEntity<PaymentInitiationExceptionResponse> handleCustomException(final PaymentInitiationException ex) {
        final PaymentInitiationExceptionResponse errorResponse = new PaymentInitiationExceptionResponse();
        errorResponse.setReason(ex.getMessage());
        errorResponse.setStatus(ApplicationConstant.REJECTED);
        errorResponse.setReasonCode(String.valueOf(ex.getStatusCode()));
        errorResponse.setTimeStamp(LocalDateTime.now(ZoneId.of(ApplicationConstant.ZONEID)));
        if(400 == ex.getStatusCode()){
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }else if(422 == ex.getStatusCode()){
        return new ResponseEntity<>(errorResponse, HttpStatus.UNPROCESSABLE_ENTITY);
        }else{
        	 return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
    }
	
	/**
	 * This method handles the generic exception thrown in the application
	 * 
	 * @param ex
	 *            Exception - exception to be thrown
	 * @return ResponseEntity<PaymentInitiationExceptionResponse>
	 */
	@ExceptionHandler(Exception.class)
	public ResponseEntity<PaymentInitiationExceptionResponse> genericExceptionHandler(Exception ex) {
		PaymentInitiationExceptionResponse errorResponse = new PaymentInitiationExceptionResponse();
		errorResponse.setReason(ex.getMessage());
		errorResponse.setStatus(ApplicationConstant.REJECTED);
		errorResponse.setTimeStamp(LocalDateTime.now(ZoneId.of(ApplicationConstant.ZONEID)));
		return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	
}
