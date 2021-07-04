package com.lottery.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lottery.entity.LotteryTicket;
import com.lottery.entity.TicketLine;
import com.lottery.model.TicketStatus;
import com.lottery.repository.LotteryTicketRepository;

@Service
public class TicketService {

	@Autowired
	LineService lineService;
	
    public LotteryTicket generateTicket(int numOfLines){
        return new LotteryTicket(this.generateLines(numOfLines));
    }
    
    public List<TicketLine> generateLines(int numOfLines) {
    	
    	List<TicketLine> lines = new ArrayList<TicketLine>(numOfLines);
        
        for (int i = 0; i < numOfLines; i++){
            lines.add(lineService.generateLine());
        }
        
        return lines;
    }
    
    public LotteryTicket amendLines(LotteryTicket ticket, int numOfLines) {
		
    	for (TicketLine generatedLine : this.generateLines(numOfLines)) {
			ticket.addLine(generatedLine);
		}
    	
    	return ticket;
    }
    
    public TicketStatus getTicketStatus(LotteryTicket ticket) {
    	return new TicketStatus(ticket, lineService.getSortedLineResults(ticket.getLines()));
    }


}
