package di.결제시스템;

public class CreditCardProcessor implements PaymentProcessor{
    private final PaymentValidator validator;
    private final PaymentGateway gateway;

    public CreditCardProcessor(PaymentValidator validator, PaymentGateway gateway) {
        this.validator = validator;
        this.gateway = gateway;
    }

    @Override
    public void processPayment(double amount) throws PaymentValidationException {
        if (validator.validatePayment(amount)) {
            gateway.processPayment(amount);
        } else {
            throw new PaymentValidationException("Invalid payment amounts");
        }
    }
}
