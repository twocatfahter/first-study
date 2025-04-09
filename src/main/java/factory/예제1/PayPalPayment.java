package factory.예제1;

import java.math.BigDecimal;

public class PayPalPayment implements Payment{
    private PaymentStatus status = PaymentStatus.PENDING;

    @Override
    public void processPayment(BigDecimal amount) {
        // paypal 결제 로직
        System.out.println("페이팔");
        status = PaymentStatus.COMPLETED;
    }

    @Override
    public PaymentStatus getStatus() {
        return status;
    }
}
