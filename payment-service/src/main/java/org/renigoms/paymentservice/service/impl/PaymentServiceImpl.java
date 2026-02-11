package org.renigoms.paymentservice.service.impl;

import lombok.extern.log4j.Log4j2;
import org.renigoms.paymentservice.model.Payment;
import org.renigoms.paymentservice.service.PaymentService;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class PaymentServiceImpl implements PaymentService {

    @Override
    public void sendPayment(Payment payment) {
        log.info("PAYMENT_SERVICEC_IMPL ::: Recebi o pagamento {}", payment);
    }
}
