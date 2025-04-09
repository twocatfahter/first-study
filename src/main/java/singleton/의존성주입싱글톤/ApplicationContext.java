package singleton.의존성주입싱글톤;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class ApplicationContext {
    // 싱글톤 객체 저장소
    private final Map<Class<?>, Object> singletons = new HashMap<>();

    // 팩토리 저장소 (객체 생성 방법)
    private final Map<Class<?>, Supplier<?>> factories = new HashMap<>();

    // 싱글톤 홀더 클래스
    private static class ContextHolder {
        private static final ApplicationContext INSTANCE = new ApplicationContext();
    }

    private ApplicationContext() {
        // 기본 바인딩 설정
        // 자기 자신을 컨텍스트로 등록
        singletons.put(ApplicationContext.class, this);
    }

    public static ApplicationContext getInstance() {
        return ContextHolder.INSTANCE;
    }

    public <T> void register(Class<T> type, T instance) {
        if (type == null || instance == null) {
            throw new IllegalArgumentException("타입과 인스턴스는 null 일 수 없습니다.");
        }

        singletons.put(type, instance);
    }

    public <T> void registerFactory(Class<T> type, Supplier<T> factory) {
        if (type == null || factory == null) {
            throw new IllegalArgumentException("타입과 인스턴스는 null 일 수 없습니다.");
        }

        factories.put(type, factory);
    }

    @SuppressWarnings("unchecked")
    public <T> T getBean(Class<T> type) {
        // 이미 생성된 싱글톤이 있는지 확인
        Object instance = singletons.get(type);

        if (instance == null) {
            // 등록된 팩토리가 있는지 확인
            @SuppressWarnings("unchecked")
            Supplier<T> factory = (Supplier<T>) factories.get(type);

            if (factory != null) {
                // 팩토리로 객체를 생성 후에 싱글톤으로 등록
                instance = factory.get();
                singletons.put(type, instance);
            }
        }

        return (T) instance;
    }

    public Class<?>[] getBeanTypes() {
        // 중복제거를 위한 Set 으로 변환후에 다시 배열로 변환
        return singletons.keySet().stream()
                .filter(type -> !ApplicationContext.class.equals(type))
                .toArray(Class<?>[]::new);
    }
 }
