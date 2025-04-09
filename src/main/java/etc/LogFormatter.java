package etc;

import java.time.LocalDateTime;

public class LogFormatter {

    public String formatErrorLog(String errorCode, String message, String stackTrace) {
        return """
                ERROR REPORT
                ============
                Time: %s
                Code: %s
                Message: %s
                
                Stack Trace:
                %s
                """.formatted(
                LocalDateTime.now(),
                errorCode,
                message,
                stackTrace
        );
    }
}
