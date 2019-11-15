package io.cjf.bitcoinexplorerback.dao;

import io.cjf.bitcoinexplorerback.po.TransactionDetail;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TransactionDetailMapper {
    int deleteByPrimaryKey(Long txDetailId);

    int insert(TransactionDetail record);

    int insertSelective(TransactionDetail record);

    TransactionDetail selectByPrimaryKey(Long txDetailId);

    int updateByPrimaryKeySelective(TransactionDetail record);

    int updateByPrimaryKey(TransactionDetail record);

//    custom
    List<TransactionDetail> selectByTransactionId(@Param("transactionId") Integer transactionId);
}