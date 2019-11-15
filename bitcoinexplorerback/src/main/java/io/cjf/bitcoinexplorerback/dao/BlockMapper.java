package io.cjf.bitcoinexplorerback.dao;

import io.cjf.bitcoinexplorerback.po.Block;

import java.util.List;

public interface BlockMapper {
    int deleteByPrimaryKey(Integer blockId);

    int insert(Block record);

    int insertSelective(Block record);

    Block selectByPrimaryKey(Integer blockId);

    int updateByPrimaryKeySelective(Block record);

    int updateByPrimaryKey(Block record);

//    custom
    List<Block> selectRecent();
}