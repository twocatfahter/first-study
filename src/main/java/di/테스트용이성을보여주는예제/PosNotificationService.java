package di.테스트용이성을보여주는예제;

public class PosNotificationService implements NotificationService{
    @Override
    public void sendNotification(String message) {
        System.out.println("포스기에 알림 전송: "  + message);
    }
}
