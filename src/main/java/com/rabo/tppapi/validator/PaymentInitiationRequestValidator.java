package com.rabo.tppapi.validator;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.security.PublicKey;
import java.security.Signature;
import java.security.cert.X509Certificate;
import java.util.Base64;

import org.apache.commons.lang3.StringUtils;
import org.bouncycastle.asn1.x500.RDN;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x500.style.BCStyle;
import org.bouncycastle.asn1.x500.style.IETFUtils;
import org.bouncycastle.cert.jcajce.JcaX509CertificateHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.rabo.tppapi.constants.ApplicationConstant;
import com.rabo.tppapi.exception.GenericException;
import com.rabo.tppapi.exception.InvalidRequestException;
import com.rabo.tppapi.exception.InvalidSignatureException;
import com.rabo.tppapi.exception.LimitExceededException;
import com.rabo.tppapi.exception.UnknownCertificateException;
import com.rabo.tppapi.model.PaymentInitiationRequest;

/**
 * Validator class for the API
 * 
 * @author Nishchal
 *
 */
@Component
public class PaymentInitiationRequestValidator {

	private static final Logger log = LoggerFactory.getLogger(PaymentInitiationRequestValidator.class);

	/**
	 * This method validates the input values
	 * 
	 * @param cert
	 *            X509 certificate
	 * @param signature
	 *            Base64 encoded signature
	 * @param request
	 *            request body
	 * 
	 */
	public void validateRequest(X509Certificate cert, String signature, PaymentInitiationRequest request) {

		if (null == request || StringUtils.isBlank(request.getDebtorIBAN()) || !StringUtils.isAlphanumeric(request.getDebtorIBAN())
				|| StringUtils.isBlank(request.getCreditorIBAN()) || !StringUtils.isAlphanumeric(request.getCreditorIBAN())) {
			throw new InvalidRequestException(ApplicationConstant.INVALID_REQUEST, 400);
		}
		validateCertificate(cert);
		validateSignature(cert, signature, request);
		validateAmountLimit(request);

	}

	/**
	 * This method validates the CN value of the incoming certificate
	 * 
	 * @param cert
	 *            X509 certificate
	 */
	public void validateCertificate(X509Certificate cert) {
		String commonName;
		X500Name x500name;
		try {
			x500name = new JcaX509CertificateHolder(cert).getSubject();
			RDN cn = x500name.getRDNs(BCStyle.CN)[0];
			commonName = IETFUtils.valueToString(cn.getFirst().getValue());
		} catch (Exception ex) {
			log.error("Exception occured while validating the certificate" + " " + ex.getMessage());
			throw new GenericException(ApplicationConstant.GENERAL_ERROR, 500);
		}
		if (!ApplicationConstant.COMMON_NAME.equals(commonName)) {
			throw new UnknownCertificateException(ApplicationConstant.UNKNOWN_CERTIFICATE, 400);
		}
	}

	/**
	 * This method validates the signature based on the incoming certificate
	 * 
	 * @param cert
	 *            X509 certificate
	 * @param requestSignature
	 *            Base64 encoded signature
	 * @param request
	 *            Request Body
	 */
	public void validateSignature(X509Certificate cert, String requestSignature, PaymentInitiationRequest request) {
		boolean result;
		try {
			PublicKey publicKey = cert.getPublicKey();
			Signature signature = Signature.getInstance(ApplicationConstant.SHA256_RSA);
			signature.initVerify(publicKey);
			String requestMsg = request.toString();
			byte[] messageBytes = requestMsg.getBytes(StandardCharsets.UTF_8);
			signature.update(messageBytes);
			byte[] originalSigBytes = Base64.getDecoder().decode(requestSignature.getBytes());
			result = signature.verify(originalSigBytes);
		} catch (Exception ex) {
			log.error("Exception occured while validating the signature" + " " + ex.getMessage());
			throw new GenericException(ApplicationConstant.GENERAL_ERROR, 500);
		}
		if (!result) {
			throw new InvalidSignatureException(ApplicationConstant.INVALID_SIGNATURE, 400);
		}

	}

	/**
	 * This method validates Limit amount
	 * 
	 * @param request
	 *            Incoming request body
	 */
	public void validateAmountLimit(PaymentInitiationRequest request) {

		BigDecimal amount = new BigDecimal(request.getAmount());
		int sum = calculateSumofDigits(request.getDebtorIBAN());
		int length = request.getDebtorIBAN().length();
		if (amount.compareTo(BigDecimal.ZERO) > 0 && (sum % length) == 0) {
			throw new LimitExceededException(ApplicationConstant.LIMIT_AMOUNT_EXCEEDED, 422);
		}

	}

	/**
	 * This method calculates the sum numbers in an IBAN
	 * 
	 * @param debtorIBAN
	 *            IBAN of the Debtor
	 * @return int value
	 */
	private int calculateSumofDigits(String debtorIBAN) {

		int sum = 0;
		for (int i = 0; i < debtorIBAN.length(); i++) {
			char temp = debtorIBAN.charAt(i);
			if (Character.isDigit(temp)) {
				sum = sum + Integer.parseInt(String.valueOf(temp));
			}
		}
		return sum;
	}

}
