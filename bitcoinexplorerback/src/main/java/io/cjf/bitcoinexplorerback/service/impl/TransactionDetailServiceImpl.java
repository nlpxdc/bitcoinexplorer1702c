package io.cjf.bitcoinexplorerback.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import io.cjf.bitcoinexplorerback.dao.TransactionDetailMapper;
import io.cjf.bitcoinexplorerback.enumeration.TxDetailType;
import io.cjf.bitcoinexplorerback.po.TransactionDetail;
import io.cjf.bitcoinexplorerback.service.TransactionDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionDetailServiceImpl implements TransactionDetailService {

    @Autowired
    private TransactionDetailMapper  transactionDetailMapper;

    @Override
    public void syncTxDetail(JSONObject vout, Integer transactionId) {
        TransactionDetail transactionDetail = new TransactionDetail();
        JSONObject scriptPubKey = vout.getJSONObject("scriptPubKey");
        JSONArray addresses = scriptPubKey.getJSONArray("addresses");
        if (addresses != null){
            String address = (String) addresses.get(0);
            transactionDetail.setAddress(address);
            transactionDetail.setAmount(vout.getDouble("value"));
            transactionDetail.setType((byte) TxDetailType.Receive.ordinal());
            transactionDetail.setTransactionId(transactionId);

            transactionDetailMapper.insert(transactionDetail);
        }
    }
}
