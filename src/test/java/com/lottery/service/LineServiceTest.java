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
public class LineServiceTest {

	@Autowired
	private LineService lineService;
	
    private final static Integer HIGHEST_POSSIBLE_RESULT = 10;
    private final static int LINE_LENGTH = 3;

	@Test
    public void test_generateLine_returns_ticketLine_with_correct_length() throws Exception {
		
		// Act
		TicketLine returnedTicketLine = lineService.generateLine();
		
		// Assert 
		assertEquals(LINE_LENGTH, returnedTicketLine.getLineValues().length);
    }
	
	@Test
    public void test_getSortedLineResults_returns_ticketLine_with_correct_length() throws Exception {
		
		// Arrange
		List<TicketLine> lines = new ArrayList<>();
		
		// result of 5
	    TicketLine line1 = new TicketLine(1, 1, 1);
	    
	    // result of 10 (highest possible)
	    TicketLine line2 = new TicketLine(0, 1, 1);
		
	    lines.add(line1);
	    lines.add(line2);
	    
		// Act
		List<TicketLineResult> returnedTicketLineResults = lineService.getSortedLineResults(lines);
		
		// Assert
		assertEquals(HIGHEST_POSSIBLE_RESULT, returnedTicketLineResults.get(0).getLotteryLineResult());
    }

}
