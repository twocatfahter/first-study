package di.데이터저장소시스템;

public interface UserRepository {
    String findById(String id);

    void save(User user);
}
