package etc;

public class QueryBuilder {
    // 기존 방식
    public String getOldStyleQuery() {
        return "SELECT u.id, u.name, u.email, " +
                "a.street, a.city, a.country " +
                "FROM users u " +
                "LEFT JOIN addresses a ON u.id = a.user_id " +
                "WHERE u.active = true " +
                "ORDER BY u.name";
    }

    // Text block
    public String getModernQuery() {
        return """
                SELECT  u.id, u.name, u.email,
                        a.street, a.city, a.country,
                FROM users u
                LEFT JOIN addresses a ON u.id = a.user_id
                WHERE u.active = true
                ORDER BY u.name
                """;
    }

    // 동적 쿼리 생성
    public String getDynamicQuery(String tableName, String[] columns) {
        return """
                SELECT %s
                FROM %s
                WHERE active = true
                ORDER BY created_at DESC
                """.formatted(String.join(", ", columns), tableName);
    }
}
