package stream.온라인쇼핑몰주문처리;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@ToString
public class Order {
    private String orderId;
    private String customerName;
    private List<Product> products;
    private LocalDateTime orderDate;

    public Order(String orderId, String customerName, List<Product> products, LocalDateTime orderDate) {
        this.orderId = orderId;
        this.customerName = customerName;
        this.products = products;
        this.orderDate = orderDate;
    }

    public double getTotalAmount() {
        return products.stream()
                .mapToDouble(Product::getPrice)
                .sum();
    }
}
