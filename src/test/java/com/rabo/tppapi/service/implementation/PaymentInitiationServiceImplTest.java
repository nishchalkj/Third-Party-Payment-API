package com.rabo.tppapi.service.implementation;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.security.cert.X509Certificate;

import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabo.tppapi.exception.GenericException;
import com.rabo.tppapi.model.PaymentInitiationRequest;
import com.rabo.tppapi.model.PaymentInitiationResponse;
import com.rabo.tppapi.model.PaymentInitiationResponseOverview;
import com.rabo.tppapi.testutils.TestUtils;
import com.rabo.tppapi.validator.PaymentInitiationRequestValidator;

@RunWith(MockitoJUnitRunner.class)
@ExtendWith(MockitoExtension.class)
public class PaymentInitiationServiceImplTest {

	@InjectMocks
	PaymentInitiationServiceImpl service;

	@Mock
	PaymentInitiationRequestValidator validator;

	@Spy
	PaymentInitiationResponse response;

	private final ObjectMapper mapper = new ObjectMapper();

	String encodedCertificate = null;

	String encodedSignature = null;

	PaymentInitiationRequest request;

	@Test
	public void givenValidRequest_WhenMethodIsCalled_ThenReturnsSuccessfulResponse() throws IOException {

		perpareDataForSuccesfulResponse();

		PaymentInitiationResponseOverview responseOverview = service.validateRequest(encodedCertificate.trim(),
				encodedSignature.trim(), request);

		assertEquals("Accepted", responseOverview.getResponse().getStatus());

	}

	@Test(expected = GenericException.class)
	public void givenInvalidCertificateRequest_WhenMethodIsCalled_ThenReturnsUnSuccessfulResponse() throws IOException {

		perpareDataForInvalidCertificate();

		PaymentInitiationResponseOverview responseOverview = service.validateRequest(encodedCertificate.trim(),
				encodedSignature.trim(), request);

		assertEquals("Rejected", responseOverview.getResponse().getStatus());

	}

	private void perpareDataForSuccesfulResponse() throws IOException {

		String certFilePath = "classpath:ValidCertificate.txt";
		String signFilePath = "classpath:ValidRequestSignature.txt";
		encodedCertificate = TestUtils.readCertificate(certFilePath.trim());
		encodedSignature = TestUtils.readSignature(signFilePath.trim());
		request = mapper.readValue(TestUtils.readFile("test-valid-request.json"), PaymentInitiationRequest.class);
		Mockito.doNothing().when(validator).validateRequest(Mockito.anyObject(), Mockito.anyString(),
				Mockito.anyObject());

	}

	private void perpareDataForInvalidCertificate() throws IOException {

		String certFilePath = "classpath:InvalidCertificate.txt";
		String signFilePath = "classpath:ValidRequestSignature.txt";
		encodedCertificate = TestUtils.readCertificate(certFilePath.trim());
		encodedSignature = TestUtils.readSignature(signFilePath.trim());
		request = mapper.readValue(TestUtils.readFile("test-valid-request.json"), PaymentInitiationRequest.class);

	}

}
