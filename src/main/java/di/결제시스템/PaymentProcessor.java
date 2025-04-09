package di.결제시스템;

public interface PaymentProcessor {

    void processPayment(double amount) throws PaymentValidationException;
}
