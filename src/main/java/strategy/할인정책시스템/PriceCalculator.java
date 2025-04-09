package strategy.할인정책시스템;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class PriceCalculator {
    private DiscountStrategy strategy;

    public double calculateFinalPrice(double originalPrice) {
        if (strategy == null) {
            throw new IllegalStateException("할인 전략이 설정되지 않았습니다");
        }

        double discountAmount = strategy.calculateDiscountAmount(originalPrice);
        return originalPrice - discountAmount;
    }

}
