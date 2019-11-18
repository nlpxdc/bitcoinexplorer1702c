package io.cjf.bitcoinexplorerback.service;

import com.github.pagehelper.Page;
import io.cjf.bitcoinexplorerback.po.Transaction;

import java.util.List;

public interface TransactionService {
    void syncTransaction(String txid, Integer blockId, Long time);

    List<Transaction> getByBlockId(Integer blockId);

    Page<Transaction> getByBlockIdWithPage(Integer blockId, Integer page);

    Transaction getByTxid(String txid);

    Page<Transaction> getTransactionByAddressWithPage(String address, Integer page);
}
