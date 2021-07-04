package com.lottery.controller;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.lottery.entity.LotteryTicket;
import com.lottery.entity.TicketLine;
import com.lottery.model.TicketLineResult;
import com.lottery.model.TicketStatus;
import com.lottery.service.LotteryService;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class LotteryControllerTest {

	@InjectMocks
	private LotteryController controller;
	
	@Mock
	private LotteryService lotteryService; 
	
	@Autowired
	WebApplicationContext wac;

	@Autowired
	private MockMvc mockMvc;
	
	@Before
	public void setup() throws Exception {
		
		MockitoAnnotations.openMocks(this);

		this.mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

	}
	
    private final static String NUMBER_OF_LINES_PARAMETER = "numberOfLines";
    private final static Integer NUMBER_OF_LINES = 2;
    
    private final static String TICKET_ID_PARAMETER = "ticketId";
    private final static Long TICKET_ID = 1L;
    
    

	
	@Test
    public void test_createTicket_returns_created_ticket() throws Exception {
		
		// Arrange
		LotteryTicket ticket = new LotteryTicket();
		
		TicketLine line1 = new TicketLine();
		TicketLine line2 = new TicketLine();
		
		ticket.addLine(line1);
		ticket.addLine(line2);

		when(lotteryService.createTicket(NUMBER_OF_LINES)).thenReturn(ticket);
		
		// Act
		MvcResult requestResult = mockMvc.perform(MockMvcRequestBuilders.post("/lottery/ticket/create")
				.queryParam(NUMBER_OF_LINES_PARAMETER, new String[]{NUMBER_OF_LINES.toString()}))
				.andReturn();
		
		// Assert 
		assertEquals(HttpStatus.OK.value(), requestResult.getResponse().getStatus());
		assertEquals(NUMBER_OF_LINES, ticket.getLines().size());
				
    }
	
	@Test
    public void test_getTicket_returns_valid_ticket() throws Exception {
		
		// Arrange
		LotteryTicket ticket = new LotteryTicket();
		ticket.setId(TICKET_ID);
		
		when(lotteryService.getTicket(TICKET_ID)).thenReturn(ticket);
		
		// Act
		MvcResult requestResult = mockMvc.perform(MockMvcRequestBuilders.get("/lottery/ticket?" + TICKET_ID_PARAMETER + "=" + TICKET_ID))
								  .andReturn();
		
		// Assert 
		assertEquals(HttpStatus.OK.value(), requestResult.getResponse().getStatus());
		assertEquals(TICKET_ID, ticket.getId());
    }
	
	@Test
    public void test_amendLinesForTicket_returns_valid_number_of_lines() throws Exception {
		
		// Arrange
		LotteryTicket originalTicket = new LotteryTicket();
		TicketLine line1 = new TicketLine();
		TicketLine line2 = new TicketLine();
		
		originalTicket.addLine(line1);
		originalTicket.addLine(line2);
		originalTicket.uncheckTicket();
		originalTicket.setId(TICKET_ID);
		
		LotteryTicket amendedTicket = new LotteryTicket();
		TicketLine addedLine1 = new TicketLine();
		TicketLine addedLine2 = new TicketLine();
		
		amendedTicket.addLine(line1);
		amendedTicket.addLine(line2);
		amendedTicket.addLine(addedLine1);
		amendedTicket.addLine(addedLine2);

		when(lotteryService.amendLinesForTicket(originalTicket.getId(), NUMBER_OF_LINES)).thenReturn(amendedTicket);
		when(lotteryService.getTicket(TICKET_ID)).thenReturn(originalTicket);

		// Act
		MvcResult requestResult = mockMvc.perform(MockMvcRequestBuilders.put("/lottery/ticket/amend?" +
								 TICKET_ID_PARAMETER + "=" + TICKET_ID + "&" + NUMBER_OF_LINES_PARAMETER + "=" + NUMBER_OF_LINES))
								 .andReturn();
		
		// Assert 
		assertEquals((originalTicket.getLines().size() + NUMBER_OF_LINES), amendedTicket.getLines().size());
    }
	
	@Test
    public void test_getAllTickets_returns_ticket_list() throws Exception {
		
		// Arrange
		List<LotteryTicket> tickets = new ArrayList<LotteryTicket>();
		LotteryTicket ticket1 = new LotteryTicket();
		LotteryTicket ticket2 = new LotteryTicket();
		
		tickets.add(ticket1);
		tickets.add(ticket2);
		
		when(lotteryService.getAllTickets()).thenReturn(tickets);
		
		// Act
		MvcResult requestResult = mockMvc.perform(MockMvcRequestBuilders.get("/lottery/ticket")).andReturn();
		
		// Assert
		assertEquals(HttpStatus.OK.value(), requestResult.getResponse().getStatus());
		assertEquals(2, tickets.size());
    }
	
	@Test
    public void test_checkTicket_returns_valid_ticket_status() throws Exception {
		
		// Arrange
		LotteryTicket ticket = new LotteryTicket();
	    List<TicketLineResult> lineResults = new ArrayList<TicketLineResult>();

	    TicketLineResult lineResult = new TicketLineResult(new int[]{0, 1, 1}, 10);
	    lineResults.add(lineResult);
	    
		TicketStatus ticketStatus = new TicketStatus(ticket, lineResults);

		when(lotteryService.checkTicket(TICKET_ID)).thenReturn(ticketStatus);
		
		// Act
		MvcResult requestResult = mockMvc.perform(MockMvcRequestBuilders.put("/lottery/ticket/check?" + TICKET_ID_PARAMETER + "=" + TICKET_ID)).andReturn();
		
		// Assert
		assertEquals(HttpStatus.OK.value(), requestResult.getResponse().getStatus());
		assertTrue(ticketStatus.getLineResults().contains(lineResult));
    }
}
