package com.lottery.model;



import java.util.ArrayList;
import java.util.List;

import com.lottery.entity.LotteryTicket;

public class TicketStatus {

    private LotteryTicket checkedTicket;
    private List<TicketLineResult> lineResults = new ArrayList<TicketLineResult>();

    public TicketStatus(LotteryTicket checkedTicket, List<TicketLineResult> lineResults) {
        this.checkedTicket = checkedTicket;
        this.lineResults = lineResults;
    }

	public List<TicketLineResult> getLineResults() {
		return this.lineResults;
	}
	
	public LotteryTicket getCheckedTicket() {
		return this.checkedTicket;
	}
}
