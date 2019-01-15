package org.mengyun.tcctransaction.sample.http.order.service;

import org.mengyun.tcctransaction.api.Compensable;
import org.mengyun.tcctransaction.api.Propagation;
import org.mengyun.tcctransaction.api.TransactionContext;
import org.mengyun.tcctransaction.context.MethodTransactionContextEditor;
import org.mengyun.tcctransaction.sample.http.capital.api.CapitalTradeOrderService;
import org.mengyun.tcctransaction.sample.http.capital.api.dto.CapitalTradeOrderDto;
import org.mengyun.tcctransaction.sample.http.redpacket.api.RedPacketTradeOrderService;
import org.mengyun.tcctransaction.sample.http.redpacket.api.dto.RedPacketTradeOrderDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by changming.xie on 4/19/17.
 */
@Component
public class TradeOrderServiceProxy {

    @Autowired
    CapitalTradeOrderService capitalTradeOrderService;

    @Autowired
    RedPacketTradeOrderService redPacketTradeOrderService;

    /*the propagation need set Propagation.SUPPORTS,otherwise the recover doesn't work,
      The default value is Propagation.REQUIRED, which means will begin new transaction when recover.
    */
    @Compensable(propagation = Propagation.SUPPORTS, confirmMethod = "record", cancelMethod = "record", transactionContextEditor = MethodTransactionContextEditor.class)
    public String record(TransactionContext transactionContext, CapitalTradeOrderDto tradeOrderDto) {
    	System.out.println("+++++++++++++");
    	return capitalTradeOrderService.record(transactionContext, tradeOrderDto);
    }

    @Compensable(propagation = Propagation.SUPPORTS, confirmMethod = "record", cancelMethod = "record", transactionContextEditor = MethodTransactionContextEditor.class)
    public String record(TransactionContext transactionContext, RedPacketTradeOrderDto tradeOrderDto) {
    	System.out.println("---------");
    	return redPacketTradeOrderService.record(transactionContext, tradeOrderDto);
    }
    
    @Compensable(propagation = Propagation.SUPPORTS, confirmMethod = "record1", cancelMethod = "record1", transactionContextEditor = MethodTransactionContextEditor.class)
    public String record1(TransactionContext transactionContext, CapitalTradeOrderDto tradeOrderDto) {
    	System.out.println("+++++++++++++");
    	return capitalTradeOrderService.capiRecord(transactionContext, tradeOrderDto);
    }

    @Compensable(propagation = Propagation.SUPPORTS, confirmMethod = "record2", cancelMethod = "record2", transactionContextEditor = MethodTransactionContextEditor.class)
    public String record2(TransactionContext transactionContext, RedPacketTradeOrderDto tradeOrderDto) {
    	System.out.println("---------");
    	return redPacketTradeOrderService.redRecord(transactionContext, tradeOrderDto);
    }
    
}
