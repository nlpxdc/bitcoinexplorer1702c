package io.cjf.bitcoinexplorerback.service;

import com.alibaba.fastjson.JSONObject;

public interface TransactionDetailService {
    void syncTxDetail(JSONObject vout, Integer transactionId);
}
