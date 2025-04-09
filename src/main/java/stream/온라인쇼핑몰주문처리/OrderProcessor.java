package stream.온라인쇼핑몰주문처리;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class OrderProcessor {
    public static void main(String[] args) {
        // sample
        List<Order> orders = getOrders();

        // 1. 총 주문 금액 계산
        double sum = Objects.requireNonNull(orders).stream()
                .flatMap(order -> order.getProducts().stream())
                .mapToDouble(Product::getPrice)
                .sum();

        System.out.println("총 주문 금액: " + sum + "원");

        // 2. 카테고리별 판매 금액 집계
        Map<String, Double> categorySales = orders.stream()
                .flatMap(order -> order.getProducts().stream())
                .collect(Collectors.groupingBy(
                        Product::getCategory,
                        Collectors.summingDouble(Product::getPrice)
                ));

        System.out.println("카테고리별 판매 금액: ");
        categorySales.forEach((category, sales) ->
                System.out.println(" " + category + ": " + sales + "원"));

        // 최근 24시간 이내 주문 필터링
        LocalDateTime yesterday = LocalDateTime.now().minusDays(1);
        List<Order> recentOrders = orders.stream()
                .filter(order -> order.getOrderDate().isAfter(yesterday))
                .toList();

        System.out.println("최근 24시간 이내 주문: " + recentOrders.size() + "건");

        // 4. 고객별 주문 횟수 및 총 금액 집계
        // 고객명으로 그룹화
        // 각 고객의 총 주문 금액을 계산
        Map<String, Double> customerTotalAmount = orders.stream()
                .collect(Collectors.groupingBy(
                        Order::getCustomerName,
                        Collectors.summingDouble(order ->
                                order.getProducts().stream()
                                        .mapToDouble(Product::getPrice)
                                        .sum())
                ));
    }

    private static List<Order> getOrders() {
        // 현재 시간과 이전 시간 데이터 준비
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime yesterdayTime = now.minusDays(2);

        // 다양한 카테고리의 상품 생성
        Product laptop = new Product("노트북", 1_200_000, "전자제품");
        Product phone = new Product("스마트폰", 800_000, "전자제품");
        Product book = new Product("자바 프로그래밍", 30_000, "도서");
        Product chair = new Product("사무용 의자", 150_000, "가구");
        Product desk = new Product("책상", 200_000, "가구");

        // 여러 주문 생성
        return List.of(
                new Order("ORD001", "김철수", List.of(laptop, chair), now),
                new Order("ORD002", "이영희", List.of(phone, book), now),
                new Order("ORD003", "박지민", List.of(desk), yesterdayTime),
                new Order("ORD004", "김철수", List.of(book, book), now.minusHours(6))
        );
    }
}
