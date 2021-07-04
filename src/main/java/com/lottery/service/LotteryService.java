package com.lottery.service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lottery.entity.LotteryTicket;
import com.lottery.exception.NoTicketsFoundException;
import com.lottery.exception.TicketCheckedException;
import com.lottery.exception.TicketNotFoundException;
import com.lottery.model.TicketStatus;
import com.lottery.repository.LotteryTicketRepository;

@Service
public class LotteryService {

    @Autowired
    TicketService ticketService;
    
    @Autowired
    LotteryTicketRepository lotteryTicketRepository;

    /**
     * Create a lottery ticket with the provided number of lines
     * 
     * @param numberOfLines
     * @return LotteryTicket
     * @throws IllegalArgumentException if number of lines is either not provided or smaller than 1
     */
    public LotteryTicket createTicket(Integer numberOfLines) throws IllegalArgumentException {
        
    	if (numberOfLines == null || numberOfLines <= 0) {
        	throw new IllegalArgumentException("Invalid number of lines: " + numberOfLines);
        }
    	
        LotteryTicket lotteryTicket = ticketService.generateTicket(numberOfLines);
        lotteryTicketRepository.save(lotteryTicket);
        
        return lotteryTicket;
    }
    
    /**
     * Get a ticket for the provided ID
     * 
     * @return LotteryTicket
     * @throws TicketNotFoundException if ticket for the provided ID was not found
     */
    public LotteryTicket getTicket(Long ticketId) throws TicketNotFoundException {

        return lotteryTicketRepository
				   .findById(ticketId)
	               .orElseThrow(() -> new TicketNotFoundException("Ticket with ID " + ticketId + " was not found."));
    }


    /**
     * Amend given ticket with additional lines
     * 
     * @param ticketId
     * @param numberOfLines to be added
     * @return LotteryTicket with additional lines
     * 
     * @throws IllegalArgumentException if number of lines <strong>or</strong> ticket ID is either not provided or smaller than 1
     * @throws TicketNotFoundException if the ticket for the provided ID was not found
     * @throws TicketCheckedException if ticket has been already checked
     */
    public LotteryTicket amendLinesForTicket(Long ticketId, Integer numberOfLines) throws IllegalArgumentException, TicketNotFoundException, TicketCheckedException {
        
    	if (ticketId == null || ticketId <= 0) {
        	throw new IllegalArgumentException("Invalid ticket ID: " + ticketId);
        }
        
    	if (numberOfLines == null || numberOfLines <= 0) {
        	throw new IllegalArgumentException("Invalid number of lines: " + numberOfLines);
        }
        
        LotteryTicket ticket = this.getTicket(ticketId);
        
		if (ticket.isChecked()) {
			throw new TicketCheckedException("Ticket with ID " + ticketId + " was already checked.");
		}
		
		ticket = ticketService.amendLines(ticket, numberOfLines);
		
		lotteryTicketRepository.save(ticket);
        
        return ticket;
    }

    /**
     * Check ticket with the given ID
     * 
     * @param ticketId
     * @return TicketStatus
     * @throws TicketNotFoundException if ticket with the provided ID has not been found
     */
    public TicketStatus checkTicket(Long ticketId) throws TicketNotFoundException {
        
    	if (ticketId == null || ticketId <= 0) {
        	throw new IllegalArgumentException("Invalid ticket ID: " + ticketId);
        }
                
        LotteryTicket ticket = this.getTicket(ticketId);
        
        ticket.checkTicket();
        
        lotteryTicketRepository.save(ticket);
                
        return ticketService.getTicketStatus(ticket);
    }
    
    /**
     * Return list of all tickets
     * 
     * @return List<LotteryTicket>
     * @throws NoTicketsFoundException if no tickets were found
     */
    public List<LotteryTicket> getAllTickets() throws NoTicketsFoundException {
        
    	List<LotteryTicket> tickets = StreamSupport
									  .stream(lotteryTicketRepository.findAll().spliterator(), false)
									  .collect(Collectors.toList());
    	
    	if(tickets.isEmpty()) {
    		throw new NoTicketsFoundException("No tickets were found.");
    	}
    	
        return tickets;
    }
}
