package com.lottery.service;


import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.lottery.entity.LotteryTicket;
import com.lottery.entity.TicketLine;
import com.lottery.model.TicketLineResult;
import com.lottery.model.TicketStatus;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TicketServiceTest {

	@Autowired
	private TicketService ticketService;
	
    private final static Integer NUMBER_OF_LINES = 2;
	
	@Test
    public void test_generateTicket_returns_lotteryTicket_with_correct_number_of_lines() throws Exception {
		
		// Act
		LotteryTicket returnedTicket = ticketService.generateTicket(NUMBER_OF_LINES);
		
		// Assert 
		assertEquals(NUMBER_OF_LINES, returnedTicket.getLines().size());
    }
	
	@Test
    public void test_generateLines_returns_correct_number_of_lines() throws Exception {
		
		// Act
		List<TicketLine> returnedLines = ticketService.generateLines(NUMBER_OF_LINES);
		
		// Assert 
		assertEquals(NUMBER_OF_LINES, returnedLines.size());
    }
	
	@Test
    public void test_amendLines_returns_lotteryTicket_with_correct_number_of_lines() throws Exception {
		
		// Arrange
		LotteryTicket ticket = new LotteryTicket();
		
		// Act
		LotteryTicket returnedTicket = ticketService.amendLines(ticket, NUMBER_OF_LINES);
		
		// Assert 
		assertEquals(NUMBER_OF_LINES, returnedTicket.getLines().size());
    }
	
	@Test
    public void test_getTicketStatus_returns_correct_ticket_status() throws Exception {
		
		// Arrange	
		LotteryTicket ticket = new LotteryTicket();
	    TicketLine line1 = new TicketLine(0, 1, 1);
	    TicketLine line2 = new TicketLine(1, 1, 1);
	    
	    ticket.addLine(line1);
	    ticket.addLine(line2);
	    
	    List<TicketLineResult> lineResults = new ArrayList<TicketLineResult>();
	    
	    TicketLineResult result1 = new TicketLineResult(line1.getLineValues(), 10);
	    TicketLineResult result2 = new TicketLineResult(line2.getLineValues(), 5);
	    
	    lineResults.add(result1);
	    lineResults.add(result2);

		// Act
		TicketStatus returnedTicketStatus = ticketService.getTicketStatus(ticket);
		
		// Assert 
		assertEquals(ticket, returnedTicketStatus.getCheckedTicket());
    }
	

}
