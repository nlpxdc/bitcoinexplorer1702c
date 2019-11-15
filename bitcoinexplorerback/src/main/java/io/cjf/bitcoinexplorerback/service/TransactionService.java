package io.cjf.bitcoinexplorerback.service;

import io.cjf.bitcoinexplorerback.po.Transaction;

import java.util.List;

public interface TransactionService {
    void syncTransaction(String txid, Integer blockId, Long time);

    List<Transaction> getByBlockId(Integer blockId);
}
