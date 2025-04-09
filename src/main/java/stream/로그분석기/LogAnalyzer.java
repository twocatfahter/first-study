package stream.로그분석기;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class LogAnalyzer {
    public static void main(String[] args) {

        List<LogEntry> logs = getLogs();

        // 1.  에러 로그 필터링 및 소스별 집계
        Map<String, Long> errorsBySource = logs.stream()
                .filter(log -> "ERROR".equals(log.getLevel()))
                .collect(Collectors.groupingBy(
                        LogEntry::getSource,
                        Collectors.counting()
                ));

        // 2.  시간대별 로그 수 집계
        Map<Integer, Long> logsByHour = logs.stream()
                .collect(Collectors.groupingBy(
                        log -> log.getTimestamp().getHour(),
                        Collectors.counting()
                ));

        // 3.  특정 키워드를 포함한 로그 검색
        String keyword = "exception";
        List<LogEntry> exceptionLogs = logs.stream()
                .filter(log -> log.getMessage().contains(keyword))
                .sorted(Comparator.comparing(LogEntry::getTimestamp))
                .toList();


        // 4. 로그 레벨별 메시지 연결
        Map<String, String> concatenationMessages = logs.stream()
                .collect(Collectors.groupingBy(
                        LogEntry::getLevel,
                        Collectors.mapping(
                                LogEntry::getMessage,
                                Collectors.joining("\n")
                        )
                ));

    }

    private static List<LogEntry> getLogs() {
        return List.of(
                new LogEntry(LocalDateTime.now(), "INFO", "Application started", "app"),
                new LogEntry(LocalDateTime.now(), "ERROR", "Division by zero", "app"),
                new LogEntry(LocalDateTime.now(), "ERROR", "Null pointer exception", "app"),
                new LogEntry(LocalDateTime.now(), "INFO", "Application stopped", "app")
        );
    }
}
