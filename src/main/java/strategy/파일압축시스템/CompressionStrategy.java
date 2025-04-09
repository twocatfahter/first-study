package strategy.파일압축시스템;

import java.io.File;
import java.util.List;

public interface CompressionStrategy {
    void compressFiles(List<File> files, String destination);
}
