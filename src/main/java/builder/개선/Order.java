package builder.개선;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Order {
    private final String orderId;
    private final String customerName;
    private final List<String> items;
    private final String deliveryAddress;
    private final boolean express;
    private final String paymentMethod;
    private final String specialInstructions;

    private Order(Builder builder) {
        this.orderId = builder.orderId;
        this.customerName = builder.customerName;
        this.items = builder.items;
        this.deliveryAddress = builder.deliveryAddress;
        this.express = builder.express;
        this.paymentMethod = builder.paymentMethod;
        this.specialInstructions = builder.specialInstructions;
    }

    public static class Builder {
        private final String orderId;
        private final String customerName;
        private List<String> items = new ArrayList<>();
        private String deliveryAddress;
        private boolean express = false;
        private String paymentMethod = "CASH";
        private String specialInstructions;

        public Builder(String orderId, String customerName) {
            this.orderId = Objects.requireNonNull(orderId, "OrderId must not be null");
            this.customerName = Objects.requireNonNull(customerName, "CustomerName must not be null");
        }

        public Builder items(List<String> items) {
            this.items = new ArrayList<>(items);
            return this;
        }

        public Builder deliveryAddress(String deliveryAddress) {
            this.deliveryAddress = deliveryAddress;
            return this;
        }

        public Builder express(boolean express) {
            this.express = express;
            return this;
        }

        public Builder paymentMethod(String paymentMethod) {
            this.paymentMethod = paymentMethod;
            return this;
        }

        public Builder specialInstructions(String specialInstructions) {
            this.specialInstructions = specialInstructions;
            return this;
        }

        public Order build() {
            validateOrderData();
            return new Order(this);
        }

        private void validateOrderData() {
            if (items.isEmpty()) {
                throw new IllegalStateException("Order must have at least one item");
            }

            if (deliveryAddress == null && !items.isEmpty()) {
                throw new IllegalStateException("Delivery address is required for orders");
            }
        }

    }

}
