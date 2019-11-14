package io.cjf.bitcoinexplorerback.service;

public interface TransactionService {
    void syncTransaction(String txid, Integer blockId, Long time);
}
