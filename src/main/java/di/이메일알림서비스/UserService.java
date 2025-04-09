package di.이메일알림서비스;

public class UserService {
    // 의존성
    private final NotificationService service;

    public UserService(NotificationService service) {
        this.service = service;
    }

    public void registerUser(String username) {
        System.out.println("사용자 등록: " + username);

        service.sendNotification("Welcome " + username);
    }
}
