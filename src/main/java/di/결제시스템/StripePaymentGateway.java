package di.결제시스템;

public class StripePaymentGateway implements ImprovedPaymentGateway{
    private final PaymentValidator validator;

    public StripePaymentGateway(PaymentValidator validator) {
        this.validator = validator;
    }

    @Override
    public boolean processPayment(double amount) {
        if (validator.validatePayment(amount)) {
            try {
                System.out.println("Payment of " + amount + " processed successfully through Stripe");
                return true;
            }catch (Exception e) {
                System.err.println("Payment processing error : " + e.getMessage());
                return false;
            }
        } else {
            System.err.println("Payment validation failed for amount: " + amount);
            return false;
        }
    }
}
