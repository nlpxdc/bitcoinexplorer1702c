## 1. 最近区块列表

URL: /block/getRecent  
Method：GET  

ResponseBody:  
```json
[
    {
        "height": 603421,
        "blockhash": "000000000000000000035134b00546a977c51bf1bc9f8abd71fb23f189497419",
        "time": 1573546755,
        "miner": "Poolin",
        "size": 1230690
    },
    {
        "height": 603421,
        "blockhash": "000000000000000000035134b00546a977c51bf1bc9f8abd71fb23f189497419",
        "time": 1573546755,
        "miner": "Poolin",
        "size": 1230690
    }
]

```

| 字段     |     类型 |   描述   | 
| :--------------: | :--------:| :------: |
| height   | Integer   | 区块高度    |
| blockhash   | String   | 区块hash    |
| time   | Long   | 出块时间    |
| miner   | String   | 矿工    |
| size   | Integer   | 字节大小    |

## 2. 区块列表

URL: /block/getWithPage?page={page}  
Method：GET  

ResponseBody:  
```json
{
    "total": 5678,
    "pageSize": 30,
    "currentPage": 2,
    "blocks":[
        {
            "height": 603421,
            "blockhash": "000000000000000000035134b00546a977c51bf1bc9f8abd71fb23f189497419",
            "time": 1573546755,
            "miner": "Poolin",
            "size": 1230690
        },
        {
            "height": 603421,
            "blockhash": "000000000000000000035134b00546a977c51bf1bc9f8abd71fb23f189497419",
            "time": 1573546755,
            "miner": "Poolin",
            "size": 1230690
        }
    ]
}

```

Request 字段:  

| 字段     |     类型 |   描述   | 
| :--------------: | :--------:| :------: |
| page   | Integer   | 页码    |


Response 字段:  

| 字段     |     类型 |   描述   | 
| :--------------: | :--------:| :------: |
| total   | Integer   | 总数    |
| pageSize   | Integer   | 每页大小    |
| currentPage   | Integer   | 当前页    |
| height   | Integer   | 区块高度    |
| blockhash   | String   | 区块hash    |
| time   | Long   | 出块时间    |
| miner   | String   | 矿工    |
| size   | Integer   | 字节大小    |

## 3. 最近未完成交易

URL: /transaction/getRecentUnconfirmed?size={size}  
Method：GET  

ResponseBody:  
```json
[
    {
        "txhash": "bc687317ed8d5f871b21e57498ba76349bea73837ddb719fde9876d0320c8ac5",
        "time": 1573546755,
        "amountBTC": 0.00219597,
        "amountUSD": 19.21
    },
    {
        "txhash": "bc687317ed8d5f871b21e57498ba76349bea73837ddb719fde9876d0320c8ac5",
        "time": 1573546755,
        "amountBTC": 0.00219597,
        "amountUSD": 19.21
    }
]

```

Request 字段:  

| 字段     |     类型 |   描述   | 
| :--------------: | :--------:| :------: |
| size   | Integer   | 单页数量    |

Response 字段:  

| 字段     |     类型 |   描述   | 
| :--------------: | :--------:| :------: |
| txhash   | String   | 交易hash    |
| time   | Long   | 交易时间（出块时间）    |
| amountBTC   | double   | 金额（BTC）    |
| amountUSD   | double   | 金额（美元）    |

## 4. 区块详情

URL: /block/getInfoByHash?blockhash={blockhash}  
Method：GET  

ResponseBody:  
```json
{
    "blockhash":"000000000000021bc34292fcbe27b1bd6d08fe718a7dd39a23b5f681cec0092c",
    "confirmations": 12345,
    "time": 1573546755,
    "height": 603421,
    "miner": "PoolVin",
    "txSize": 1892,
    "difficulty": 12720005267390.51,
    "merkleroot":"1fb10a4a8e9568212d2a82dc2bb0f66c14e0ccab992b234c6dd5424d5f779a51",
    "version":"0x2000e000",
    "bits": 387326161,
    "weight": 3993432,
    "sizeOnDisk": 1230690,
    "nonce": 355265745,
    "txVol": 4912.68140588,
    "blockReward": 12.50000000,
    "feeReward": 0.16901241,
    "transactions":[
        {
            "txhash":"456d535373c56e444c30324c124a6a3f351bccf3d6be294c4fbb839e3f324fcd",
            "time": 1573546755,
            "fees": 0.00000001,
            "totalOutput": 12.66901241,
            "txDetail":[
                {
                    "address":"1MUz4VMYui5qY1mxUiG8BQ1Luv6tqkvaiL",
                    "type": 0,
                    "amount": 0.01930000
                },
                {
                    "address":"1MUz4VMYui5qY1mxUiG8BQ1Luv6tqkvaiL",
                    "type": 0,
                    "amount": 0.01930000
                }
            ]
        },
        {
            "txhash":"456d535373c56e444c30324c124a6a3f351bccf3d6be294c4fbb839e3f324fcd",
            "time": 1573546755,
            "fees": 0.00000001,
            "totalOutput": 12.66901241,
            "txDetails":[
                {
                    "address":"1MUz4VMYui5qY1mxUiG8BQ1Luv6tqkvaiL",
                    "type": 0,
                    "amount": 0.01930000
                },
                {
                    "address":"1MUz4VMYui5qY1mxUiG8BQ1Luv6tqkvaiL",
                    "type": 0,
                    "amount": 0.01930000
                }
            ]
        }
    ]
}

```

Request 字段:  

| 字段     |     类型 |   描述   | 
| :--------------: | :--------:| :------: |
| blockhash   | String   | 区块hash    |

Response 字段:  

| 字段     |     类型 |   描述   | 
| :--------------: | :--------:| :------: |
| blockhash   | String   | 区块hash    |
| confirmations   | Integer   | 确认数    |
| time   | Long   | 出块时间    |
| height   | Integer   | 区块高度    |
| miner   | String   | 矿工    |
| txSize   | Integer   | 交易数量    |
| difficulty   | double   | 难度系数    |
| merkleroot   | String   | 默尔克树    |
| version   | String   | 版本    |
| bits   | Integer   | 位    |
| weight   | Integer   | 重量    |
| sizeOnDisk   | Integer   | 字节大小    |
| nonce   | String   | 随机值    |
| txVol   | double   | 交易总额    |
| blockReward   | double   | 出块奖励    |
| feeReward   | double   | 交易费用    |
| transactions   | array   | 交易列表    |
| txhash   | String   | 交易hash    |
| time   | Long   | 交易时间（出块时间）    |
| fees   | double   | 交易费用    |
| totalOutput   | double   | 交易总输出金额    |
| txDetails   | array   | 交易详情    |
| address   | array   | 地址    |
| type   | array   | 类型（发送0，接受1）    |
| amount   | array   | 金额    |