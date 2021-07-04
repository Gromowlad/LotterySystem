package com.lottery.model;

public class TicketLineResult {

    private int[] numbers;
    private int lotteryLineResult;

    public TicketLineResult(int[] numbers, int result) {
        this.numbers = numbers;
        this.lotteryLineResult = result;
    }

    public Integer getLotteryLineResult() {
        return lotteryLineResult;
    }
    
    public void setLotteryLineResult(Integer lotteryLineResult) {
        this.lotteryLineResult = lotteryLineResult;
    }
}
