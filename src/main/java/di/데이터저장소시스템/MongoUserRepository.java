package di.데이터저장소시스템;

public class MongoUserRepository implements UserRepository{
    private final MongoClient client;

    public MongoUserRepository(MongoClient client) {
        this.client = client;
    }

    @Override
    public String findById(String id) {
        return client.findById(id);
    }

    @Override
    public void save(User user) {
        System.out.println("MongoDB에 사용자 저장: " + user.getName());
        client.save(user.toString());
    }
}
