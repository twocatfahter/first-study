package di.이메일알림서비스;

public class SMSNotification implements NotificationService{
    private final SMSClient smsClient;

    public SMSNotification(SMSClient smsClient) {
        this.smsClient = smsClient;
    }

    @Override
    public void sendNotification(String message) {
        smsClient.sendSMS("01000000000", message);
    }
}
