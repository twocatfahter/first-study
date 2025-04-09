package optional.상품재고관리시스템;

import optional.사용자프로필시스템.UserProfile;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ProductInventoryService {
    private Map<String, Product> inventory = new HashMap<>();

    public Optional<Product> findProduct(String productId) {
        return Optional.ofNullable(inventory.get(productId));
    }

    public void processOrder(String productId, int quantity) {
        findProduct(productId)
                .filter(product -> product.getStock() >= quantity)
                .ifPresentOrElse(
                        product -> {
                            product.setStock(product.getStock() - quantity);
                            System.out.println("주문 처리 완료!");
                        },
                        () -> {
                            throw new IllegalStateException("재고 부족 또는 상품이 존재하지 않습니다.");
                        }
                );
    }

    public BigDecimal calculateDiscountedPrice(String productId, double discountRate) {
        return findProduct(productId)
                .map(Product::getPrice)
                .map(price -> price.multiply(BigDecimal.valueOf(1 - discountRate)))
                .orElse(BigDecimal.ZERO);
    }

    // 상품 정보 요약을 문자열로 반환하는 메서드
    // 기본메세지는 상품 정보 없음
    // 상품이름 (재고: 00개, 가격: 00원)
    public String getProductSummary(String productId) {
        return findProduct(productId)
                .map(product -> String.format("%s (재고: %d개, 가격: %s원)",
                        product.getName(), product.getStock(), product.getPrice()))
                .orElse("상품 정보 없음");

    }

    // 상품을 재고에 추가하는 메소드
    public void addProduct(Product product) {
        inventory.put(product.getId(), product);
    }
}
