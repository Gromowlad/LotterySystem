package com.lottery.service;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.stereotype.Service;

import com.lottery.entity.TicketLine;
import com.lottery.model.TicketLineResult;

@Service
public class LineService {

	int[] availableLineValues = IntStream.rangeClosed(0, 2).toArray();

	public TicketLine generateLine() {
		return new TicketLine(generateTicketLineValue(), generateTicketLineValue(), generateTicketLineValue());
	}
	
    private int generateTicketLineValue() {
    	
    	Random generator = new Random();
    	int randomIndex = generator.nextInt(availableLineValues.length);
    	
    	return availableLineValues[randomIndex];
    }
    
    public List<TicketLineResult> getSortedLineResults(List<TicketLine> lines) {
    	return lines.stream()
			   .map(line -> new TicketLineResult(line.getLineValues(), this.checkLine(line.getLineValues())))
			   .sorted(Comparator.comparingInt(TicketLineResult::getLotteryLineResult).reversed())
			   .collect(Collectors.toList());
    }

    /**
     * Line checking algorithm:<br>
     * If the sum of values on the line is 2 then the result is 10<br>
     * If the numbers are the same the result is 5<br>
     * If 2nd and 3rd values are different than the 1st the result is 1<br>
     * Otherwise the result is 0
     * 
     * @param int[] linesValues
     * @return result
     */
    private int checkLine(int[] lineValues) {
        int result = 0;
        
        if (Arrays.stream(lineValues).sum() == 2) {
            result = 10;
        } 
       
        else if (Arrays.stream(lineValues).allMatch(linePredicate -> linePredicate == lineValues[0])) {
            result = 5;
        } 
        
        else if (Arrays.stream(lineValues).skip(1).noneMatch(linePredicate -> linePredicate == lineValues[0])) {
            result = 1;
        }
        
        return result;
    }
}
