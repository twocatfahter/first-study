package factory.예제2;

// 1. Logger 인터페이스
public interface Logger {
    void log(String message);
    void error(String message);
    LogLevel getLogLevel();
}
