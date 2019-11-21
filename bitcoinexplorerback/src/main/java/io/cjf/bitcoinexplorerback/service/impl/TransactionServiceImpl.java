package io.cjf.bitcoinexplorerback.service.impl;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import io.cjf.bitcoinexplorerback.client.BitcoinRest;
import io.cjf.bitcoinexplorerback.constants.PageConfig;
import io.cjf.bitcoinexplorerback.dao.TransactionMapper;;
import io.cjf.bitcoinexplorerback.enumeration.TxDetailType;
import io.cjf.bitcoinexplorerback.po.Transaction;
import io.cjf.bitcoinexplorerback.po.TransactionDetail;
import io.cjf.bitcoinexplorerback.service.TransactionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class TransactionServiceImpl implements TransactionService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private BitcoinRest bitcoinRest;

    @Autowired
    private TransactionMapper transactionMapper;

    @Autowired
    private TransactionDetailServiceImpl transactionDetailService;

    private JSONObject originMempoolTx = new JSONObject();

    private List<JSONObject> deltaTxes = new LinkedList<>();

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @Override
    public void syncTransaction(String txid, Integer blockId, Long time) {
        JSONObject transactionJson = bitcoinRest.getTransaction(txid);
        Transaction transaction = new Transaction();
        //set amount
        transaction.setBlockId(blockId);
        transaction.setSizeondisk(transactionJson.getInteger("size"));
        transaction.setStatus((byte)0);
        transaction.setTime(time);
        transaction.setTxhash(transactionJson.getString("hash"));
        transaction.setTxid(transactionJson.getString("txid"));
        transaction.setWeight(transactionJson.getInteger("weight"));

        transactionMapper.insert(transaction);

        Integer transactionId = transaction.getTransactionId();

        List<JSONObject> vouts = transactionJson.getJSONArray("vout").toJavaList(JSONObject.class);
        for (JSONObject vout : vouts) {
            transactionDetailService.syncTxDetailVout(vout, transactionId);
        }

        //todo insert tx detail vin
        List<JSONObject> vins = transactionJson.getJSONArray("vin").toJavaList(JSONObject.class);
        for (JSONObject vin : vins) {
            transactionDetailService.syncTxDetailVin(vin, transactionId);
        }

    }

    @Override
    public List<Transaction> getByBlockId(Integer blockId) {
        List<Transaction> transactions = transactionMapper.selectByBlockId(blockId);
        return transactions;
    }

    @Override
    public Page<Transaction> getByBlockIdWithPage(Integer blockId, Integer page) {
        PageHelper.startPage(page, PageConfig.PAGE_SIZE);
        Page<Transaction> transactions = transactionMapper.selectByBlockIdWithPage(blockId);
        return transactions;
    }

    @Override
    public Transaction getByTxid(String txid) {
        Transaction transaction = transactionMapper.selectByTxid(txid);
        return transaction;
    }

    @Override
    public Page<Transaction> getTransactionByAddressWithPage(String address, Integer page) {
        PageHelper.startPage(page, PageConfig.PAGE_SIZE);
        Page<Transaction> transactions = transactionMapper.selectTransactionByAddress(address);
        return transactions;
    }

    @Override
    public void pushNewMempoolTxes() {

        logger.info("begin sync mempool tx");

        JSONObject newMempoolTx = bitcoinRest.getMempoolContents();

        int originSize = originMempoolTx.size();
        int newSize = newMempoolTx.size();
        if (newSize <= originSize){
            return ;
        }

        for (Map.Entry<String, Object> entry : newMempoolTx.entrySet()) {
            String key = entry.getKey();
            if (!originMempoolTx.containsKey(key)){
                JSONObject addJson = newMempoolTx.getJSONObject(key);
                addJson.put("txid", key);
                deltaTxes.add(addJson);
            }
        }

        //todo push delta tx
        logger.info("delta tx: {}", deltaTxes);
        logger.info("delta size: {}", deltaTxes.size());

        List<JSONObject> deltaTxesJsons = deltaTxes.stream().map(t -> {
            JSONObject tJson = new JSONObject();
            tJson.put("txid", t.getString("txid"));
            tJson.put("wtxid", t.getString("wtxid"));
            tJson.put("time", t.getLong("time"));
            //todo calculate amount

            return tJson;
        }).collect(Collectors.toList());
        List<JSONObject> sortedDeltaTxesJsons = deltaTxesJsons.stream().sorted((t1, t2) -> {
            return (int)(t2.getLong("time") - t1.getLong("time"));
        }).collect(Collectors.toList());
        simpMessagingTemplate.convertAndSend("/bitcoin/deltaTx", sortedDeltaTxesJsons);

        deltaTxes = new LinkedList<>();
        originMempoolTx = newMempoolTx;

        logger.info("end sync mempool tx");

    }
}
