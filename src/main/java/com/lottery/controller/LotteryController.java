package com.lottery.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.lottery.entity.LotteryTicket;
import com.lottery.model.TicketStatus;
import com.lottery.service.LotteryService;

@RestController
@RequestMapping("/lottery")
public class LotteryController {
	
	@Autowired
	LotteryService lotteryService;
	
    @PostMapping(value = "/ticket/create", params = "numberOfLines")
    @ResponseBody
    LotteryTicket createTicket(Integer numberOfLines) {
    	return this.lotteryService.createTicket(numberOfLines);
    }
    
    @GetMapping(value = "/ticket", params = "ticketId")
    @ResponseBody
    LotteryTicket getTicket(Long ticketId) {
    	return this.lotteryService.getTicket(ticketId);
    }

    @PutMapping(value = "/ticket/amend", params = {"ticketId", "numberOfLines"})
    @ResponseBody
    LotteryTicket amendLinesForTicket(Long ticketId, Integer numberOfLines) {
    	return this.lotteryService.amendLinesForTicket(ticketId, numberOfLines);
    }
    
    @GetMapping(value = "/ticket")
    @ResponseBody
    List<LotteryTicket> getAllTickets() {
    	return this.lotteryService.getAllTickets();
    }
 
    @PutMapping(value = "/ticket/check", params = "ticketId")
    @ResponseBody
    TicketStatus checkTicket(Long ticketId) {
    	return this.lotteryService.checkTicket(ticketId);
    }
}
