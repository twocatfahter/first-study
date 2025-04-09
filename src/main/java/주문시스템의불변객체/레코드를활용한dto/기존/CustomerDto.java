package 주문시스템의불변객체.레코드를활용한dto.기존;

import java.util.Objects;


public final class CustomerDto {
    private final String name;
    private final String email;
    private final String address;

    public CustomerDto(String name, String email, String address) {
        this.name = name;
        this.email = email;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getAddress() {
        return address;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof CustomerDto that)) return false;
        return Objects.equals(name, that.name)
                && Objects.equals(email, that.email)
                && Objects.equals(address, that.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, email, address);
    }

}
