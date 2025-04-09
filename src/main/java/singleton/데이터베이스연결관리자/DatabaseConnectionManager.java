package singleton.데이터베이스연결관리자;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *  Double - Check 싱글톤 예제
 */
public class DatabaseConnectionManager {
    // volatile 키워드: 멀티스레드 환경에서 변수의 가시성(visibility) 보장
    // 메인 메모리에서 저장되기때문에 다른 스레드에서 항상 최신 값을 읽을수 있다.
    private static volatile DatabaseConnectionManager instance;

    // 데이터베이스 연결 객체
    private Connection connection;

    // 데이터베이스 접속 정보
    private final String url;
    private final String username;
    private final String password;

    // 싱글톤 패턴의 핵심요소 중 하나
    private DatabaseConnectionManager() {
        this.url = "jdbc:mysql://localhost:3306/db";
        this.username = "user";
        this.password = "password";
    }

    public static DatabaseConnectionManager getInstance() {
        // 첫번째 검사 - 동기화 없이 빠른검사
        if (instance == null) {
            // 여러 스레드가 동시에 이 블록에 접근하는 것을 방지
            synchronized (DatabaseConnectionManager.class) {
                // 두 번째 검사 -> 동기화 블록 내에서 다시검사
                if (instance == null) {
                    instance = new DatabaseConnectionManager();
                }
            }
        }
        return instance;
    }

    public Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(url, username, password);
        }
        return connection;
    }

    public void closeConnection() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
            connection = null; // GC (가비지컬렉터) 알아서 회수할 수 있도록 제거 (참조제거)
        }
    }

    // 주의 하여야한다. 싱글톤은 전역에서 관리합니다.
    public static void reset() {
        synchronized (DatabaseConnectionManager.class) {
            if (instance != null) {
                try {
                    instance.closeConnection();
                } catch (SQLException e) {
                    // 로깅 처리
                }
            }
            instance = null;
        }
    }
}
