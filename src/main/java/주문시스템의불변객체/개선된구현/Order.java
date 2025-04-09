package 주문시스템의불변객체.개선된구현;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class Order {
    private final String orderId;
    private final List<String> items;
    private final double totalAmount;

    public Order(String orderId, List<String> items, double totalAmount) {
        this.orderId = Objects.requireNonNull(orderId, "OrderId must not be null");
        this.items = List.copyOf(items); // 불변 리스트 생성
        this.totalAmount = totalAmount;
    }

    public String getOrderId() {
        return orderId;
    }

    public List<String> getItems() {
        return items; // 불변 리스트
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public Order addItem(String item) {
        List<String> newItems = new ArrayList<>(items);
        newItems.add(item);
        return new Order(orderId, newItems, recalculateAmount(newItems));
    }

    private double recalculateAmount(List<String> items) {
        return items.size() * 10.0;
    }
}
