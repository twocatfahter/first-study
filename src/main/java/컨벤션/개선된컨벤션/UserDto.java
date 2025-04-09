package 컨벤션.개선된컨벤션;

import java.util.Objects;

public class UserDto {
    private String username;
    private int age;
    private String phoneNumber;

    public void setUsername(String username) {
        this.username = Objects.requireNonNull(username, "Username cannot be null");
    }

    public boolean isValidAge() {
        return age >= 0;
    }
}
