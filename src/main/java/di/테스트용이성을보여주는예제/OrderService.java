package di.테스트용이성을보여주는예제;

public class OrderService {
    private final OrderRepository orderRepository;
    private final PaymentProcessor paymentProcessor;
    private final NotificationService notificationService;

    public OrderService(OrderRepository orderRepository,
                        PaymentProcessor paymentProcessor,
                        NotificationService notificationService) {
        this.orderRepository = orderRepository;
        this.paymentProcessor = paymentProcessor;
        this.notificationService = notificationService;
    }

    public void processOrder(Order order) {
        // 주문저장
        orderRepository.save(order);

        // 결제 처리
        paymentProcessor.processPayment(order.getAmount());

        // 알림 전송
        notificationService.sendNotification("Order Processed: " + order.getId());
    }
}
