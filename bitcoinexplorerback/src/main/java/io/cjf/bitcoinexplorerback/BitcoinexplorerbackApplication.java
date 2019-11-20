package io.cjf.bitcoinexplorerback;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableFeignClients
@MapperScan("io.cjf.bitcoinexplorerback.dao")
@EnableScheduling
@EnableAsync
public class BitcoinexplorerbackApplication {

    public static void main(String[] args) {
        SpringApplication.run(BitcoinexplorerbackApplication.class, args);
    }

}
