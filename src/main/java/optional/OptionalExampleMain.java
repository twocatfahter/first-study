package optional;

import optional.사용자프로필시스템.Address;
import optional.사용자프로필시스템.UserProfile;
import optional.사용자프로필시스템.UserProfileService;
import optional.상품재고관리시스템.Product;
import optional.상품재고관리시스템.ProductInventoryService;
import optional.설정관리시스템.ConfigurationManager;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.List;

public class OptionalExampleMain {
    public static void main(String[] args) {
        System.out.println("===== Optional 활용 예제 =====\n");

        // 1. 사용자 프로필 시스템 예제
        demonstrateUserProfileSystem();

        // 2. 상품 재고 관리 시스템 예제
        demonstrateProductInventorySystem();

        // 3. 설정 관리 시스템 예제
        demonstrateConfigurationSystem();

        // 4. Optional 안티 패턴 예제
        demonstrateOptionalAntiPatterns();
    }

    /**
     * 사용자 프로필 시스템 예제 시연
     */
    private static void demonstrateUserProfileSystem() {
        System.out.println("1. 사용자 프로필 시스템 예제");

        UserProfileService service = new UserProfileService();

        // 테스트 사용자 데이터 추가
        UserProfile user1 = new UserProfile("user1", "홍길동", "hong@example.com",
                new Address("서울시 강남구", "서울", "12345"));
        UserProfile user2 = new UserProfile("user2", "김철수", "kim@example.com", null);
        UserProfile user3 = new UserProfile("user3", "이영희", "invalid-email",
                new Address("부산시 해운대구", "부산", "67890"));

        service.addUser(user1);
        service.addUser(user2);
        service.addUser(user3);

        // 1.1 주소 정보 조회 예제
        System.out.println("\n1.1 주소 정보 조회 예제:");
        System.out.println("사용자1 주소: " + service.getDisplayAddress("user1"));
        System.out.println("사용자2 주소: " + service.getDisplayAddress("user2"));
        System.out.println("존재하지 않는 사용자 주소: " + service.getDisplayAddress("unknown"));

        // 1.2 이메일 조회 예제 (예외 처리)
        System.out.println("\n1.2 이메일 조회 예제:");
        try {
            System.out.println("사용자1 이메일: " + service.getUserEmail("user1"));
            System.out.println("사용자3 이메일: " + service.getUserEmail("user3")); // 예외 발생
        } catch (IllegalStateException e) {
            System.out.println("예외 발생: " + e.getMessage());
        }

        // 1.3 이메일 업데이트 예제
        System.out.println("\n1.3 이메일 업데이트 예제:");
        service.updateUserEmail("user3", "lee@example.com");
        System.out.println("사용자3 새 이메일: " + service.getUserEmail("user3"));

        System.out.println("\n-----------------------------------");
    }

    /**
     * 상품 재고 관리 시스템 예제 시연
     */
    private static void demonstrateProductInventorySystem() {
        System.out.println("2. 상품 재고 관리 시스템 예제");

        ProductInventoryService service = new ProductInventoryService();

        // 테스트 상품 데이터 추가
        Product product1 = new Product("P001", "노트북", 10, new BigDecimal("1200000"));
        Product product2 = new Product("P002", "스마트폰", 5, new BigDecimal("800000"));

        service.addProduct(product1);
        service.addProduct(product2);

        // 2.1 상품 요약 정보 조회 예제
        System.out.println("\n2.1 상품 요약 정보 조회 예제:");
        System.out.println("상품1 정보: " + service.getProductSummary("P001"));
        System.out.println("존재하지 않는 상품 정보: " + service.getProductSummary("P999"));

        // 2.2 할인 가격 계산 예제
        System.out.println("\n2.2 할인 가격 계산 예제:");
        System.out.println("상품1 20% 할인가: " + service.calculateDiscountedPrice("P001", 0.2));
        System.out.println("존재하지 않는 상품 할인가: " + service.calculateDiscountedPrice("P999", 0.1));

        // 2.3 주문 처리 예제
        System.out.println("\n2.3 주문 처리 예제:");
        try {
            service.processOrder("P001", 2);
            System.out.println("남은 재고: " + service.getProductSummary("P001"));

            // 재고 부족 상황 처리
            service.processOrder("P002", 10); // 예외 발생
        } catch (IllegalStateException e) {
            System.out.println("예외 발생: " + e.getMessage());
        }

        System.out.println("\n-----------------------------------");
    }

    /**
     * 설정 관리 시스템 예제 시연
     */
    private static void demonstrateConfigurationSystem() {
        System.out.println("3. 설정 관리 시스템 예제");

        ConfigurationManager configManager = new ConfigurationManager();

        // 일부 설정만 추가
        configManager.setConfigValue("port", "9090");
        configManager.setConfigValue("allowed.hosts", "localhost,127.0.0.1,example.com");

        // 3.1 포트 설정 예제
        System.out.println("\n3.1 포트 설정 예제:");
        System.out.println("설정된 포트: " + configManager.getPortNumber());

        // 유효하지 않은 포트 값 설정
        configManager.setConfigValue("port", "99999"); // 유효 범위 초과
        System.out.println("유효하지 않은 포트 설정 후: " + configManager.getPortNumber()); // 기본값 사용

        // 3.2 타임아웃 설정 예제
        System.out.println("\n3.2 타임아웃 설정 예제:");
        Duration timeout = configManager.getTimeout(); // 설정 없음, 기본값 사용
        System.out.println("타임아웃 설정: " + timeout.getSeconds() + "초");

        // 타임아웃 설정 추가
        configManager.setConfigValue("timeout", "60");
        timeout = configManager.getTimeout();
        System.out.println("새 타임아웃 설정: " + timeout.getSeconds() + "초");

        // 3.3 허용 호스트 목록 예제
        System.out.println("\n3.3 허용 호스트 목록 예제:");
        List<String> hosts = configManager.getAllowedHosts();
        System.out.println("허용된 호스트 수: " + hosts.size());
        System.out.println("허용된 호스트 목록: " + String.join(", ", hosts));

        System.out.println("\n-----------------------------------");
    }

    /**
     * Optional 안티 패턴 예제 시연
     */
    private static void demonstrateOptionalAntiPatterns() {
        System.out.println("4. Optional 안티 패턴 예제");

        OptionalAntiPatterns antiPatterns = new OptionalAntiPatterns();

        System.out.println("\n4.1 안전하지 않은 Optional 사용 vs 안전한 Optional 사용:");

        try {
            // 안전하지 않은 메서드 호출 시도 (존재하지 않는 사용자)
            String unsafeName = antiPatterns.getUserNameUnsafe("unknown");
            System.out.println("이 코드는 실행되지 않습니다.");
        } catch (Exception e) {
            System.out.println("예상된 예외 발생: " + e.getClass().getSimpleName() + " - " + e.getMessage());
        }

        // 안전한 메서드 호출
        String safeName = antiPatterns.getUserNameSafe("unknown");
        System.out.println("안전한 메서드 결과: " + safeName);

        System.out.println("\n결론: Optional은 null 체크를 위한 도구이지만, 모든 null 참조를 Optional로 대체해야 한다는 의미는 아닙니다.");
        System.out.println("적절한 상황에서 올바르게 사용할 때 코드의 안전성과 가독성을 높일 수 있습니다.");

        System.out.println("\n===== Optional 활용 예제 종료 =====");
    }
}
