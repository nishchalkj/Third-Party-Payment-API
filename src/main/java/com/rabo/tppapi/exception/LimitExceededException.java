package com.rabo.tppapi.exception;

/**
 * LimitExceededException custom exception
 * @author Nishchal
 *
 */
public class LimitExceededException extends PaymentInitiationException {


	private static final long serialVersionUID = 6639841978610906958L;
	

	/**
	 * {@link LimitExceededException} constructor.
	 *
	 * @param message
	 *            exception message
	 * 
	 */
	public LimitExceededException(final String message) {
		super(message);
	}
	
	/**
	 * {@link LimitExceededException} constructor.
	 *
	 * @param message
	 *            exception message
	 * @param statusCode  
	 *            error statuscode
	 *  
	 */
	public LimitExceededException(final String message, final int statusCode) {
		super(message,statusCode);
	}


	

}
