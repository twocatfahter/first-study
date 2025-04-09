package builder.개선;

import java.util.*;

public class QueryBuilder {
    private final String table;
    private final List<String> selectColumns;
    private final List<String> whereConditions;
    private final List<String> orderByColumns;
    private final Integer limit;
    private final Integer offset;

    // private 생성자
    private QueryBuilder(Builder builder) {
        this.table = builder.table;
        this.selectColumns = new ArrayList<>(builder.selectColumns);
        this.whereConditions = new ArrayList<>(builder.whereConditions);
        this.orderByColumns = new ArrayList<>(builder.orderByColumns);
        this.limit = builder.limit;
        this.offset = builder.offset;
    }

    // static builder
    public static class Builder {
        private final String table;
        private final List<String> selectColumns = new ArrayList<>();
        private final List<String> whereConditions = new ArrayList<>();
        private final List<String> orderByColumns = new ArrayList<>();
        private Integer limit;
        private Integer offset;

        public Builder(String table) {
            this.table = Objects.requireNonNull(table, "Table name must not be null");
        }

        // table
        // name, age, phoneNumber, email
        // select * from
        // String[] columns = [name, age, email]
        // where a = 'a'
        // and b = 'b'
        public Builder select(String... columns) {
            if (columns.length == 0) {
                this.selectColumns.add("*");
            } else {
                this.selectColumns.addAll(Arrays.asList(columns));
            }
            return this;
        }

        public Builder where(String condition) {
            this.whereConditions.add(condition);
            return this;
        }

        public Builder orderBy(String... columns) {
            this.orderByColumns.addAll(Arrays.asList(columns));
            return this;
        }

        public Builder limit(int limit) {
            if (limit < 0) {
                throw new IllegalArgumentException("Limit must be positive");
            }
            this.limit = limit;
            return this;
        }

        public Builder offset(int offset) {
            if (offset < 0) {
                throw new IllegalArgumentException("Offset must be positive");
            }
            this.offset = offset;
            return this;
        }

        public QueryBuilder build() {
            return new QueryBuilder(this);
        }
    }

    public String buildQuery() {
        StringBuilder sb = new StringBuilder();

        // SELECT 절
        sb.append("SELECT ");
        //sb -> "SELECT name, age, email FROM "
        // [name, age, email]
        sb.append(String.join(", ", selectColumns.isEmpty() ?
                Collections.singletonList("*") : selectColumns));

        // FROM 절
        sb.append(" FROM ").append(table);
        //sb -> "SELECT name, age, email FROM table"

        // WHERE 절
        if (!whereConditions.isEmpty()) {
            sb.append(" WHERE ")
                    .append(String.join(" AND ", whereConditions));
        }

        // ORDER BY 절
        if (!orderByColumns.isEmpty()) {
            sb.append(" ORDER BY ")
                    .append(String.join(", ", orderByColumns));
        }

        if (limit != null) {
            sb.append(" LIMIT ")
                    .append(limit);
        }

        if (offset != null) {
            sb.append(" OFFSET ")
                    .append(offset);
        }

        return sb.toString();
    }

}
