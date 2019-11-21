package io.cjf.bitcoinexplorerback.scheduler;

import com.alibaba.fastjson.JSONObject;
import io.cjf.bitcoinexplorerback.client.BitcoinRest;
import io.cjf.bitcoinexplorerback.service.TransactionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class BitcoinScheduler {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private BitcoinRest bitcoinRest;

    @Autowired
    private TransactionService transactionService;


//    @Scheduled(cron = "${bitcoin.sync.interval}")
//    public void syncData(){
//        logger.info("begin to sync bitcoin data");
//    }

    @Scheduled(cron = "${bitcoin.syncMempoolTx.interval}")
    public void syncMempoolTx(){
        transactionService.pushNewMempoolTxes();
    }

}
