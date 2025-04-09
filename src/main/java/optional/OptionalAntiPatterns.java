package optional;

import optional.사용자프로필시스템.UserProfile;
import optional.사용자프로필시스템.UserProfileService;

import java.util.Optional;

public class OptionalAntiPatterns {
    // 잘못된 사용 예시를 보여주기 위한 필드
    // 안티패턴: Optional을 필드로 사용
    private Optional<String> name; // 잘못된 사용 - Optional은 필드로 사용하지 않음

    // UserProfileService 인스턴스
    private UserProfileService userProfileService = new UserProfileService();

    /**
     * 안티패턴: Optional을 메서드 매개변수로 사용
     * @param user Optional로 래핑된 UserProfile 객체
     */
    public void processUser(Optional<UserProfile> user) { // 잘못된 사용 - Optional은 매개변수로 사용하지 않음
        // 매개변수로 Optional을 받는 것보다 직접 객체를 받고 내부에서 Optional로 처리하는 것이 바람직
        if (user.isPresent()) {
            UserProfile profile = user.get();
            // 프로필 처리 로직
        }
    }

    /**
     * 안티패턴: Optional.get() 직접 호출
     * @param userId 사용자 ID
     * @return 사용자 이름
     */
    public String getUserNameUnsafe(String userId) {
        Optional<UserProfile> user = userProfileService.findUserById(userId);
        return user.get().getName(); // 위험한 사용 - NoSuchElementException 발생 가능
    }

    /**
     * 올바른 사용법: Optional 메서드 체이닝과 대체 값 제공
     * @param userId 사용자 ID
     * @return 사용자 이름, 사용자가 없을 경우 "Unknown User" 반환
     */
    public String getUserNameSafe(String userId) {
        return userProfileService.findUserById(userId)
                .map(UserProfile::getName)
                .orElse("Unknown User");
    }

    /**
     * 올바른 사용법: Optional을 매개변수로 사용하지 않고 직접 객체 전달
     * @param user UserProfile 객체 (null 가능)
     */
    public void processUserCorrect(UserProfile user) {
        // 내부에서 Optional로 래핑하여 처리
        Optional.ofNullable(user)
                .ifPresent(profile -> {
                    // 프로필 처리 로직
                    System.out.println("사용자 처리: " + profile.getName());
                });
    }

    /**
     * 올바른 사용법: Optional을 반환 타입으로 사용
     * @param userId 사용자 ID
     * @return 사용자 이름을 담은 Optional (사용자가 없으면 빈 Optional)
     */
    public Optional<String> findUserName(String userId) {
        return userProfileService.findUserById(userId)
                .map(UserProfile::getName);
    }
}
