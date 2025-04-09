package optional.사용자프로필시스템;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Address {
    private String street; // 거리주소
    private String city; // 도시명
    private String zipCode; // 우편번호
}
