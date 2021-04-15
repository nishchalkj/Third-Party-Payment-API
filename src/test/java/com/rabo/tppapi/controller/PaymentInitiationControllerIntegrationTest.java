package com.rabo.tppapi.controller;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabo.tppapi.TppapiApplication;
import com.rabo.tppapi.TestUtils.TestUtils;
import com.rabo.tppapi.model.PaymentInitiationRequest;
import com.rabo.tppapi.model.PaymentInitiationResponse;


@RunWith(SpringRunner.class)
@SpringBootTest(classes=TppapiApplication.class,webEnvironment=SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PaymentInitiationControllerIntegrationTest {

	@LocalServerPort
	private int port;

	TestRestTemplate restTemplate = new TestRestTemplate();

	HttpHeaders headers = new HttpHeaders();
	
	private final ObjectMapper mapper = new ObjectMapper();
	
	@Test
	public void givenValidPostRequest_WhenControllerIsCalled_ThenReturnsSuccessfulResponse() throws JsonParseException, JsonMappingException, IOException{
		
		HttpEntity<PaymentInitiationRequest> entity = prepareDataForSuccessfulResponse();
		
		ResponseEntity<PaymentInitiationResponse> response = restTemplate.exchange(
				createURLWithPort("/v1.0.0/initiate-payment"),
				HttpMethod.POST, entity, PaymentInitiationResponse.class);
		
		assertEquals("Accepted", response.getBody().getStatus());
		
	}
	
	@Test
	public void givenInvalidCertificateRequest_WhenControllerIsCalled_ThenReturnsUnSuccessfulResponse() throws JsonParseException, JsonMappingException, IOException{
		
		HttpEntity<PaymentInitiationRequest> entity = prepareDataForInvalidCertificate();
		
		ResponseEntity<PaymentInitiationResponse> response = restTemplate.exchange(
				createURLWithPort("/v1.0.0/initiate-payment"),
				HttpMethod.POST, entity, PaymentInitiationResponse.class);
		
		assertEquals("Rejected", response.getBody().getStatus());
		
	}
	
	@Test
	public void givenInvalidSignatureRequest_WhenControllerIsCalled_ThenReturnsUnSuccessfulResponse() throws JsonParseException, JsonMappingException, IOException{
		
		HttpEntity<PaymentInitiationRequest> entity = prepareDataForInvalidSignature();
		
		ResponseEntity<PaymentInitiationResponse> response = restTemplate.exchange(
				createURLWithPort("/v1.0.0/initiate-payment"),
				HttpMethod.POST, entity, PaymentInitiationResponse.class);
		
		assertEquals("Rejected", response.getBody().getStatus());
		
	}
	
	@Test
	public void givenInvalidRequestBody_WhenControllerIsCalled_ThenReturnsUnSuccessfulResponse() throws JsonParseException, JsonMappingException, IOException{
		
		HttpEntity<PaymentInitiationRequest> entity = prepareDataForInvalidRequestBody();
		
		ResponseEntity<PaymentInitiationResponse> response = restTemplate.exchange(
				createURLWithPort("/v1.0.0/initiate-payment"),
				HttpMethod.POST, entity, PaymentInitiationResponse.class);
		
		assertEquals(400, response.getStatusCodeValue());
		
	}
	
	
     private HttpEntity<PaymentInitiationRequest> prepareDataForSuccessfulResponse() throws JsonParseException, JsonMappingException, IOException{
		
    	String certFilePath = "classpath:ValidCertificate.txt"; 
    	String signFilePath = "classpath:ValidRequestSignature.txt"; 
    	String encodedCertificate =  TestUtils.readCertificate(certFilePath);
    	String encodedSignature = TestUtils.readSignature(signFilePath);
		final PaymentInitiationRequest request = mapper.readValue(TestUtils.readFile("test-valid-request.json"), PaymentInitiationRequest.class);
		headers.set("Signature-Certificate", encodedCertificate.trim());
		headers.set("Signature", encodedSignature.trim());
		headers.set("X-Request-Id", "29318e25-cebd-498c-888a-f77672f66449");
		headers.set("Content-Type", "application/json");
		HttpEntity<PaymentInitiationRequest> entity = new HttpEntity<>(request, headers);
		return entity;
		
	}
     
     private HttpEntity<PaymentInitiationRequest> prepareDataForInvalidCertificate() throws JsonParseException, JsonMappingException, IOException{
 		
     	String certFilePath = "classpath:InvalidCertificate.txt"; 
     	String signFilePath = "classpath:ValidRequestSignature.txt"; 
     	String encodedCertificate =  TestUtils.readCertificate(certFilePath);
     	String encodedSignature = TestUtils.readSignature(signFilePath);
 		final PaymentInitiationRequest request = mapper.readValue(TestUtils.readFile("test-valid-request.json"), PaymentInitiationRequest.class);
 		headers.set("Signature-Certificate", encodedCertificate.trim());
 		headers.set("Signature", encodedSignature.trim());
 		headers.set("X-Request-Id", "29318e25-cebd-498c-888a-f77672f66449");
 		headers.set("Content-Type", "application/json");
 		HttpEntity<PaymentInitiationRequest> entity = new HttpEntity<>(request, headers);
 		return entity;
 		
 	}
     
     private HttpEntity<PaymentInitiationRequest> prepareDataForInvalidSignature() throws JsonParseException, JsonMappingException, IOException{
  		
      	String certFilePath = "classpath:ValidCertificate.txt"; 
      	String signFilePath = "classpath:InvalidRequestSignature.txt"; 
      	String encodedCertificate =  TestUtils.readCertificate(certFilePath);
      	String encodedSignature = TestUtils.readSignature(signFilePath);
  		final PaymentInitiationRequest request = mapper.readValue(TestUtils.readFile("test-valid-request.json"), PaymentInitiationRequest.class);
  		headers.set("Signature-Certificate", encodedCertificate.trim());
  		headers.set("Signature", encodedSignature.trim());
  		headers.set("X-Request-Id", "29318e25-cebd-498c-888a-f77672f66449");
  		headers.set("Content-Type", "application/json");
  		HttpEntity<PaymentInitiationRequest> entity = new HttpEntity<>(request, headers);
  		return entity;
  		
  	}
     
     private HttpEntity<PaymentInitiationRequest> prepareDataForInvalidRequestBody() throws JsonParseException, JsonMappingException, IOException{
   		
       	String certFilePath = "classpath:ValidCertificate.txt"; 
       	String signFilePath = "classpath:ValidRequestSignature.txt"; 
       	String encodedCertificate =  TestUtils.readCertificate(certFilePath);
       	String encodedSignature = TestUtils.readSignature(signFilePath);
   		final PaymentInitiationRequest request = null;
   		headers.set("Signature-Certificate", encodedCertificate.trim());
   		headers.set("Signature", encodedSignature.trim());
   		headers.set("X-Request-Id", "29318e25-cebd-498c-888a-f77672f66449");
   		headers.set("Content-Type", "application/json");
   		HttpEntity<PaymentInitiationRequest> entity = new HttpEntity<>(request, headers);
   		return entity;
   		
   	}
     
     private String createURLWithPort(String uri) {
 		return "http://localhost:" + port + uri;
 	}
}
