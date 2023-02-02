package com.dat.whm.exception;

import org.springframework.transaction.TransactionSystemException;

public class SystemException extends TransactionSystemException {
	private String errorCode;
	private Object response;

	public SystemException(String errorCode, String message) {
		super(message);
		this.errorCode = errorCode;
	}

	public SystemException(String errorCode, String message, Throwable throwable) {
		super(message, throwable);
		this.errorCode = errorCode;
	}

	public SystemException(String errorCode, Object response, String message) {
		super(message);
		this.errorCode = errorCode;
		this.response = response;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public Object getResponse() {
		return response;
	}
}
