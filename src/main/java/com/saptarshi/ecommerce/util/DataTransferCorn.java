package com.saptarshi.ecommerce.util;

import com.saptarshi.ecommerce.service.Impl.DataTransferService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataTransferCorn {

    private final DataTransferService dataTransferService;

    @Scheduled(fixedDelay = 50000000)
    public void run(){
//        System.out.println("test");
        dataTransferService.transferData();
    }

}
