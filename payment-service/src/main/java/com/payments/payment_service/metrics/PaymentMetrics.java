package com.payments.payment_service.metrics;

import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.stereotype.Component;

@Component
public class PaymentMetrics {

    private final MeterRegistry meterRegistry;

    public PaymentMetrics(MeterRegistry meterRegistry) {
        this.meterRegistry = meterRegistry;
    }

    public void incrementPaymentCreated() {
        meterRegistry.counter("payments_created_total").increment();
    }
}
