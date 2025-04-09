package 주문시스템의불변객체.레코드를활용한dto.개선;

import 주문시스템의불변객체.레코드를활용한dto.기존.CustomerDto;

public class Main {
    public static void main(String[] args) {
        CustomerRecord record = new CustomerRecord("test", "test", "test");
        System.out.println(record.toString());

        CustomerDto dto = new CustomerDto("test", "test", "test");
        System.out.println(dto.toString());
    }
}
