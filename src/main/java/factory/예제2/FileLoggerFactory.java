package factory.예제2;

public class FileLoggerFactory extends LoggerFactory{
    @Override
    public Logger createLogger() {
        return new FileLogger("app.log", LogLevel.DEBUG);
    }
}
