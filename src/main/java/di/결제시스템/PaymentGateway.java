package di.결제시스템;

public class PaymentGateway {
    private final PaymentValidator validator;
    private final PaymentProcessor processor;

    public PaymentGateway(PaymentValidator validator, PaymentProcessor processor) {
        this.validator = validator;
        this.processor = processor;
    }

    public void processPayment(double amount) {
        if (validator.validatePayment(amount)) {
            try {
                processor.processPayment(amount);
                System.out.println("Payment of " + amount + " processed successfully");
            } catch (Exception e) {
                throw new RuntimeException("Payment failed due to  processing error");
            }
        } else {
            throw new RuntimeException("Payment was not successfully");
        }
    }
}
