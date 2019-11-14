package io.cjf.bitcoinexplorerback.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import io.cjf.bitcoinexplorerback.client.BitcoinRest;
import io.cjf.bitcoinexplorerback.dao.BlockMapper;
import io.cjf.bitcoinexplorerback.dao.TransactionMapper;
import io.cjf.bitcoinexplorerback.po.Block;
import io.cjf.bitcoinexplorerback.po.Transaction;
import io.cjf.bitcoinexplorerback.service.BlockService;
import io.cjf.bitcoinexplorerback.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class BlockServiceImpl implements BlockService {

    @Autowired
    private BitcoinRest bitcoinRest;

    @Autowired
    private BlockMapper blockMapper;

    @Autowired
    private TransactionServiceImpl transactionService;

    @Override
    public String syncBlock(String blockhash) {
        JSONObject blockJson = bitcoinRest.getBlockNoTxDetails(blockhash);
        Block block = new Block();
        block.setBlockhash(blockJson.getString("hash"));
        block.setConfirmations(blockJson.getInteger("confirmations"));
        block.setHeight(blockJson.getInteger("height"));
        block.setTime(blockJson.getLong("time"));
        block.setDifficulty(blockJson.getDouble("difficulty"));
        block.setSizeondisk(blockJson.getInteger("size"));
        block.setMerkleRoot(blockJson.getString("merkleroot"));
        block.setTxsize(blockJson.getInteger("nTx"));
        block.setVersion(blockJson.getString("versionHex"));
        block.setNonce(blockJson.getInteger("nonce").toString());
        block.setWeight(blockJson.getInteger("weight"));
        //todo get block reward
        //todo change bits to string
        //todo calculate tx volume, fee

        blockMapper.insert(block);
        Integer blockId = block.getBlockId();
        Long time = block.getTime();

        ArrayList<String> txids = (ArrayList<String>) blockJson.get("tx");
        for (String txid : txids) {
            transactionService.syncTransaction(txid, blockId, time);
        }

        //todo insert tx


        String nextblockhash = blockJson.getString("nextblockhash");
        return nextblockhash;
    }

    @Override
    public void syncBlocks(String fromBlockhash) {
        String tempBlockhash = fromBlockhash;
        while (tempBlockhash != null){
            tempBlockhash = syncBlock(tempBlockhash);
        }
    }
}
