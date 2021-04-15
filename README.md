# Third Party Payment Processor API
Third Party Payment processor project

## Introduction

We are opening up payment initiation API for third party
payment providers (TPPs). For testing purpose, we also provide a
sandbox environment to TPPs. The API definition is provided by the
Open API format (see [api-definition.yaml](./api-definition.yaml).
TPPs shall implement their application according to the definition,
so the application must be compiliant to it.

#Functionality:

Application which confirms the API definition.
The application doesn't create actual payments but validating the incoming
requests from TPPs, check amount limit and respond back the defined response.

When the validation failed, then the application must return HTTP status code
`400`. If the amount limit check failed, then HTTP status code `422` will be
returned. If the request passes the all validations and amount check, then the
application must return HTTP code `201`. The `paymentId` must be generated on
the application.

# Validations
-----------

These are the validations need to be in place.

- White listed certificates validation 
- Signature validation
- Request validation

# Prerequisites:
a. Java 8
b. Maven

# Running the project:
a.In root folder, do:<pre>mvn clean install</pre>
b.After successful build, execute <pre>mvn spring-boot:run</pre>
c.Server Port Configured <pre>8443</pre>

# Design Overview:
1. Separation of concern has been used for designing the layered architecture.
2. All the classes with similar concerns have been grouped same package forming an layered architecture.
3. All the classes have single responsibility and all the classes which had multiple responsibility for been separated accordingly.
4. Interface has been created and can be segregated in future to add more functionality through implementation classes.
5. Inversion of control(DI) has been used so that all the layers are loosely coupled so that implementation classes can be easily changed in future if required & also easy  to write test cases.
6. While implementing this functionality Test Driven Approach has been followed.
7. Integration Test and Unit test has been implemented.

# Implementation Overview:
This application exposes below end point url

    POST:/v1.0.0/initiate-payment	
    This end point returns either successful or error result after validation based on input.
    Example : http://localhost:8443/v1.0.0/initiate-payment
    Request :      
    {
    "debtorIBAN":"NL02RABO7134384551",
    "creditorIBAN":"NL94ABNA1008270121",
    "amount":"1.00",
    "currency":"EUR",
    "endToEndId": "XRG1BIWC4OC5E8BSYBLQ"
    }
    Response for Success scenario : 
    {
    "paymentId": "XJ9NAEEOHJJQFSJRCGTN",
    "status": "Accepted"
     }
                   
    Sample Response for different Error scenario : 
    
     {
    "timestamp": "2021-04-15T15:26:39.855+00:00",
    "status": 404,
    "error": "Not Found",
    "message": "No message available",
    "path": "/v1.0.0/initiate-paymen"
     }  
    
    
    {
    "status": "Rejected",
    "reason": "General_Error",
    "reasonCode": "500",
    "timeStamp": "2021-04-15T17:27:44.686"
    } 
    
    {
    "status": "Rejected",
    "reason": "Invalid_Request",
    "reasonCode": "500",
    "timeStamp": "2021-04-16T01:03:02.342"
     }
                   


