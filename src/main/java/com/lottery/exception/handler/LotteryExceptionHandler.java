package com.lottery.exception.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.lottery.exception.TicketCheckedException;
import com.lottery.exception.TicketNotFoundException;
import com.lottery.exception.NoTicketsFoundException;

@ControllerAdvice
public class LotteryExceptionHandler {
	
    private static final Logger logger = LoggerFactory.getLogger(LotteryExceptionHandler.class);

    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Value for the parameter(s) has not been provided.")
    public void missingParameter(MissingServletRequestParameterException ex) {
    	logger.error("MissingServletRequestParameterException occured: ", ex);
    };
    
    @ExceptionHandler(TicketNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Ticket not found.")
    public void ticketNotFound(TicketNotFoundException ex) {
    	logger.error("TicketNotFoundException occured: ", ex);
    }

    @ExceptionHandler(TicketCheckedException.class)
    @ResponseStatus(value = HttpStatus.METHOD_NOT_ALLOWED, reason = "Ticket was already checked.")
    public void ticketAlreadyChecked(TicketCheckedException ex) {
    	logger.error("TicketCheckedException occured: ", ex);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Provided ticket ID or line numbers are invalid.")
    public void invalidArgumentProvided(IllegalArgumentException ex) {
    	logger.error("IllegalArgumentException occured: ", ex);
    };
    
    @ExceptionHandler(NoTicketsFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "No tickets were found.")
    public void noTicketsFound(NoTicketsFoundException ex) {
    	logger.error("TicketsNotFoundException occured: ", ex);
    }
    
}
