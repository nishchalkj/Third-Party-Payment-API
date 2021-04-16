package com.rabo.tppapi.service.implementation;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.nio.charset.StandardCharsets;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Base64;

import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import com.rabo.tppapi.constants.ApplicationConstant;
import com.rabo.tppapi.exception.GenericException;
import com.rabo.tppapi.model.PaymentInitiationRequest;
import com.rabo.tppapi.model.PaymentInitiationResponse;
import com.rabo.tppapi.model.PaymentInitiationResponseOverview;
import com.rabo.tppapi.service.PaymentInitiationService;
import com.rabo.tppapi.validator.PaymentInitiationRequestValidator;

/**
 * Service layer for the API
 * 
 * @author Nishchal
 *
 */
@Qualifier("tppAPIService")
@Service
public class PaymentInitiationServiceImpl implements PaymentInitiationService {

	@Autowired
	PaymentInitiationRequestValidator validator;

	@Autowired
	PaymentInitiationResponse response;

	private static final Logger log = LoggerFactory.getLogger(PaymentInitiationServiceImpl.class);

	/**
	 * This method validates and process the request
	 * 
	 * @param cert
	 *            Base64 encoded certificate
	 * @param signature
	 *            Base64 encoded signature
	 * @param request
	 *            request body
	 * @return PaymentInitiationResponseOverview
	 */
	@Override
	public PaymentInitiationResponseOverview validateRequest(String endodedCertificate, String signature,
			PaymentInitiationRequest request) {

		PaymentInitiationResponseOverview responseOverview = new PaymentInitiationResponseOverview();
		X509Certificate certificate;
		try {
			CertificateFactory certFactory = CertificateFactory.getInstance(ApplicationConstant.CERTIFICATETYPE);
			byte[] originalcert = Base64.getDecoder().decode(endodedCertificate);
			ByteArrayInputStream inputStream = new ByteArrayInputStream(originalcert);
			certificate = (X509Certificate) certFactory.generateCertificate(inputStream);
		} catch (Exception ex) {
			log.error("Exception occured while processing the request" + " " + ex.getMessage());
			throw new GenericException(ApplicationConstant.GENERAL_ERROR, 500);
		}
		validator.validateRequest(certificate, signature, request);
		String paymentId = generatePaymentId();
		response.setPaymentId(paymentId);
		response.setStatus(ApplicationConstant.ACCEPTED);
		responseOverview.setResponseSignature(generateSignature(response));
		responseOverview.setCertificate(endodedCertificate);
		responseOverview.setResponse(response);

		return responseOverview;
	}

	/**
	 * Random Id generator
	 * 
	 * @return string value
	 */
	private String generatePaymentId() {
		String id = RandomStringUtils.randomAlphanumeric(20).toUpperCase();
		return id;
	}

	/**
	 * This method geneartes the signature on response body
	 * 
	 * @param response
	 *            response body
	 * @return string value
	 */
	private String generateSignature(PaymentInitiationResponse response) {
		String encryptedSign = null;
		try {
			KeyStore keyStore = KeyStore.getInstance(ApplicationConstant.KEYSTORE_TYPE);
			char[] pwdArray = ApplicationConstant.PWD_ARRAY.toCharArray();
			File file = ResourceUtils.getFile("classpath:pksc12/sandbox.p12");
			keyStore.load(new FileInputStream(file), pwdArray);
			PrivateKey privateKey = (PrivateKey) keyStore.getKey("sandbox", pwdArray);
			Signature signature = Signature.getInstance(ApplicationConstant.SHA256_RSA);
			signature.initSign(privateKey);
			String responseMsg = response.toString();
			byte[] messageBytes = responseMsg.getBytes(StandardCharsets.UTF_8);
			signature.update(messageBytes);
			byte[] digitalSignature = signature.sign();
			byte[] base64ByteArraySign = Base64.getEncoder().encode(digitalSignature);
			encryptedSign = new String(base64ByteArraySign);
		} catch (Exception ex) {
			log.error("Exception occured while processing the request" + " " + ex.getMessage());
			throw new GenericException(ApplicationConstant.GENERAL_ERROR, 500);
		}
		return encryptedSign;

	}

}
