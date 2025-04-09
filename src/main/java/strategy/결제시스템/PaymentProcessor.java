package strategy.결제시스템;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class PaymentProcessor {
    private PaymentStrategy strategy;

    public void setStrategy(PaymentStrategy strategy) {
        this.strategy = strategy;
    }

    public void pay(int amount) {
        strategy.pay(amount);
    }

}
