package org.renigoms.strconsumer.exception;

import lombok.extern.log4j.Log4j2;
import org.apache.kafka.clients.consumer.Consumer;
import org.jspecify.annotations.Nullable;
import org.springframework.kafka.listener.KafkaListenerErrorHandler;
import org.springframework.kafka.listener.ListenerExecutionFailedException;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

@Log4j2
@Component
public class ErrorCustomHandler implements KafkaListenerErrorHandler {
    @Override
    public Object handleError(Message<?> message, ListenerExecutionFailedException exception) {
        log.info("EXCEPTION HANDLER ::: Capturei um erro");
        return null;
    }

//    @Override
//    public Object handleError(Message<?> message, ListenerExecutionFailedException exception, @Nullable Consumer<?, ?> consumer) {
//        return KafkaListenerErrorHandler.super.handleError(message, exception, consumer);
//    }
//
//    @Override
//    public @Nullable Object handleError(Message<?> message, ListenerExecutionFailedException exception, @Nullable Consumer<?, ?> consumer, @Nullable Acknowledgment ack) {
//        return KafkaListenerErrorHandler.super.handleError(message, exception, consumer, ack);
//    }
}
