package com.rabo.tppapi.service;

import com.rabo.tppapi.model.PaymentInitiationRequest;
import com.rabo.tppapi.model.PaymentInitiationResponseOverview;

/**
 * Interface for the service layer 
 * @author Nishchal
 *
 */
public interface PaymentInitiationService {
	
	/**
	 * This method validates and process the request
	 * @param cert Base64 encoded certificate
	 * @param signature Base64 encoded signature
	 * @param request request body
	 * @return PaymentInitiationResponseOverview
	 */
	PaymentInitiationResponseOverview validateRequest(String cert,String signature,PaymentInitiationRequest request);

}
