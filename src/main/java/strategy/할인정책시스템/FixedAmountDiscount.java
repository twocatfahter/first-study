package strategy.할인정책시스템;

public class FixedAmountDiscount implements DiscountStrategy{
    private double fixedAmount;

    // 0 보다 크면됩니다.0보다작으면 IllegalArgumentException

    public FixedAmountDiscount(double fixedAmount) {
        if (fixedAmount < 0) {
            throw new IllegalArgumentException("할인 금액은 0원 이상이어야 합니다.");
        }
        this.fixedAmount = fixedAmount;
    }


    @Override
    public double calculateDiscountAmount(double price) {
        return Math.min(fixedAmount, price);
    }
}
