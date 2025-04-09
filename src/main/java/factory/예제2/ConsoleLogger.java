package factory.예제2;

public class ConsoleLogger implements Logger{
    private final LogLevel logLevel;

    public ConsoleLogger(LogLevel logLevel) {
        this.logLevel = logLevel;
    }

    @Override
    public void log(String message) {
        System.out.println("[INFO] " + message);
    }

    @Override
    public void error(String message) {
        System.out.println("[ERROR] " + message);
    }

    @Override
    public LogLevel getLogLevel() {
        return logLevel;
    }
}
