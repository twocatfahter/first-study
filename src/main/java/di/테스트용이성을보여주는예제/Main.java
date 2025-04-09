package di.테스트용이성을보여주는예제;

public class Main {
    public static void main(String[] args) {
        // 1. 필요한 구현체들을 생성
        OrderRepository orderRepository = new DatabaseOrderRepository();
        PaymentProcessor paymentProcessor = new CreditCardPaymentProcessor();
        NotificationService notificationService = new PosNotificationService();

        // 2. 의존성 주입을 통해서 서비스 생성
        OrderService orderService = new OrderService(
                orderRepository,
                paymentProcessor,
                notificationService
        );

        // 3. 서비스사용
        Order order = new Order("12345", 1500.75);
        System.out.println("==== 주문 처리 시작 ====");
        orderService.processOrder(order);
        System.out.println("==== 주문 처리 완료 ====");

        // 4. 구현체를 다른것으로 교체해서 동일한 서비스 사용
        System.out.println("\n==== 다른 구현체로 교체 ====");

        OrderRepository paperOrderRepository = new OrderRepository() {
            @Override
            public Order findById(String id) {
                System.out.println("종이 주문서에 주문 ID " + id + " 찾는 중...");
                return new Order(id, 1000.0);
            }

            @Override
            public void save(Order order) {
                System.out.println("주문 ID " + order.getId() + ", 금액: " + order.getAmount() + " 을 종이 주문서에 기록했습니다.");
            }
        };

        PaymentProcessor cashPaymentProcessor = new PaymentProcessor() {
            @Override
            public void processPayment(double amount) {
                System.out.println("현금으로 " + amount + " 을 결제 했습니다.");
            }
        };

        NotificationService paperNotificationService = new NotificationService() {
            @Override
            public void sendNotification(String message) {
                System.out.println("종이 메모 알림 전달: "  +message);
            }
        };

        OrderService alternativeOrderService = new OrderService(
                paperOrderRepository,
                cashPaymentProcessor,
                paperNotificationService
        );

        System.out.println("==== 대체 시스템으로 주문 처리 시작 ====");
        alternativeOrderService.processOrder(order);
        System.out.println("==== 대체 시스템으로 주문 처리 완료 ====");
    }
}
