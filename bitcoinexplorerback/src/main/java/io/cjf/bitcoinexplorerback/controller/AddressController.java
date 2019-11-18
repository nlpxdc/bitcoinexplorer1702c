package io.cjf.bitcoinexplorerback.controller;

import com.alibaba.fastjson.JSONObject;
import io.cjf.bitcoinexplorerback.service.TransactionDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/address")
@CrossOrigin
public class AddressController {

    @Autowired
    private TransactionDetailService transactionDetailService;

    @GetMapping("/getInfoByAddress")
    public JSONObject getInfoByAddress(@RequestParam String address){

        Integer txTotal = transactionDetailService.getTotalByAddress(address);
        Double receiveAmount = transactionDetailService.getReceiveByAddres(address);
        Double sendAmount = transactionDetailService.getSendByAddress(address);
        Double balance = receiveAmount + sendAmount;


        JSONObject addressInfoJson = new JSONObject();
        addressInfoJson.put("address", address);
        addressInfoJson.put("txSize", txTotal);
        addressInfoJson.put("totalReceived", receiveAmount);
        addressInfoJson.put("totalSent", Math.abs(sendAmount));
        addressInfoJson.put("balance", balance);

        return addressInfoJson;
    }
}
