package 컨벤션.실무에서자주발생하는문제예시.개선된예시;

import java.util.Objects;
import java.util.Optional;

class Order1 {
    OrderStatus status;
    Customer1 customer;

    public OrderStatus getStatus() {
        return status;
    }

    public Customer1 getCustomer() {
        return customer;
    }
}

class Customer1 {
    String name;

    public String getName() {
        return name;
    }
}

enum OrderStatus {
    COMPLETED
}

public class OrderProcessor {
    public void process(Order1 order) {
        Objects.requireNonNull(order, "Order cannot be null");

        if (OrderStatus.COMPLETED.equals(order.getStatus())) {
            // 처리로직
        }

        Customer1 customer = Optional.ofNullable(order.getCustomer())
                .orElseThrow(() -> new IllegalArgumentException("Customer cannot be null"));

        String customerName = customer.getName();
    }

}
