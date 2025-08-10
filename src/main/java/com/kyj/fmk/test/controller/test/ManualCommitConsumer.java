package com.kyj.fmk.test.controller.test;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

@Component
public class ManualCommitConsumer {

    @KafkaListener(topics = "simpledata", groupId = "simple")
    public void listen(ConsumerRecord<String, String> record, Acknowledgment ack) {
        try {
            // 메시지 처리 로직
            System.out.println("Received: " + record.value());

            // 성공 시 수동 커밋 호출
            ack.acknowledge();
        } catch (Exception e) {
            // 에러 처리 (커밋 안 함, 재시도 가능)
            System.err.println("Error processing message: " + e.getMessage());
            // 필요하면 예외 던지거나 재처리 로직 작성
        }
    }
}
