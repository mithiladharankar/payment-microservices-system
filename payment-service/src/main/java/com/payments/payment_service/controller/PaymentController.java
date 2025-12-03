package com.payments.payment_service.controller;

import com.payments.payment_service.domain.Payment;
import com.payments.payment_service.dto.CreatePaymentRequest;
import com.payments.payment_service.metrics.PaymentMetrics;
import com.payments.payment_service.repository.PaymentRepository;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    private final PaymentRepository paymentRepository;
    private final PaymentMetrics paymentMetrics;

    public PaymentController(PaymentRepository paymentRepository,
                             PaymentMetrics paymentMetrics) {
        this.paymentRepository = paymentRepository;
        this.paymentMetrics = paymentMetrics;
    }

    @PostMapping
    public Payment createPayment(Authentication authentication,
                                 @RequestBody CreatePaymentRequest request) {

        String username = authentication.getName();

        Payment payment = new Payment();
        payment.setUsername(username);
        payment.setAmount(request.getAmount());
        payment.setCurrency(request.getCurrency());
        payment.setStatus("PENDING");

        Payment saved = paymentRepository.save(payment);

        paymentMetrics.incrementPaymentCreated();

        return saved;
    }

    @GetMapping
    public List<Payment> getMyPayments(Authentication authentication) {
        String username = authentication.getName();
        return paymentRepository.findByUsername(username);
    }
}
