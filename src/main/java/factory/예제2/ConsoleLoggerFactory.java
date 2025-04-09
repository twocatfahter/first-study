package factory.예제2;

public class ConsoleLoggerFactory extends LoggerFactory{
    @Override
    public Logger createLogger() {
        return new ConsoleLogger(LogLevel.INFO);
    }
}
