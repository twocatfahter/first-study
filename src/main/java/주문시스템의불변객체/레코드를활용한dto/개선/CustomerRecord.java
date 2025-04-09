package 주문시스템의불변객체.레코드를활용한dto.개선;

import java.util.Objects;

public record CustomerRecord(
        String name,
        String email,
        String address
) {

//     유효성 검사
    public CustomerRecord {
        Objects.requireNonNull(name, "Name must not be null");
        Objects.requireNonNull(email, "Email must not be null");
        Objects.requireNonNull(address, "Address must not be null");

        if (!email.contains("@")) {
            throw new IllegalArgumentException("Invalid email format");
        }
    }
}
