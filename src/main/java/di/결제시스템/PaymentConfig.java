package di.결제시스템;

// @Configuration
public class PaymentConfig {

    // @Bean
    public PaymentValidator paymentValidator() {
        return new DefaultPaymentValidator();
    }

    // @Bean
    public ImprovedPaymentGateway paymentGateway(PaymentValidator validator) {
        return new StripePaymentGateway(validator);
    }

    // @Bean
    public PaymentProcessor paymentProcessor(
            PaymentValidator validator,
            ImprovedPaymentGateway gateway
    ) {
        return new ImprovedCreditCardProcessor(validator, gateway);
    }
}
