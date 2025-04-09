package strategy.결제시스템;

public class Main {
    public static void main(String[] args) {
        PaymentStrategy strategy = new CreditCardPayment("test", "123456", "123", "12/25");
        PaymentProcessor processor = new PaymentProcessor(strategy);

        processor.pay(100000);
    }
}
