package io.cjf.bitcoinexplorerback.scheduler;

import com.alibaba.fastjson.JSONObject;
import io.cjf.bitcoinexplorerback.client.BitcoinRest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Component
public class BitcoinScheduler {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private BitcoinRest bitcoinRest;

    private JSONObject originMempoolTx = new JSONObject();

    private List<JSONObject> deltaTx = new LinkedList<>();

//    @Scheduled(cron = "${bitcoin.sync.interval}")
//    public void syncData(){
//        logger.info("begin to sync bitcoin data");
//    }

    @Scheduled(cron = "${bitcoin.syncMempoolTx.interval}")
    public void syncMempoolTx(){
        logger.info("begin sync mempool tx");

        JSONObject newMempoolTx = bitcoinRest.getMempoolContents();

        for (Map.Entry<String, Object> entry : newMempoolTx.entrySet()) {
            String key = entry.getKey();
            if (!originMempoolTx.containsKey(key)){
                JSONObject addJson = newMempoolTx.getJSONObject(key);
                addJson.put("txid", key);
                deltaTx.add(addJson);
            }
        }

        //todo push delta tx
        logger.info("delta tx: {}", deltaTx);
        logger.info("delta size: {}", deltaTx.size());

        deltaTx = new LinkedList<>();
        originMempoolTx = newMempoolTx;

        logger.info("end sync mempool tx");
    }

}
