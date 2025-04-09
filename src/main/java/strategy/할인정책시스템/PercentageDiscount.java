package strategy.할인정책시스템;

public class PercentageDiscount implements DiscountStrategy{
    private double percentage;

    // 0 보다 작거나, 100보다 크면 IllegalArgumentException;

    public PercentageDiscount(double percentage) {
        if (percentage < 0 || percentage > 100) {
            throw new IllegalArgumentException("할인율은 0 에서 100 퍼센트 사이어야 합니다.");
        }
        this.percentage = percentage;
    }


    @Override
    public double calculateDiscountAmount(double price) {
        return price * (percentage / 100);
    }
}
