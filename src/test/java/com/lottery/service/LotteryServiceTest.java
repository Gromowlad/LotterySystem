package com.lottery.service;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Rule;
import org.junit.jupiter.api.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.lottery.entity.LotteryTicket;
import com.lottery.entity.TicketLine;
import com.lottery.exception.NoTicketsFoundException;
import com.lottery.exception.TicketNotFoundException;
import com.lottery.model.TicketLineResult;
import com.lottery.model.TicketStatus;
import com.lottery.repository.LotteryTicketRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LotteryServiceTest {

	@Autowired
	private LotteryService lotteryService; 
	
    @MockBean
    TicketService ticketService;
    
    @MockBean
    LotteryTicketRepository lotteryTicketRepository;
	
    private final static Integer NUMBER_OF_LINES = 2;
    private final static Long TICKET_ID = 1L;
	
	@Test
    public void test_createTicket_returns_correct_created_ticket() throws Exception {
		
		// Arrange
		LotteryTicket ticket = new LotteryTicket();
		
		TicketLine line1 = new TicketLine();
		TicketLine line2 = new TicketLine();
		
		ticket.addLine(line1);
		ticket.addLine(line2);

		when(ticketService.generateTicket(NUMBER_OF_LINES)).thenReturn(ticket);
		
		// Act
		LotteryTicket returnedTicket = lotteryService.createTicket(NUMBER_OF_LINES);
		
		// Assert 
		assertEquals(NUMBER_OF_LINES, returnedTicket.getLines().size());			
    }
	
	@Test
    public void test_createTicket_throws_IllegalArgumentException() {
	   
		assertThrows(IllegalArgumentException.class, () ->  lotteryService.createTicket(null), "Invalid number of lines: ");
    }
	
	@Test
    public void test_getTicket_returns_correct_ticket() throws Exception {
		
		// Arrange
		LotteryTicket ticket = new LotteryTicket();
		ticket.setId(TICKET_ID);

		when(lotteryTicketRepository.findById(TICKET_ID)).thenReturn(Optional.of(ticket));
		
		// Act
		LotteryTicket returnedTicket = lotteryService.getTicket(TICKET_ID);
		
		// Assert 
		assertEquals(ticket.getId(), returnedTicket.getId());			
    }
	
	@Test
    public void test_getTicket_throws_TicketNotFoundException() {
	   
		assertThrows(TicketNotFoundException.class, () ->  lotteryService.getTicket(null), "Ticket with ID  was not found.");
    }
	
	@Test
    public void test_amendLinesForTicket_returns_correct_amended_ticket() throws Exception {
		
		// Arrange
		LotteryTicket originalTicket = new LotteryTicket();
		originalTicket.setId(TICKET_ID);
		
		TicketLine line1 = new TicketLine();
		TicketLine line2 = new TicketLine();
		
		originalTicket.addLine(line1);
		originalTicket.addLine(line2);

		LotteryTicket amendedTicket = new LotteryTicket();
		amendedTicket.setId(TICKET_ID);
		
		TicketLine addedLine1 = new TicketLine();
		TicketLine addedLine2 = new TicketLine();
		
		amendedTicket.setId(TICKET_ID);
		amendedTicket.addLine(line1);
		amendedTicket.addLine(line2);
		amendedTicket.addLine(addedLine1);
		amendedTicket.addLine(addedLine2);

		when(lotteryTicketRepository.findById(TICKET_ID)).thenReturn(Optional.of(originalTicket));
		when(ticketService.amendLines(originalTicket, NUMBER_OF_LINES)).thenReturn(amendedTicket);
		
		// Act
		LotteryTicket returnedTicket = lotteryService.amendLinesForTicket(TICKET_ID, NUMBER_OF_LINES);
		
		// Assert 
		assertEquals((originalTicket.getLines().size() + NUMBER_OF_LINES), returnedTicket.getLines().size());
    }
	
	@Test
    public void test_amendLinesForTicket_throws_IllegalArgumentException() {
	   
		assertThrows(IllegalArgumentException.class, () ->  lotteryService.amendLinesForTicket(null, NUMBER_OF_LINES), "Invalid ticket ID: ");
    }
	
	@Test
    public void test_checkTicket_returns_correct_ticket_status() throws Exception {
		
		// Arrange
		LotteryTicket ticket = new LotteryTicket();
	    List<TicketLineResult> lineResults = new ArrayList<TicketLineResult>();

	    TicketLineResult lineResult = new TicketLineResult(new int[]{0, 1, 1}, 10);
	    lineResults.add(lineResult);
	    
		TicketStatus ticketStatus = new TicketStatus(ticket, lineResults);

		when(lotteryTicketRepository.findById(TICKET_ID)).thenReturn(Optional.of(ticket));
		when(ticketService.getTicketStatus(ticket)).thenReturn(ticketStatus);

		// Act
		TicketStatus returnedTicketStatus = lotteryService.checkTicket(TICKET_ID);
		
		// Assert 
		assertTrue(returnedTicketStatus.getLineResults().contains(lineResult));
    }
	
	@Test
    public void test_checkTicket_throws_IllegalArgumentException() {
	   
		assertThrows(IllegalArgumentException.class, () ->  lotteryService.checkTicket(null), "Invalid ticket ID: ");
    }
	
	@Test
    public void test_getAllTickets_returns_list_of_all_tickets() throws Exception {
		
		// Arrange
		List<LotteryTicket> tickets = new ArrayList<LotteryTicket>();
		LotteryTicket ticket1 = new LotteryTicket();
		LotteryTicket ticket2 = new LotteryTicket();
		
		tickets.add(ticket1);
		tickets.add(ticket2);
		
		Iterable<LotteryTicket> ticketsIterable = tickets;

		when(lotteryTicketRepository.findAll()).thenReturn(ticketsIterable);

		// Act
		List<LotteryTicket> returnedTickets = lotteryService.getAllTickets();
		
		// Assert 
		assertEquals(tickets, returnedTickets);
    }
	
	@Test
    public void test_getAllTickets_throws_NoTicketsFoundException() {
		
	    assertThrows(NoTicketsFoundException.class, () -> lotteryService.getAllTickets(), "No tickets were found.");
    }

}
