package di.테스트용이성을보여주는예제;

public class CreditCardPaymentProcessor implements PaymentProcessor{
    @Override
    public void processPayment(double amount) {
        System.out.println("신용카드로 " + amount + "원 결제 처리했습니다.");
    }
}
