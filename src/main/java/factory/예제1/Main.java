package factory.예제1;

import java.math.BigDecimal;

public class Main {
    public static void main(String[] args) {
        PaymentService paymentService = new PaymentService();

        paymentService.processPayment(PaymentMethod.PAYPAL, BigDecimal.valueOf(10000));
    }
}
