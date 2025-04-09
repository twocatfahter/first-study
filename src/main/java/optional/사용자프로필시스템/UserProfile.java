package optional.사용자프로필시스템;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UserProfile {
    private String userId;
    private String name;
    private String email;
    private Address address; // null 이될 수 있다.
}
