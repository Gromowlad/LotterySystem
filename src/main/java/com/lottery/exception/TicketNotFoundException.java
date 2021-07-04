package com.lottery.exception;

public class TicketNotFoundException extends RuntimeException {
    
	private static final long serialVersionUID = 7934994175605857631L;

	public TicketNotFoundException(String message) {
        super(message);
    }
}
