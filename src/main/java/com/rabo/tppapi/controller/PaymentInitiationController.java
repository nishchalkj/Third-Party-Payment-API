package com.rabo.tppapi.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.rabo.tppapi.model.PaymentInitiationRequest;
import com.rabo.tppapi.model.PaymentInitiationResponse;
import com.rabo.tppapi.model.PaymentInitiationResponseOverview;
import com.rabo.tppapi.service.PaymentInitiationService;

/**
 * This is the controller class for Payment Initiation API
 * 
 * @author Nishchal
 *
 */
@RestController
public class PaymentInitiationController {

	@Autowired
	PaymentInitiationService tppAPIService;

	/**
	 * This method receives the httpServlet request and request Body for
	 * processing
	 * 
	 * @param servletRequest
	 *            servlet request object
	 * @param request
	 *            request body
	 * @return PaymentInitiationResponse
	 */
	@PostMapping(value = "/v1.0.0/initiate-payment")
	public ResponseEntity<PaymentInitiationResponse> paymentInitiationRequest(HttpServletRequest servletRequest,
			@Valid @RequestBody final PaymentInitiationRequest request) {
		String signature = servletRequest.getHeader("Signature");
		String certificate = servletRequest.getHeader("Signature-Certificate");
		PaymentInitiationResponseOverview responseOverview = tppAPIService.validateRequest(certificate, signature,
				request);
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.set("Signature", responseOverview.getResponseSignature());
		responseHeaders.set("Signature-Certificate", responseOverview.getCertificate());
		return new ResponseEntity<>(responseOverview.getResponse(), responseHeaders, HttpStatus.CREATED);
	}

}
