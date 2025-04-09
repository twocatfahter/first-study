package builder.개선;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class EmailMessage {
    private final String from;
    private final List<String> to;
    private final List<String> cc;
    private final String subject;
    private final String content;
    private final List<String> attachments;
    private final boolean urgent;
    private final LocalDateTime scheduledTime;

    public EmailMessage(Builder builder) {
        this.from = builder.from;
        this.to = List.copyOf(builder.to);
        this.cc = List.copyOf(builder.cc);
        this.attachments = List.copyOf(builder.attachments);
        this.subject = builder.subject;
        this.content = builder.content;
        this.urgent = builder.urgent;
        this.scheduledTime = builder.scheduledTime;
    }

    // from
    public static class Builder {
        private final String from;

        private List<String> to = new ArrayList<>();
        private List<String> cc = new ArrayList<>();
        private String subject = "";
        private String content = "";
        private List<String> attachments = new ArrayList<>();
        private boolean urgent = false;
        private LocalDateTime scheduledTime = LocalDateTime.now();

        public Builder(String from) {
            this.from = Objects.requireNonNull(from, "From address must not be null");
        }

        public Builder to(List<String> to) {
            this.to.addAll(to);
            return this;
        }

        public Builder cc(List<String> cc) {
            this.cc.addAll(cc);
            return this;
        }

        public Builder subject(String subject) {
            this.subject = subject;
            return this;
        }

        public Builder content(String content) {
            this.content = content;
            return this;
        }

        public Builder attachments(List<String> attachments) {
            this.attachments.addAll(attachments);
            return this;
        }

        public Builder urgent(boolean urgent) {
            this.urgent = urgent;
            return this;
        }

        public Builder scheduledTime(LocalDateTime scheduledTime) {
            this.scheduledTime = scheduledTime;
            return this;
        }

        public EmailMessage build() {
            return new EmailMessage(this);
        }
    }

    // 선택매개변수 기본값을 전체다
}
