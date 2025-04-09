package singleton.assignment.basecode;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 로깅을 담당하는 클래스입니다.
 * 이 클래스는 싱글톤 패턴으로 구현되어야 합니다.
 *
 * 현재는 싱글톤 패턴이 적용되어 있지 않은 상태입니다.
 * 싱글톤 패턴을 적용하여 이 클래스를 수정해야 합니다.
 */
public class Logger {
    // 로그 파일 경로
    private String logFilePath;
    // 로그 레벨 (DEBUG, INFO, WARNING, ERROR)
    private LogLevel minLogLevel;

    /**
     * Logger 생성자
     * @param logFilePath 로그 파일 경로
     * @param minLogLevel 최소 로그 레벨
     */
    public Logger(String logFilePath, LogLevel minLogLevel) {
        this.logFilePath = logFilePath;
        this.minLogLevel = minLogLevel;
    }

    /**
     * 로그 메시지를 파일에 기록합니다.
     * @param level 로그 레벨
     * @param message 로그 메시지
     */
    public void log(LogLevel level, String message) {
        // 설정된 최소 레벨보다 낮은 레벨의 로그는 무시
        if (level.getValue() < minLogLevel.getValue()) {
            return;
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String timestamp = dateFormat.format(new Date());
        String logEntry = String.format("[%s] [%s] %s%n", timestamp, level, message);

        try (PrintWriter writer = new PrintWriter(new FileWriter(logFilePath, true))) {
            writer.print(logEntry);
            // 콘솔에도 출력
            System.out.print(logEntry);
        } catch (IOException e) {
            System.err.println("로그 파일 작성 중 오류 발생: " + e.getMessage());
        }
    }

    /**
     * DEBUG 레벨 로그 메시지를 기록합니다.
     * @param message 로그 메시지
     */
    public void debug(String message) {
        log(LogLevel.DEBUG, message);
    }

    /**
     * INFO 레벨 로그 메시지를 기록합니다.
     * @param message 로그 메시지
     */
    public void info(String message) {
        log(LogLevel.INFO, message);
    }

    /**
     * WARNING 레벨 로그 메시지를 기록합니다.
     * @param message 로그 메시지
     */
    public void warning(String message) {
        log(LogLevel.WARNING, message);
    }

    /**
     * ERROR 레벨 로그 메시지를 기록합니다.
     * @param message 로그 메시지
     */
    public void error(String message) {
        log(LogLevel.ERROR, message);
    }

    /**
     * ERROR 레벨 로그 메시지와 예외 스택 트레이스를 기록합니다.
     * @param message 로그 메시지
     * @param e 예외 객체
     */
    public void error(String message, Exception e) {
        log(LogLevel.ERROR, message + " - " + e.getMessage());
        // 예외 스택 트레이스 출력
        e.printStackTrace(System.err);
    }

    /**
     * 최소 로그 레벨을 설정합니다.
     * @param level 설정할 로그 레벨
     */
    public void setMinLogLevel(LogLevel level) {
        this.minLogLevel = level;
    }

    /**
     * 현재 설정된 최소 로그 레벨을 반환합니다.
     * @return 현재 최소 로그 레벨
     */
    public LogLevel getMinLogLevel() {
        return this.minLogLevel;
    }

    /**
     * 로그 파일 경로를 반환합니다.
     * @return 로그 파일 경로
     */
    public String getLogFilePath() {
        return this.logFilePath;
    }
}
