package com.rabo.tppapi.constants;

/**
 * Constant class for the application
 * @author Nishchal
 *
 */
public final class ApplicationConstant {
	
	 public static final String ACCEPTED = "Accepted";
	    
	 public static final String REJECTED = "Rejected";
	 
	 public static final String ZONEID = "Europe/Amsterdam";
	 
	 public static final String CERTIFICATETYPE = "X.509";
	 
	 public static final String GENERAL_ERROR = "General_Error";
	 
	 public static final String INVALID_REQUEST = "Invalid_Request";
	 
	 public static final String UNKNOWN_CERTIFICATE = "Unknown Certificate";
	 
	 public static final String INVALID_SIGNATURE = "Invalid Signature";
	 
	 public static final String LIMIT_AMOUNT_EXCEEDED = "Limit Amount Exceeded";
	 
	 public static final String COMMON_NAME = "Sandbox-TPP";
	 
	 public static final String SHA256_RSA = "SHA256withRSA";
	 
	 public static final String PWD_ARRAY = "sandboxexport";
	 
	 public static final String KEYSTORE_TYPE = "PKCS12";
	 

	 private ApplicationConstant() throws InstantiationException {
	        throw new InstantiationException("Cannot instantiate this class");
	    }
}
