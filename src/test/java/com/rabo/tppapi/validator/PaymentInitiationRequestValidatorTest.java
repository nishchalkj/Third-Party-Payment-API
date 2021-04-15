package com.rabo.tppapi.validator;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Base64;

import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabo.tppapi.constants.ApplicationConstant;
import com.rabo.tppapi.exception.GenericException;
import com.rabo.tppapi.exception.InvalidRequestException;
import com.rabo.tppapi.exception.InvalidSignatureException;
import com.rabo.tppapi.exception.LimitExceededException;
import com.rabo.tppapi.exception.UnknownCertificateException;
import com.rabo.tppapi.model.PaymentInitiationRequest;
import com.rabo.tppapi.testutils.TestUtils;

@RunWith(MockitoJUnitRunner.class)
@ExtendWith(MockitoExtension.class)
public class PaymentInitiationRequestValidatorTest {

	@InjectMocks
	PaymentInitiationRequestValidator requestValidator;

	X509Certificate certificate;

	String encodedSignature;

	String encodedCertificate;

	PaymentInitiationRequest request;

	private final ObjectMapper mapper = new ObjectMapper();

	@Test(expected = Test.None.class)
	public void givenValidRequest_WhenMethodIsCalled_ThenNoExceptionIsThrown()
			throws CertificateException, IOException {

		perpareDataForSuccesfulResponse();

		requestValidator.validateRequest(certificate, encodedSignature.trim(), request);
	}

	@Test(expected = GenericException.class)
	public void givenNoCertificate_WhenMethodIsCalled_ThenInvalidRequestExceptionIsThrown()
			throws CertificateException, IOException {

		perpareDataForWithNoCertificate();

		requestValidator.validateRequest(certificate, encodedSignature.trim(), request);
	}

	@Test(expected = GenericException.class)
	public void givenNoSignature_WhenMethodIsCalled_ThenInvalidRequestExceptionIsThrown()
			throws CertificateException, IOException {

		perpareDataForWithNoSignature();

		requestValidator.validateRequest(certificate, encodedSignature, request);
	}

	@Test(expected = InvalidRequestException.class)
	public void givenNoRequestbody_WhenMethodIsCalled_ThenInvalidRequestExceptionIsThrown()
			throws CertificateException, IOException {

		perpareDataForWithNoRequestBody();

		requestValidator.validateRequest(certificate, encodedSignature.trim(), request);
	}
	
	@Test(expected = InvalidRequestException.class)
	public void givenInvalidIBAN_WhenMethodIsCalled_ThenInvalidRequestExceptionIsThrown()
			throws CertificateException, IOException {

		perpareDataForInvalidIBAN();

		requestValidator.validateRequest(certificate, encodedSignature.trim(), request);
	}

	@Test(expected = Test.None.class)
	public void givenProperCertificate_WhenMethodIsCalled_ThenNoExceptionIsThrown()
			throws CertificateException, IOException {

		perpareDataForWithProperCertificate();

		requestValidator.validateCertificate(certificate);
	}

	@Test(expected = UnknownCertificateException.class)
	public void givenInCorrectCNCertificate_WhenMethodIsCalled_ThenUnknownCertificateExceptionIsThrown()
			throws CertificateException, IOException {

		perpareDataForWithInCorrectCNCertificate();

		requestValidator.validateCertificate(certificate);
	}

	@Test(expected = Test.None.class)
	public void givenProperSignature_WhenMethodIsCalled_ThenNoExceptionIsThrown()
			throws CertificateException, IOException {

		perpareDataForSuccesfulResponse();

		requestValidator.validateSignature(certificate, encodedSignature.trim(), request);
	}

	@Test(expected = InvalidSignatureException.class)
	public void givenDifferentKeySignedSignature_WhenMethodIsCalled_ThenInvalidSignatureExceptionIsThrown()
			throws CertificateException, IOException {

		perpareDataForDifferentKeySignedSignature();

		requestValidator.validateSignature(certificate, encodedSignature.trim(), request);
	}

	@Test(expected = Test.None.class)
	public void givenProperRequestBody_WhenMethodIsCalled_ThenNoExceptionIsThrown()
			throws CertificateException, IOException {

		perpareDataForProperRequestBody();

		requestValidator.validateAmountLimit(request);
	}

	@Test(expected = LimitExceededException.class)
	public void givenLimitAmoutExceededRequestBody_WhenMethodIsCalled_ThenLimitExceededExceptionIsThrown()
			throws CertificateException, IOException {

		perpareDataForLimitExceededExceptionRequestBody();

		requestValidator.validateAmountLimit(request);
	}

	private void perpareDataForSuccesfulResponse() throws IOException, CertificateException {

		String certFilePath = "classpath:ValidCertificate.txt";
		String signFilePath = "classpath:ValidRequestSignature.txt";
		encodedCertificate = TestUtils.readCertificate(certFilePath);
		encodedSignature = TestUtils.readSignature(signFilePath);
		request = mapper.readValue(TestUtils.readFile("test-valid-request.json"), PaymentInitiationRequest.class);
		CertificateFactory certFactory = CertificateFactory.getInstance(ApplicationConstant.CERTIFICATETYPE);
		byte[] originalcert = Base64.getDecoder().decode(encodedCertificate.trim());
		ByteArrayInputStream inputStream = new ByteArrayInputStream(originalcert);
		certificate = (X509Certificate) certFactory.generateCertificate(inputStream);
	}

