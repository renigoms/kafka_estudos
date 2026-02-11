package org.renigoms.paymentservice.service;

import org.renigoms.paymentservice.model.Payment;


public interface PaymentService {

    void sendPayment(Payment payment);

}
