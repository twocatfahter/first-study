package builder.기존;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        Order order = new Order("123", "customer", Arrays.asList("item1", "item2")
                , "123 street", true, "KAKAO_PAY", null);
    }
}
