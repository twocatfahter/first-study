package singleton.assignment.basecode;

/**
 * Logger 클래스의 사용을 테스트하는 클래스입니다.
 * 현재는 싱글톤이 아닌 일반 클래스로 구현된 Logger를 사용합니다.
 */
public class LoggerTest {
    public static void main(String[] args) {
        // 각 클래스에서 Logger 객체를 생성하는 방식 (싱글톤이 아닌 경우)
        Logger logger1 = new Logger("application.log", LogLevel.INFO);
        Logger logger2 = new Logger("application.log", LogLevel.DEBUG);

        // 두 Logger 객체가 서로 다른 인스턴스인지 확인
        System.out.println("logger1과 logger2는 같은 객체인가? " + (logger1 == logger2));
        System.out.println("logger1의 최소 로그 레벨: " + logger1.getMinLogLevel());
        System.out.println("logger2의 최소 로그 레벨: " + logger2.getMinLogLevel());

        // logger1으로 로깅 시도
        logger1.debug("이 디버그 메시지는 INFO 레벨 설정으로 인해 출력되지 않아야 합니다.");
        logger1.info("정보 메시지 from logger1");
        logger1.warning("경고 메시지 from logger1");

        // logger2로 로깅 시도
        logger2.debug("디버그 메시지 from logger2 - 출력되어야 함");
        logger2.info("정보 메시지 from logger2");

        try {
            // 예외 발생 테스트
            int result = 10 / 0;
        } catch (Exception e) {
            logger1.error("예외 발생", e);
        }

        System.out.println("\n싱글톤 패턴을 적용한 후에는 모든 Logger 객체가 동일한 인스턴스여야 합니다.");
        System.out.println("또한 로그 레벨 변경 시 모든 위치에서 동일하게 적용되어야 합니다.");
    }
}
