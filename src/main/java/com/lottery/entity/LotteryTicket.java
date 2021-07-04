package com.lottery.entity;


import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.springframework.util.CollectionUtils;

@Entity
public class LotteryTicket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<TicketLine> lines;

    private boolean isChecked = false;

    public LotteryTicket() {
    }
    
    public LotteryTicket(List<TicketLine> lines) {
        this.lines = lines;
    }

    public long getId() {
        return this.id;
    }
    
    public void setId(long id) {
        this.id = id;
    }
    
    /**
     * Add a line to the ticket
     * 
     * @param TicketLine
     */
    public void addLine(TicketLine line) {
    	
    	if (CollectionUtils.isEmpty(lines)) {
    		lines = new ArrayList<>();
    	}
    	
        this.lines.add(line);
    }

    /**
     * Get the lines of the ticket
     * 
     * @return List<TicketLine>
     */
    public List<TicketLine> getLines() {
        return this.lines;
    }

    /**
     * Mark the ticket as checked
     * 
     */
    public void checkTicket() {
        this.isChecked = true;
    }
    
    /**
     * Mark the ticket as unchecked
     * 
     */
    public void uncheckTicket() {
        this.isChecked = false;
    }

    /**
     * See if the ticket has already been checked
     * 
     * @return boolean isChecked
     */
    public boolean isChecked() {
        return this.isChecked;
    }
}
