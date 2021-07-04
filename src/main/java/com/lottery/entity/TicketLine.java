package com.lottery.entity;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class TicketLine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private int[] lineValues = new int[3];
    
    public TicketLine() {
    }

    public TicketLine(int value1, int value2, int value3){
        lineValues[0] = value1;
        lineValues[1] = value2;
        lineValues[2] = value3;
    }	
    
    public int[] getLineValues() {
    	return this.lineValues;
    }
}
