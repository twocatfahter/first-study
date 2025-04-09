package builder.개선;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        Order order = new Order.Builder("123", "test")
                .items(Arrays.asList("item1", "item2"))
                .express(true)
                .deliveryAddress("test")
                .build();

        String query = new QueryBuilder.Builder("users")
                .select("id", "name", "email")
                .where("age >= 20")
                .where("status = 'ACTIVE'")
                .orderBy("name ASC", "id DESC")
                .limit(10)
                .offset(20)
                .build()
                .buildQuery();

        System.out.println(query);


    }
}