	private void perpareDataForWithNoCertificate() throws IOException, CertificateException {
		String signFilePath = "classpath:ValidRequestSignature.txt";
		encodedSignature = TestUtils.readSignature(signFilePath);
		request = mapper.readValue(TestUtils.readFile("test-valid-request.json"), PaymentInitiationRequest.class);
		certificate = null;
	}

	private void perpareDataForWithNoSignature() throws IOException, CertificateException {

		String certFilePath = "classpath:ValidCertificate.txt";
		encodedCertificate = TestUtils.readCertificate(certFilePath);
		request = mapper.readValue(TestUtils.readFile("test-valid-request.json"), PaymentInitiationRequest.class);
		CertificateFactory certFactory = CertificateFactory.getInstance(ApplicationConstant.CERTIFICATETYPE);
		byte[] originalcert = Base64.getDecoder().decode(encodedCertificate.trim());
		ByteArrayInputStream inputStream = new ByteArrayInputStream(originalcert);
		certificate = (X509Certificate) certFactory.generateCertificate(inputStream);
	}

	private void perpareDataForWithNoRequestBody() throws IOException, CertificateException {

		String certFilePath = "classpath:ValidCertificate.txt";
		String signFilePath = "classpath:ValidRequestSignature.txt";
		encodedCertificate = TestUtils.readCertificate(certFilePath);
		encodedSignature = TestUtils.readSignature(signFilePath);
		request = null;
		CertificateFactory certFactory = CertificateFactory.getInstance(ApplicationConstant.CERTIFICATETYPE);
		byte[] originalcert = Base64.getDecoder().decode(encodedCertificate.trim());
		ByteArrayInputStream inputStream = new ByteArrayInputStream(originalcert);
		certificate = (X509Certificate) certFactory.generateCertificate(inputStream);
	}
	
	private void perpareDataForInvalidIBAN() throws IOException, CertificateException {

		String certFilePath = "classpath:ValidCertificate.txt";
		String signFilePath = "classpath:ValidRequestSignature.txt";
		encodedCertificate = TestUtils.readCertificate(certFilePath);
		encodedSignature = TestUtils.readSignature(signFilePath);
		request = mapper.readValue(TestUtils.readFile("test-invalidIban-request.json"), PaymentInitiationRequest.class);;
		CertificateFactory certFactory = CertificateFactory.getInstance(ApplicationConstant.CERTIFICATETYPE);
		byte[] originalcert = Base64.getDecoder().decode(encodedCertificate.trim());
		ByteArrayInputStream inputStream = new ByteArrayInputStream(originalcert);
		certificate = (X509Certificate) certFactory.generateCertificate(inputStream);
	}

	private void perpareDataForWithProperCertificate() throws IOException, CertificateException {

		String certFilePath = "classpath:ValidCertificate.txt";
		encodedCertificate = TestUtils.readCertificate(certFilePath);
		CertificateFactory certFactory = CertificateFactory.getInstance(ApplicationConstant.CERTIFICATETYPE);
		byte[] originalcert = Base64.getDecoder().decode(encodedCertificate.trim());
		ByteArrayInputStream inputStream = new ByteArrayInputStream(originalcert);
		certificate = (X509Certificate) certFactory.generateCertificate(inputStream);
	}

	private void perpareDataForWithInCorrectCNCertificate() throws IOException, CertificateException {

		String certFilePath = "classpath:IncorrectCNnameCertificate.txt";
		encodedCertificate = TestUtils.readCertificate(certFilePath);
		CertificateFactory certFactory = CertificateFactory.getInstance(ApplicationConstant.CERTIFICATETYPE);
		byte[] originalcert = Base64.getDecoder().decode(encodedCertificate.trim());
		ByteArrayInputStream inputStream = new ByteArrayInputStream(originalcert);
		certificate = (X509Certificate) certFactory.generateCertificate(inputStream);
	}

	private void perpareDataForDifferentKeySignedSignature() throws IOException, CertificateException {

		String certFilePath = "classpath:ValidCertificate.txt";
		String signFilePath = "classpath:DifferentKeySignedRequestSignature.txt";
		encodedCertificate = TestUtils.readCertificate(certFilePath);
		encodedSignature = TestUtils.readSignature(signFilePath);
		request = mapper.readValue(TestUtils.readFile("test-valid-request.json"), PaymentInitiationRequest.class);
		CertificateFactory certFactory = CertificateFactory.getInstance(ApplicationConstant.CERTIFICATETYPE);
		byte[] originalcert = Base64.getDecoder().decode(encodedCertificate.trim());
		ByteArrayInputStream inputStream = new ByteArrayInputStream(originalcert);
		certificate = (X509Certificate) certFactory.generateCertificate(inputStream);
	}

	private void perpareDataForProperRequestBody() throws IOException, CertificateException {

		request = mapper.readValue(TestUtils.readFile("test-valid-request.json"), PaymentInitiationRequest.class);
	}

	private void perpareDataForLimitExceededExceptionRequestBody() throws IOException, CertificateException {

		request = mapper.readValue(TestUtils.readFile("test-amountlimit-request.json"), PaymentInitiationRequest.class);
	}

}
