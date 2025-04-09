package singleton;

import singleton.데이터베이스연결관리자.DatabaseConnectionManager;
import singleton.로깅매니저.LogManager;
import singleton.설정관리자.ConfigurationManager;
import singleton.의존성주입싱글톤.ApplicationContext;
import singleton.캐시매니저.CacheManager;

public class SingletonExample {

    public static void main(String[] args) {
        // 1. Double-checked Locking 싱글톤 사용 예제
        DatabaseConnectionManager dbManager = DatabaseConnectionManager.getInstance();
        try {
            // 동일한 연결 인스턴스 재사용
            System.out.println("DB 연결 객체: " + dbManager.getConnection());
            System.out.println("동일 객체 여부: " + (dbManager == DatabaseConnectionManager.getInstance()));
        } catch (Exception e) {
            System.err.println("DB 연결 오류: " + e.getMessage());
        }

        System.out.println("\n-----------------------------------\n");

        // 2. Eager Initialization 싱글톤 사용 예제
        CacheManager cacheManager = CacheManager.getInstance();
        cacheManager.put("user.1", "John Doe");
        cacheManager.put("user.2", "Jane Smith");

        System.out.println("캐시에서 user.1 조회: " + cacheManager.get("user.1"));
        System.out.println("동일 객체 여부: " + (cacheManager == CacheManager.getInstance()));

        System.out.println("\n-----------------------------------\n");

        // 3. Enum 싱글톤 사용 예제
        ConfigurationManager configManager = ConfigurationManager.INSTANCE;
//        configManager.setProperty("app.name", "Singleton Demo");
//        configManager.setProperty("app.version", "1.0.0");
//
//        System.out.println("설정에서 app.name 조회: " + configManager.getProperty("app.name"));
//        System.out.println("설정에서 app.version 조회: " + configManager.getProperty("app.version"));
        System.out.println("동일 객체 여부: " + (configManager == ConfigurationManager.INSTANCE));

        System.out.println("\n-----------------------------------\n");

        // 4. Holder 패턴 싱글톤 사용 예제
        LogManager logManager = LogManager.getInstance();
//        logManager.info("애플리케이션 시작");
//        logManager.debug("디버그 메시지");
//        logManager.warning("경고 메시지");

        try {
            throw new RuntimeException("예외 테스트");
        } catch (Exception e) {
//            logManager.error("예외 발생", e);
        }

        System.out.println("동일 객체 여부: " + (logManager == LogManager.getInstance()));

        System.out.println("\n-----------------------------------\n");

//        // 5. 스레드 풀 매니저 싱글톤 사용 예제
//        ThreadPoolManager threadPoolManager = ThreadPoolManager.getInstance();

//        // CPU 바운드 작업 실행
//        threadPoolManager.execute(() -> {
//            System.out.println("CPU 집약적 작업 실행 - " + Thread.currentThread().getName());
//        }, true);
//
//        // I/O 바운드 작업 실행
//        threadPoolManager.execute(() -> {
//            System.out.println("I/O 집약적 작업 실행 - " + Thread.currentThread().getName());
//        }, false);
//
//        // 주기적 작업 스케줄링 (여기서는 한 번만 실행되도록 짧은 간격 설정)
//        threadPoolManager.scheduleAtFixedRate(() -> {
//            System.out.println("예약된 작업 실행 - " + Thread.currentThread().getName());
//        }, 100, 1000);
//
//        System.out.println("동일 객체 여부: " + (threadPoolManager == ThreadPoolManager.getInstance()));

        try {
            // 스레드 풀 작업이 실행될 시간 부여
            Thread.sleep(200);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        System.out.println("\n-----------------------------------\n");

        // 6. 의존성 주입 지원 싱글톤 사용 예제
        ApplicationContext context = ApplicationContext.getInstance();

        // 서비스 등록
        context.register(ExampleService.class, new ExampleServiceImpl());

        // 팩토리 등록 (지연 초기화)
        context.registerFactory(LazyService.class, LazyServiceImpl::new);

        // 등록된 서비스 사용
        ExampleService service = context.getBean(ExampleService.class);
        System.out.println(service.getMessage());

        // 지연 초기화된 서비스 사용
        LazyService lazyService = context.getBean(LazyService.class);
        System.out.println(lazyService.getStatus());

        // 등록된 빈 목록 출력
        System.out.println("\n등록된 빈 목록:");
        for (Class<?> type : context.getBeanTypes()) {
            System.out.println(" - " + type.getSimpleName());
        }

        System.out.println("동일 객체 여부: " + (context == ApplicationContext.getInstance()));

        // 리소스 정리
        try {
            dbManager.closeConnection();
//            logManager.close();
//            threadPoolManager.shutdown();
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("\n싱글톤 패턴 테스트 완료!");
    }
}

/**
 * 예제 서비스 인터페이스
 */
interface ExampleService {
    String getMessage();
}

/**
 * 예제 서비스 구현체
 */
class ExampleServiceImpl implements ExampleService {
    @Override
    public String getMessage() {
        return "ExampleService 구현체에서 제공하는 메시지";
    }
}

/**
 * 지연 초기화 서비스 인터페이스
 */
interface LazyService {
    String getStatus();
}

/**
 * 지연 초기화 서비스 구현체
 */
class LazyServiceImpl implements LazyService {
    public LazyServiceImpl() {
        System.out.println("LazyServiceImpl이 지연 초기화되었습니다.");
    }

    @Override
    public String getStatus() {
        return "LazyService가 활성화되었습니다.";
    }
}

