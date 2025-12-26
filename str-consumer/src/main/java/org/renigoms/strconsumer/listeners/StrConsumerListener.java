package org.renigoms.strconsumer.listeners;

import lombok.extern.log4j.Log4j2;
import org.renigoms.strconsumer.custom.StrConsumerCustomListener;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Log4j2
public class StrConsumerListener {

    @StrConsumerCustomListener(groupId = "group-01")
    public void create(String message){
        log.info("CREATE ::: Receive message {}", message);
    }

    @StrConsumerCustomListener(groupId = "group-01")
    public void log(String message){
        log.info("LOG ::: Receive message {}", message);
    }

    @KafkaListener(groupId = "group-02",
            topics = "str-topic", containerFactory = "validMessageContainerFactory")
    public void history(String message){
        log.info("HISTORY ::: Receive message {}", message);
    }
}
