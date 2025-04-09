package singleton.로깅매니저;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 *
 */
public class LogManager {

    // 로그 레벨
    public enum LogLevel {
        DEBUG, INFO, WARNING, ERROR;

        @Override
        public String toString() {
            return name();
        }
    }

    // 현재 로그 레벨 - 기본값은 INFO
    private LogLevel currentLogLevel = LogLevel.INFO;

    // 날짜/시간 포맷터
    private final DateTimeFormatter dateTimeFormatter =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");

    // 로그 파일 경로
    private final String logFilePath;

    // 로그 작성기
    private PrintWriter logWriter;

    /**
     *  LazyHolder 정적 내부클래스
     */
    private static class LazyHolder {
        private static final LogManager INSTANCE = new LogManager();
    }

    private LogManager() {
        this("application.log");
    }

    private LogManager(String logFilePath) {
        this.logFilePath = logFilePath;
        try {
            // 로그 파일 추가 모드로 열기
            this.logWriter = new PrintWriter(new FileWriter(logFilePath, true));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        log(LogLevel.INFO, "로깅 시스템 초기화 완료");
    }

    public static LogManager getInstance() {
        return LazyHolder.INSTANCE;
    }

    public void log(LogLevel logLevel, String message) {
        // 현재 설정된 로그 레벨보다 낮은 레벨은 무시
        if (logLevel.ordinal() < currentLogLevel.ordinal()) {
            return;
        }

        String timestamp = LocalDateTime.now().format(dateTimeFormatter);

        // 로그형식: [시간] [레벨] 메시지
        String formattedMessage = String.format("[%s] [%s] %s", timestamp, logLevel, message);

        logWriter.println(formattedMessage);

        logWriter.flush();

        System.out.println(formattedMessage);
    }
}
