package di.결제시스템;

public class DefaultPaymentValidator implements PaymentValidator{
    @Override
    public boolean validatePayment(double amount) {
        return false;
    }
}
