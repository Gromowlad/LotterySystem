package com.lottery.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.lottery.entity.LotteryTicket;

@Repository
public interface LotteryTicketRepository extends CrudRepository<LotteryTicket, Long>  {
}
