package optional.설정관리시스템;


import java.time.Duration;
import java.util.*;

public class ConfigurationManager {
    private Properties properties = new Properties();

    // 설정 키로 설정 값을 조회하는 메소드
    public Optional<String> getConfigValue(String key) {
        return Optional.ofNullable(properties.getProperty(key));
    }

    // 포트 번호 설정을 조회하는 메소드
    public int getPortNumber() {
        return getConfigValue("port")
                .map(Integer::parseInt)
                .filter(port -> port >= 0 && port <= 65535)
                .orElse(8080);
    }

    // 타임아웃 설정을 조회하는 메소드
    public Duration getTimeout() {
        return getConfigValue("timeout")
                .map(Integer::parseInt)
                .map(Duration::ofSeconds)
                .orElseGet(() -> {
                    System.out.println("기본 타임아웃 설정 사용");
                    return Duration.ofSeconds(30);
                });
    }

    // 호스트 목록을 조회하는 메소드
    public List<String> getAllowedHosts() {
        return getConfigValue("allowed.hosts")
                .map(hosts -> hosts.split(","))
                .map(Arrays::asList)
                .orElseGet(ArrayList::new);
    }

    // 설정 값을 추가하는 메소드
    public void setConfigValue(String key, String value) {
        properties.setProperty(key, value);
    }
}
