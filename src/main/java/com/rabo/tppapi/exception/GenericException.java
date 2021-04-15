package com.rabo.tppapi.exception;

public class GenericException extends PaymentInitiationException {


	private static final long serialVersionUID = 1L;

	/**
	 * {@link GenericException} constructor.
	 *
	 * @param message
	 *            exception message
	 * 
	 */
	public GenericException(String message, int statusCode) {
		super(message, statusCode);
		
	}
	
	/**
	 * {@link GenericException} constructor.
	 *
	 * @param message
	 *            exception message
	 * @param statusCode  
	 *            error statuscode
	 *  
	 */
	
	public GenericException(String message) {
		super(message);
	}

}
