package org.renigoms.strproducer.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class StringProducerService {
    private final KafkaTemplate<String, String> kafkaTemplate;

    public void sendMessage(String message){
        log.info("Send message {}", message);
        kafkaTemplate.send("str-topic", message);
//                .whenComplete(
//                (sendResult, throwable) -> {
//                    if (throwable == null) {
//                        log.info("Send message with success {}", message);
//                        log.info("Partition {} offset {}",
//                                sendResult.getRecordMetadata().partition(),
//                                sendResult.getRecordMetadata().offset());
//                    }else log.error("Error send message");
//                }
//        );

    }
}
