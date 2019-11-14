package io.cjf.bitcoinexplorerback.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import io.cjf.bitcoinexplorerback.client.BitcoinRest;
import io.cjf.bitcoinexplorerback.dao.TransactionDetailMapper;
import io.cjf.bitcoinexplorerback.enumeration.TxDetailType;
import io.cjf.bitcoinexplorerback.po.TransactionDetail;
import io.cjf.bitcoinexplorerback.service.TransactionDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionDetailServiceImpl implements TransactionDetailService {

    @Autowired
    private BitcoinRest bitcoinRest;

    @Autowired
    private TransactionDetailMapper  transactionDetailMapper;

    @Override
    public void syncTxDetailVout(JSONObject vout, Integer transactionId) {
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

    @Override
    public void syncTxDetailVin(JSONObject vin, Integer transactionId) {
        TransactionDetail transactionDetail = new TransactionDetail();
        transactionDetail.setTransactionId(transactionId);
        transactionDetail.setType((byte)TxDetailType.Send.ordinal());
        String txidVin = vin.getString("txid");
        Integer n = vin.getInteger("vout");
        if (txidVin != null && n != null){
            JSONObject utxoJson = bitcoinRest.getUTXO(txidVin, n);
            List<JSONObject> utxos = utxoJson.getJSONArray("utxos").toJavaList(JSONObject.class);
            JSONObject utxo = utxos.get(0);
            Double amount = utxo.getDouble("value");
            transactionDetail.setAmount(-amount);
            JSONObject scriptPubKey = utxo.getJSONObject("scriptPubKey");
            JSONArray addresses = scriptPubKey.getJSONArray("addresses");
            if (addresses != null){
                String address = (String) addresses.get(0);
                transactionDetail.setAddress(address);
                transactionDetailMapper.insert(transactionDetail);
            }
        }
    }
}
