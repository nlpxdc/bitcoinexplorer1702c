package io.cjf.bitcoinexplorerback.dao;

import io.cjf.bitcoinexplorerback.po.Transaction;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TransactionMapper {
    int deleteByPrimaryKey(Integer transactionId);

    int insert(Transaction record);

    int insertSelective(Transaction record);

    Transaction selectByPrimaryKey(Integer transactionId);

    int updateByPrimaryKeySelective(Transaction record);

    int updateByPrimaryKey(Transaction record);

//    custom

    List<Transaction> selectByBlockId(@Param("blockId") Integer blockId);
}