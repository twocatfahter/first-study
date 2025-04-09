package 주문시스템의불변객체.잘못된구현;

import java.util.List;

public class Order {
    private String orderId;
    private List<String> items;
    private double totalAmount;

    public Order(String orderId, List<String> items, double totalAmount) {
        this.orderId = orderId;
        this.items = items;             // 외부 리스트를 직접 참조
        this.totalAmount = totalAmount;
    }

    public void addItem(String item) {
        items.add(item);    // 상태가 변경이 가능하다.
        recalculateAmount();
    }

    private void recalculateAmount() {
        // 금액 재계산 로직
    }
}
