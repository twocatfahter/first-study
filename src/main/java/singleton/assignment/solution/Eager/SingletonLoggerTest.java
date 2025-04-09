package singleton.assignment.solution.Eager;

import singleton.assignment.basecode.LogLevel;

public class SingletonLoggerTest {
    public static void main(String[] args) {
        // 첫 번째 로거 인스턴스 가져오기
        LoggerSingleton logger1 = LoggerSingleton.getInstance();
        logger1.initialize("application_eager.log", LogLevel.INFO);

        // 두 번째 로거 인스턴스 가져오기
        LoggerSingleton logger2 = LoggerSingleton.getInstance();

        // 싱글톤 검증: 두 참조 변수가 동일한 인스턴스를 가리키는지 확인
        System.out.println("싱글톤 패턴 테스트:");
        System.out.println("logger1과 logger2는 같은 객체인가? " + (logger1 == logger2));
        System.out.println("logger1의 최소 로그 레벨: " + logger1.getMinLogLevel());
        System.out.println("logger2의 최소 로그 레벨: " + logger2.getMinLogLevel());

        System.out.println("\n로그 작성 테스트:");

        // logger1으로 로깅 시도
        logger1.debug("이 디버그 메시지는 INFO 레벨 설정으로 인해 출력되지 않아야 합니다.");
        logger1.info("정보 메시지 from logger1");
        logger1.warning("경고 메시지 from logger1");

        // 로그 레벨 변경 후 테스트
        System.out.println("\n로그 레벨 변경 테스트:");
        logger1.setMinLogLevel(LogLevel.DEBUG);
        System.out.println("logger1에서 로그 레벨 변경: " + logger1.getMinLogLevel());
        System.out.println("logger2의 로그 레벨 (동일해야 함): " + logger2.getMinLogLevel());

        // logger2로 로깅 시도
        logger2.debug("디버그 메시지 from logger2 - 이제 출력되어야 함");
        logger2.info("정보 메시지 from logger2");

        // 예외 처리 테스트
        System.out.println("\n예외 처리 테스트:");
        try {
            // 0으로 나누기 예외 발생
            int result = 10 / 0;
        } catch (Exception e) {
            logger1.error("예외 발생", e);
        }

        // 멀티스레드 환경 테스트
        System.out.println("\n멀티스레드 환경 테스트:");
        for (int i = 0; i < 5; i++) {
            new Thread(() -> {
                LoggerSingleton threadLogger = LoggerSingleton.getInstance();
                System.out.println("스레드 " + Thread.currentThread().getName() +
                        "에서의 로거 인스턴스: " + threadLogger);
                threadLogger.info("스레드 " + Thread.currentThread().getName() +
                        "에서 로그 작성");
            }).start();
        }

        // 모든 스레드가 실행을 완료할 때까지 대기
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        System.out.println("\n즉시 초기화 싱글톤 로거 테스트 완료!");
    }
}
