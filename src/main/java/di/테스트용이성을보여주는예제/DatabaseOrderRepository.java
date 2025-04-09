package di.테스트용이성을보여주는예제;

public class DatabaseOrderRepository implements OrderRepository{
    @Override
    public Order findById(String id) {
        System.out.println("데이터베이스에서 주문 ID " + id + " 조회 중....");
        return new Order(id, 1000.0);
    }

    @Override
    public void save(Order order) {
        System.out.println("주문 ID " + order.getId() + ", 금액: " + order.getAmount() + " 을 데이터베이스에 저장했습니다.");
    }
}
