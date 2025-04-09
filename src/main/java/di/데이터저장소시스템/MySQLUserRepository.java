package di.데이터저장소시스템;

import javax.sql.DataSource;

public class MySQLUserRepository implements UserRepository{
    private final DataSource dataSource;

    public MySQLUserRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public String findById(String id) {
        System.out.println("MySQL DB 에서 ID " + id + "인 사용자 검색 중입니다...");
        return "";
    }

    @Override
    public void save(User user) {
        System.out.println("MySQL DB 에서 사용자 저장: " + user.getName());
    }
}
