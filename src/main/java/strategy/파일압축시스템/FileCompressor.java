package strategy.파일압축시스템;

import lombok.AllArgsConstructor;

import java.io.File;
import java.util.List;

@AllArgsConstructor
public class FileCompressor {
    private CompressionStrategy strategy;

    public void compressFiles(List<File> files, String destination) {
        strategy.compressFiles(files, destination);
    }
}
