package di.결제시스템;

public class ImprovedCreditCardProcessor implements PaymentProcessor{
    private final PaymentValidator validator;
    private final ImprovedPaymentGateway gateway;

    public ImprovedCreditCardProcessor(PaymentValidator validator, ImprovedPaymentGateway gateway) {
        this.validator = validator;
        this.gateway = gateway;
    }

    @Override
    public void processPayment(double amount) throws PaymentValidationException {
        if (validator.validatePayment(amount)) {
            boolean success = gateway.processPayment(amount);

            if (!success) {
                throw new PaymentValidationException("Payment processing failed at the gateway");
            }

            // 실제 거래로직구성 실행
        } else {
            throw new PaymentValidationException("Invalid payment amount");
        }
    }
}
