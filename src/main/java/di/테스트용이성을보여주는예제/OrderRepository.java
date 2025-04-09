package di.테스트용이성을보여주는예제;

public interface OrderRepository {

    Order findById(String id);

    void save(Order order);
}
