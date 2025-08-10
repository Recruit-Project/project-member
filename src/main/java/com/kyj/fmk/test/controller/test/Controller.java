package com.kyj.fmk.test.controller.test;

import com.kyj.fmk.sec.annotation.PublicEndpoint;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/member")
public class Controller {

    private final KafkaTemplate<String,String> kafkaTemplate;

    @RequestMapping("/produce")
    @PublicEndpoint
    public String test(){
        for(int i=0; i<1000; i++ ){
            kafkaTemplate.send("simpledata",String.valueOf(i)) ;
        }
            return "ok";
    }
}
