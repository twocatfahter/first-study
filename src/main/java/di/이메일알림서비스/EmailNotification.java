package di.이메일알림서비스;

public class EmailNotification implements NotificationService{
    // 이메일을 실제로 보내는 클라이언트 (의존성 주입받는겁니다.)
    private final EmailClient emailClient;

    public EmailNotification(EmailClient emailClient) {
        this.emailClient = emailClient;
    }

    @Override
    public void sendNotification(String message) {
        emailClient.sendEmail("user@example.com", message);
    }
}
