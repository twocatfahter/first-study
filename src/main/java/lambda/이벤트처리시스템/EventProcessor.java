package lambda.이벤트처리시스템;

import java.util.*;

record Event(String userId) {

}

// 다양한 이벤트 타입 대해서 핸들러 등록하고 처리할 수 잇는 기능을 제공
// 람다식을 활용할겁니다.
public class EventProcessor {


    /**
     * 이벤트 핸들러를 정의하는 함수형 인터페이스
     *
     */
    @FunctionalInterface
    interface EventHandler<T> {

        void handle(T event);
    }

    // 이벤트 타입별 핸들러 목록을 저장하는 맵
    private final Map<String, List<EventHandler<Event>>> eventHandlers = new HashMap<>();

    // 특정 이벤트 타입에 대한 핸들러를 등록하는 메서드
    public void registerHandler(String eventType, EventHandler<Event> handler) {
        // computeIfAbsent -> 키가 없으면 키 뒤에 default 값이 반환된다. 함수가 올수았다
        eventHandlers.computeIfAbsent(eventType, k -> new ArrayList<>())
                .add(handler);
    }

    // 특정 이벤트를 처리하는 메서드
    public void processEvent(String eventType, Event event) {
        // getOrDefault -> 키가 없으면 빈리스트를 반환해라
        eventHandlers.getOrDefault(eventType, Collections.emptyList())
                .forEach(handler -> handler.handle(event));
    }

    // 다양한 이벤트 핸들러를 등록하는 예시
    public void example() {
        // 로깅 핸들러
        registerHandler("USER_LOGIN", event -> System.out.println("User logged in: " + event.userId()));

        // 이메일 발송 핸들러
        registerHandler("USER_REGISTRATION", event -> System.out.println("Sending welcome email to: " + event.userId()));
    }

    // 이메일 발송로직 sendEmail
    public void sendEmail(String userId) {
        System.out.println("Sending email to: " + userId);
    }
    // 재고 업데이트 로직 updateInventory
    private void updateInventory(Event event) {
        System.out.println("Updating inventory for user: " + event.userId());
    }
    // 구매 확인 발송 로직 sendConfirmation
    private void sendConfirmation(Event event) {
        System.out.println("Sending purchase confirmation to: " + event.userId());
    }

    public static void main(String[] args) {
        EventProcessor eventProcessor = new EventProcessor();
        eventProcessor.example();
    }

}
