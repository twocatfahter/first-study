package factory.예제2;

public abstract class LoggerFactory {
    public abstract Logger createLogger();

    public static LoggerFactory getFactory(LoggerType type) {
        return switch (type) {
            case CONSOLE -> new ConsoleLoggerFactory();
            case FILE -> new FileLoggerFactory();
            default -> throw new IllegalArgumentException("Unsupported logger type");
        };
    }
}
