package com.rabo.tppapi.model;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO class for exception response
 * @author Nishchal
 *
 */
@EqualsAndHashCode
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PaymentInitiationExceptionResponse {
	
	@EqualsAndHashCode.Include
    private String status;
	
	@EqualsAndHashCode.Include
	private String reason;
	
	@EqualsAndHashCode.Include
	private String reasonCode;
	
	@EqualsAndHashCode.Include
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private LocalDateTime timeStamp;
	

}
