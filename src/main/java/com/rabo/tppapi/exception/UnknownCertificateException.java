package com.rabo.tppapi.exception;

/**
 * UnknownCertificateException custom exception
 * @author Nishchal
 *
 */
public class UnknownCertificateException extends PaymentInitiationException {
	
	
    private static final long serialVersionUID = -6674963614713141884L;
    

	/**
	 * {@link UnknownCertificateException} constructor.
	 *
	 * @param message
	 *            exception message
	 * 
	 */
	public UnknownCertificateException(final String message) {
		super(message);
	}
	
	/**
	 * {@link InvalidSignatureException} constructor.
	 *
	 * @param message
	 *            exception message
	 * @param statusCode  
	 *            error statuscode
	 *  
	 */
	public UnknownCertificateException(final String message, final int statusCode) {
		super(message,statusCode);
	}




}
