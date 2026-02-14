package org.renigoms.paymentservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.renigoms.paymentservice.model.Payment;
import org.renigoms.paymentservice.service.PaymentService;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.io.Serializable;

@Service
@RequiredArgsConstructor
@Log4j2
public class PaymentServiceImpl implements PaymentService {

    private final KafkaTemplate<String, Serializable> kafkaTemplate;

    @SneakyThrows
    @Override
    public void sendPayment(Payment payment) {
        log.info("Recebi o pagamento {}", payment);
        Thread.sleep(1000);
        log.info("Enviando pagamento... ");
        kafkaTemplate.send("payment-topic", payment);
    }
}
