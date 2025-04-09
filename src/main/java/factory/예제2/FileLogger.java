package factory.예제2;

public class FileLogger implements Logger{
    private final String filePath;
    private final LogLevel logLevel;

    public FileLogger(String filePath, LogLevel logLevel) {
        this.filePath = filePath;
        this.logLevel = logLevel;
    }

    @Override
    public void log(String message) {
        // 파일에 로그 작성로직
        System.out.println("[ " + logLevel + " ] " + filePath);
    }

    @Override
    public void error(String message) {
        // 파일에 에러 로그 작성로직
    }

    @Override
    public LogLevel getLogLevel() {
        return logLevel;
    }
}
