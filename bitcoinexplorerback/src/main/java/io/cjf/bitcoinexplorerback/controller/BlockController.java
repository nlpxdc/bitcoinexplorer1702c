package io.cjf.bitcoinexplorerback.controller;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import io.cjf.bitcoinexplorerback.dto.PageDTO;
import io.cjf.bitcoinexplorerback.po.Block;
import io.cjf.bitcoinexplorerback.service.BlockService;
import io.cjf.bitcoinexplorerback.service.impl.BlockServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/block")
public class BlockController {

    @Autowired
    private BlockService blockService;

    @GetMapping("/getRecent")
    public List<JSONObject> getRecent(){
        List<Block> blocks = blockService.getRecent();
        List<JSONObject> blockJsons = blocks.stream().map(block -> {
            JSONObject blockJson = new JSONObject();
            blockJson.put("height", block.getHeight());
            blockJson.put("blockhash", block.getBlockhash());
            blockJson.put("time", block.getTime());
            blockJson.put("miner", block.getMiner());
            blockJson.put("size", block.getSizeondisk());
            return blockJson;
        }).collect(Collectors.toList());
        return blockJsons;
    }

    @GetMapping("/getWithPage")
    public PageDTO<JSONObject> getWithPage(@RequestParam(required = false, defaultValue = "1") Integer page){
        Page<Block> blocks = blockService.getWithPage(page);
        List<JSONObject> blockJsons = blocks.stream().map(block -> {
            JSONObject blockJson = new JSONObject();
            blockJson.put("height", block.getHeight());
            blockJson.put("blockhash", block.getBlockhash());
            blockJson.put("time", block.getTime());
            blockJson.put("miner", block.getMiner());
            blockJson.put("size", block.getSizeondisk());
            return blockJson;
        }).collect(Collectors.toList());

        PageDTO<JSONObject> pageDTO = new PageDTO<>();
        pageDTO.setList(blockJsons);
        pageDTO.setTotal(blocks.getTotal());
        pageDTO.setPageSize(blocks.getPageSize());
        pageDTO.setCurrentPage(blocks.getPageNum());

        return pageDTO;
    }

    @GetMapping("/getInfoByHash")
    public JSONObject getInfoByHash(@RequestParam String blockhash){
        return null;
    }

    @GetMapping("/getInfoByHeight")
    public JSONObject getInfoByHeight(@RequestParam Integer height){
        return null;
    }
}
