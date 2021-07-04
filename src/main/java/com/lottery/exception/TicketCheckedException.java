package com.lottery.exception;

public class TicketCheckedException extends RuntimeException {

	private static final long serialVersionUID = -6501674623876454635L;
	
	public TicketCheckedException(String message) {
		super(message);
	}
}
