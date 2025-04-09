package 컨벤션.실무에서자주발생하는문제예시.잘못된예시;

class Order {
    String status;
    Customer customer;

    public String getStatus() {
        return status;
    }

    public Customer getCustomer() {
        return customer;
    }
}

class Customer {
    String name;

    public String getName() {
        return name;
    }
}

public class OrderProcessor {
    public void process(Order order) {
        if (order.getStatus() == "COMPLETED") {
            // 처리로직
        }

        String customerName = order.getCustomer().getName();
    }
}
