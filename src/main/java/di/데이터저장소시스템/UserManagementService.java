package di.데이터저장소시스템;

import di.이메일알림서비스.NotificationService;

public class UserManagementService {
    private final UserRepository repository;
    private final NotificationService notificationService;

    public UserManagementService(UserRepository repository, NotificationService notificationService) {
        this.repository = repository;
        this.notificationService = notificationService;
    }

    public void createUser(User user) {
        repository.save(user);
        notificationService.sendNotification("User created: " + user.getName());
    }
}
