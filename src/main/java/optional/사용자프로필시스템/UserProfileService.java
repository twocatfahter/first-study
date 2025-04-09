package optional.사용자프로필시스템;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class UserProfileService {
    // 사용자 아이디를 키로 사용하는 사용자 프로필 저장소
    private Map<String, UserProfile> userProfiles = new HashMap<>();

    public Optional<UserProfile> findUserById(String userId) {
        return Optional.ofNullable(userProfiles.get(userId));
    }

    public String getDisplayAddress(String userId) {
        return findUserById(userId)
                .map(UserProfile::getAddress)
                .map(address -> String.format("%s, %s, %s",
                        address.getStreet(),
                        address.getCity(),
                        address.getZipCode()))
                .orElse("주소 정보 없음");
    }

    // 1. 사용자의 이메일을 반환하는 메소드
    public String getUserEmail(String userId) {
        return findUserById(userId)
                .map(UserProfile::getEmail)
                .filter(email -> email.contains("@"))
                .orElseThrow(() -> new IllegalStateException("Invalid email for user: " + userId));
    }

    // 2. 사용자의 이메일을 업데이트하는 메소드
    public void updateUserEmail(String userId, String newEmail) {
        findUserById(userId).ifPresent(user -> {
            user.setEmail(newEmail);
            System.out.println("이메일 업데이트 완료: " + newEmail);
        });
    }

    // 3. 사용자 데이터를 저장소에 추가하는 메서드
    public void addUser(UserProfile userProfile) {
        userProfiles.put(userProfile.getUserId(), userProfile);
    }

}
