package io.cjf.bitcoinexplorerback.controller;

import com.alibaba.fastjson.JSONObject;
import io.cjf.bitcoinexplorerback.service.TransactionDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/address")
public class AddressController {

    @Autowired
    private TransactionDetailService transactionDetailService;

    @GetMapping("/getInfoByAddress")
    public JSONObject getInfoByAddress(@RequestParam String address){

        Integer txTotal = transactionDetailService.getTotalByAddress(address);

        JSONObject addressInfoJson = new JSONObject();
        addressInfoJson.put("txSize", txTotal);

        return addressInfoJson;
    }
}
