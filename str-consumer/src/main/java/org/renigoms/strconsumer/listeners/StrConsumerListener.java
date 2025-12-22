package org.renigoms.strconsumer.listeners;

import lombok.extern.log4j.Log4j2;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Log4j2
public class StrConsumerListener {

    @KafkaListener(groupId = "group-00", topics = "str-topic", containerFactory = "strContainerFactory")
    public void create(String message){
        log.info("CREATE ::: Receive message {}", message);
    }

    @KafkaListener(groupId = "group-01", topics = "str-topic", containerFactory = "strContainerFactory")
    public void log(String message){
        log.info("LOG ::: Receive message {}", message);
    }

    @KafkaListener(groupId = "group-02", topics = "str-topic", containerFactory = "strContainerFactory")
    public void history(String message){
        log.info("HISTORY ::: Receive message {}", message);
    }
}
