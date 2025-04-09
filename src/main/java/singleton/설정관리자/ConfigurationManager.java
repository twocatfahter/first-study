package singleton.설정관리자;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

public enum ConfigurationManager {
    INSTANCE;

    private final Properties properties;

    private final ConcurrentHashMap<String, String> cachedProperties;

    private static final String DEFAULT_CONFIG_FILE = "config.properties";
    private String configFilePath;

    ConfigurationManager() {
        this.properties = new Properties();
        this.cachedProperties = new ConcurrentHashMap<>();
        this.configFilePath = DEFAULT_CONFIG_FILE;
        loadProperties();
    }

    private void loadProperties() {
        try (InputStream input = getClass().getClassLoader()
                .getResourceAsStream(configFilePath)){

            if (input == null) {
                System.err.println("설정 파일 '" + configFilePath + "'을 찾을 수 없습니다.");
                return;
            }

            properties.clear();
            cachedProperties.clear();

            properties.load(input);

            System.out.println("설정 파일 '" + configFilePath + "' 로드 완료");

        } catch (IOException e) {
            throw new RuntimeException("설정 파일 로드 실패: " + e.getMessage(), e);
        }
    }

}
