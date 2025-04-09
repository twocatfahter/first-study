package exception.파일처리시스템;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

public class FileProcessorDemo {

    public static void main(String[] args) {
        System.out.println("===== 파일처리시스템 예외 처리 데모 =====\n");

        // 임시 테스트 파일 생성
        String validFile = createTempFile("valid.txt",
                "data1,value1\n" +
                        "data2,value2\n" +
                        "data3,value3\n" +
                        "# This is a comment\n" +
                        "\n" +
                        "data4,value4");

        String invalidFile = createTempFile("invalid.txt",
                "data1,value1\n" +
                        "invalid_line_without_comma\n" +
                        "data2,value2");

        String nonExistentFile = "non_existent_file.txt";

        String outputFile = Paths.get(System.getProperty("java.io.tmpdir"), "output.txt").toString();

        // FileProcessor 인스턴스 생성
        FileProcessor processor = new FileProcessor();

        // 시나리오 1: 정상 파일 처리
        System.out.println("시나리오 1: 정상 파일 처리");
        try {
            List<String> results = processor.readAndProcessFile(validFile);
            System.out.println("처리 결과 (라인 수: " + results.size() + "):");
            for (String line : results) {
                System.out.println("  " + line);
            }
        } catch (FileProcessor.FileProcessingException e) {
            System.out.println("예외 발생: " + e.getMessage());
        }
        System.out.println();

        // 시나리오 2: 형식이 잘못된 파일 처리 (일부 오류 무시)
        System.out.println("시나리오 2: 형식이 잘못된 파일 처리 (일부 오류 무시)");
        try {
            List<String> results = processor.readAndProcessFile(invalidFile);
            System.out.println("처리 결과 (라인 수: " + results.size() + "):");
            for (String line : results) {
                System.out.println("  " + line);
            }
        } catch (FileProcessor.FileProcessingException e) {
            System.out.println("예외 발생: " + e.getMessage());
        }
        System.out.println();

        // 시나리오 3: 존재하지 않는 파일 처리
        System.out.println("시나리오 3: 존재하지 않는 파일 처리");
        try {
            List<String> results = processor.readAndProcessFile(nonExistentFile);
            System.out.println("처리 결과: " + results.size() + " 라인");
        } catch (FileProcessor.FileNotFoundException e) {
            System.out.println("예외 발생: " + e.getMessage());
            System.out.println("찾을 수 없는 파일 경로: " + e.getFilePath());
        } catch (FileProcessor.FileProcessingException e) {
            System.out.println("기타 예외 발생: " + e.getMessage());
        }
        System.out.println();

        // 시나리오 4: 파일 변환 처리
        System.out.println("시나리오 4: 파일 변환 처리");
        try {
            // 각 라인에 적용할 변환 함수 정의
            Function<String, String> transformer = line -> {
                if (line.trim().isEmpty() || line.startsWith("#")) {
                    return line; // 빈 라인이나 주석은 그대로 유지
                }
                String[] parts = line.split(",");
                if (parts.length >= 2) {
                    // 첫 번째 값과 두 번째 값 위치 바꾸기
                    return parts[1] + "," + parts[0];
                }
                return line.toUpperCase(); // 형식이 맞지 않으면 대문자로 변환
            };

            processor.processAndTransformFile(validFile, outputFile, transformer);
            System.out.println("파일 변환 완료: " + outputFile);

            // 변환된 파일 내용 출력
            System.out.println("변환된 파일 내용:");
            List<String> transformedContent = processor.safeReadFile(outputFile);
            for (String line : transformedContent) {
                System.out.println("  " + line);
            }
        } catch (FileProcessor.FileProcessingException e) {
            System.out.println("예외 발생: " + e.getMessage());
        }
        System.out.println();

        // 시나리오 5: 안전한 파일 처리 (다양한 예외 처리)
        System.out.println("시나리오 5: 안전한 파일 처리 (다양한 예외 처리)");
        processor.processFileSafely(validFile, outputFile);
        System.out.println();

        processor.processFileSafely(nonExistentFile, outputFile);
        System.out.println();

        // 시나리오 6: 여러 파일 일괄 처리
        System.out.println("시나리오 6: 여러 파일 일괄 처리");
        List<String> fileBatch = Arrays.asList(
                validFile,
                invalidFile,
                nonExistentFile,
                validFile + ".backup");

        List<String> failedFiles = processor.batchProcessFiles(fileBatch);

        System.out.println("처리 결과:");
        System.out.println("  총 파일 수: " + fileBatch.size());
        System.out.println("  성공한 파일 수: " + (fileBatch.size() - failedFiles.size()));
        System.out.println("  실패한 파일 수: " + failedFiles.size());

        if (!failedFiles.isEmpty()) {
            System.out.println("실패한 파일 목록:");
            for (String file : failedFiles) {
                System.out.println("  " + file);
            }
        }

        // 임시 파일 정리
        cleanupTempFile(validFile);
        cleanupTempFile(invalidFile);
        cleanupTempFile(outputFile);

        System.out.println("\n파일처리시스템 데모 완료!");
    }

    /**
     * 테스트용 임시 파일 생성
     */
    private static String createTempFile(String fileName, String content) {
        try {
            String filePath = Paths.get(System.getProperty("java.io.tmpdir"), fileName).toString();
            try (FileWriter writer = new FileWriter(filePath)) {
                writer.write(content);
            }
            return filePath;
        } catch (IOException e) {
            System.err.println("Error creating temp file: " + e.getMessage());
            return null;
        }
    }

    /**
     * 임시 파일 정리
     */
    private static void cleanupTempFile(String filePath) {
        if (filePath != null) {
            File file = new File(filePath);
            if (file.exists()) {
                boolean deleted = file.delete();
                if (!deleted) {
                    System.err.println("Failed to delete temporary file: " + filePath);
                }
            }
        }
    }
}

