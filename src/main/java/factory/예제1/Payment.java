package factory.예제1;

import java.math.BigDecimal;

enum PaymentStatus {
    COMPLETED,
    PENDING
}

// 1. 결제 인터페이스
public interface Payment {
    void processPayment(BigDecimal amount);
    PaymentStatus getStatus();
}
