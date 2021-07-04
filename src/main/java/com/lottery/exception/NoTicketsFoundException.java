package com.lottery.exception;

public class NoTicketsFoundException extends RuntimeException {

	private static final long serialVersionUID = 4803397460399570354L;

	public NoTicketsFoundException(String message) {
		super(message);
	}
}
