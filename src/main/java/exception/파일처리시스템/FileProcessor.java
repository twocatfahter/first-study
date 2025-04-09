package exception.파일처리시스템;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

public class FileProcessor {

    /**
     * 파일 처리 과정에서 발생하는 기본 예외 클래스
     */
    public static class FileProcessingException extends Exception {
        public FileProcessingException(String message) {
            super(message);
        }

        public FileProcessingException(String message, Throwable cause) {
            super(message, cause);
        }
    }

    /**
     * 파일을 찾을 수 없을 때 발생하는 예외
     */
    public static class FileNotFoundException extends FileProcessingException {
        private final String filePath;

        public FileNotFoundException(String filePath) {
            super("File not found: " + filePath);
            this.filePath = filePath;
        }

        public String getFilePath() {
            return filePath;
        }
    }

    /**
     * 파일 내용 형식이 올바르지 않을 때 발생하는 예외
     */
    public static class InvalidFileFormatException extends FileProcessingException {
        private final String filePath;
        private final int lineNumber;

        public InvalidFileFormatException(String filePath, int lineNumber, String message) {
            super(String.format("Invalid format in file %s at line %d: %s",
                    filePath, lineNumber, message));
            this.filePath = filePath;
            this.lineNumber = lineNumber;
        }

        public String getFilePath() {
            return filePath;
        }

        public int getLineNumber() {
            return lineNumber;
        }
    }

    /**
     * 파일 접근 권한이 없을 때 발생하는 예외
     */
    public static class FileAccessDeniedException extends FileProcessingException {
        public FileAccessDeniedException(String filePath) {
            super("Access denied to file: " + filePath);
        }
    }

    /**
     * 파일 쓰기 오류 예외
     */
    public static class FileWriteException extends FileProcessingException {
        public FileWriteException(String message, Throwable cause) {
            super(message, cause);
        }
    }

    /**
     * 파일 명세 검증에 실패했을 때 발생하는 Runtime 예외
     */
    public static class FileSpecificationException extends RuntimeException {
        public FileSpecificationException(String message) {
            super(message);
        }
    }

    /**
     * 리소스 누수 방지를 위한 try-with-resources 패턴을 사용해
     * 파일을 읽고 처리하는 메서드
     *
     * @param filePath 처리할 파일 경로
     * @return 처리된 라인 목록
     * @throws FileProcessingException 파일 처리 중 발생하는 예외
     */
    public List<String> readAndProcessFile(String filePath) throws FileProcessingException {
        // 결과 저장 리스트
        List<String> results = new ArrayList<>();
        File file = new File(filePath);

        // 파일 존재 여부 확인
        if (!file.exists()) {
            throw new FileNotFoundException(filePath);
        }

        // 파일 읽기 권한 확인
        if (!file.canRead()) {
            throw new FileAccessDeniedException(filePath);
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            int lineNumber = 0;

            while ((line = reader.readLine()) != null) {
                lineNumber++;
                try {
                    String processed = processLine(line, lineNumber, filePath);
                    if (processed != null) {
                        results.add(processed);
                    }
                } catch (InvalidFileFormatException e) {
                    // 개별 라인 처리 실패 로깅 후 계속 진행 (fail-safe 동작)
                    System.err.println("Warning: " + e.getMessage());
                    // 추가 정보를 위한 예외 속성 활용
                    System.err.println("  at line: " + e.getLineNumber() + " in file: " + e.getFilePath());
                }
            }
        } catch (IOException e) {
            // 체크 예외(IOException)를 사용자 정의 예외로 포장하여 의미 있는 예외로 변환
            throw new FileProcessingException(
                    "Error reading file: " + filePath, e);
        } catch (IllegalArgumentException e) {
            // 런타임 예외도 사용자 정의 예외로 포장 가능
            throw new FileProcessingException(
                    "Invalid data in file: " + filePath, e);
        }

        return results;
    }

    /**
     * 개별 라인을 처리하는 메서드
     * @param line 처리할 라인
     * @param lineNumber 현재 라인 번호
     * @param filePath 파일 경로
     * @return 처리된 라인
     * @throws InvalidFileFormatException 라인 형식이 유효하지 않을 때
     */
    private String processLine(String line, int lineNumber, String filePath)
            throws InvalidFileFormatException {
        // 빈 라인 무시
        if (line.trim().isEmpty()) {
            return null;
        }

        // 주석 라인 무시 (# 으로 시작하는 라인)
        if (line.trim().startsWith("#")) {
            return null;
        }

        // 형식 검증 예제: 콤마로 구분된 값이 2개 이상 있어야 함
        String[] parts = line.split(",");
        if (parts.length < 2) {
            throw new InvalidFileFormatException(
                    filePath, lineNumber, "Line must contain at least two comma-separated values");
        }

        // 데이터 처리 로직 (예: 대문자로 변환)
        return line.toUpperCase();
    }

    /**
     * 여러 예외를 다양한 방식으로 처리하는 파일 처리 예제
     * @param inputPath 입력 파일 경로
     * @param outputPath 출력 파일 경로
     * @param processor 각 라인에 적용할 처리 함수
     * @throws FileProcessingException 파일 처리 중 발생하는 예외
     */
    public void processAndTransformFile(String inputPath, String outputPath,
                                        Function<String, String> processor) throws FileProcessingException {

        // 입력과 출력 파일의 Path 객체 생성
        Path input = Paths.get(inputPath);
        Path output = Paths.get(outputPath);

        // 파일 명세 검증 - 실패 시 즉시 런타임 예외 발생
        validateFiles(input, output);

        List<String> processedLines = new ArrayList<>();

        try {
            // 파일을 한 번에 모든 라인을 읽음 - Files 클래스 활용
            List<String> lines = Files.readAllLines(input, StandardCharsets.UTF_8);

            // 각 라인 처리
            for (int i = 0; i < lines.size(); i++) {
                String line = lines.get(i);
                try {
                    // 사용자 지정 함수로 처리
                    String processedLine = processor.apply(line);
                    processedLines.add(processedLine);
                } catch (Exception e) {
                    // 개별 라인 처리 중 예외 발생 시 경고 출력 후 계속 진행
                    System.err.println("Warning: Failed to process line " + (i+1) +
                            ": " + e.getMessage());
                }
            }

            // 처리된 결과를 파일에 쓰기
            Files.write(output, processedLines, StandardCharsets.UTF_8);

        } catch (IOException e) {
            // IOException을 사용자 정의 예외로 포장
            throw new FileProcessingException("Error processing file: " + input, e);
        }
    }

    /**
     * 파일 쓰기 작업을 수행하는 메서드
     * @param lines 쓸 내용
     * @param filePath 저장할 파일 경로
     * @throws FileWriteException 파일 쓰기 중 오류 발생 시
     */
    public void writeToFile(List<String> lines, String filePath) throws FileWriteException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (String line : lines) {
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            throw new FileWriteException("Failed to write to file: " + filePath, e);
        }
    }

    /**
     * 파일 명세를 검증하는 메서드 - 검증 실패 시 즉시 런타임 예외 발생
     * @param input 입력 파일 경로
     * @param output 출력 파일 경로
     * @throws FileSpecificationException 파일 명세 검증에 실패한 경우
     */
    private void validateFiles(Path input, Path output) {
        // 입력 파일이 존재하고 읽기 가능한지 확인
        File inputFile = input.toFile();
        if (!inputFile.exists()) {
            throw new FileSpecificationException("Input file does not exist: " + input);
        }
        if (!inputFile.canRead()) {
            throw new FileSpecificationException("Cannot read input file: " + input);
        }

        // 출력 파일 검증
        File outputFile = output.toFile();
        File outputDir = outputFile.getParentFile();

        // 출력 디렉토리가 존재하는지 확인
        if (outputDir != null && !outputDir.exists()) {
            throw new FileSpecificationException("Output directory does not exist: " + outputDir);
        }

        // 출력 파일이 이미 존재하는 경우 쓰기 가능한지 확인
        if (outputFile.exists() && !outputFile.canWrite()) {
            throw new FileSpecificationException("Cannot write to output file: " + output);
        }
    }

    /**
     * 예외 처리 파이프라인 예제 - 다양한 예외를 처리하는 패턴
     * @param inputPath 입력 파일 경로
     * @param outputPath 출력 파일 경로
     */
    public void processFileSafely(String inputPath, String outputPath) {
        try {
            // 안전한 파일 처리 로직 - 체크 예외 처리
            List<String> data = readAndProcessFile(inputPath);
            writeToFile(data, outputPath);
            System.out.println("File processed successfully");

        } catch (FileNotFoundException e) {
            // 파일을 찾을 수 없는 경우 - 사용자 안내
            System.err.println("Error: " + e.getMessage());
            System.err.println("Please check if the file exists at: " + e.getFilePath());

        } catch (FileAccessDeniedException e) {
            // 파일 접근 권한이 없는 경우
            System.err.println("Error: " + e.getMessage());
            System.err.println("Please check file permissions");

        } catch (InvalidFileFormatException e) {
            // 파일 형식이 잘못된 경우
            System.err.println("Error: " + e.getMessage());
            System.err.println("Check line " + e.getLineNumber() + " in file: " + e.getFilePath());

        } catch (FileWriteException e) {
            // 파일 쓰기 오류
            System.err.println("Error writing to file: " + e.getMessage());
            System.err.println("Cause: " + e.getCause().getMessage());

        } catch (FileProcessingException e) {
            // 기타 파일 처리 예외
            System.err.println("File processing error: " + e.getMessage());
            if (e.getCause() != null) {
                System.err.println("Root cause: " + e.getCause().getMessage());
            }

        } catch (RuntimeException e) {
            // 예상치 못한 런타임 예외 처리
            System.err.println("Unexpected error: " + e.getMessage());
            e.printStackTrace();

        } finally {
            // 정리 작업 - 항상 실행됨
            System.out.println("File processing operation completed");
        }
    }

    /**
     * 여러 파일을 일괄 처리하는 메서드 - 실패한 파일 목록 반환
     * @param filePaths 처리할 파일 경로 목록
     * @return 처리에 실패한 파일 경로 목록
     */
    public List<String> batchProcessFiles(List<String> filePaths) {
        List<String> failedFiles = new ArrayList<>();

        for (String path : filePaths) {
            try {
                List<String> processed = readAndProcessFile(path);
                System.out.println("Successfully processed: " + path +
                        " (" + processed.size() + " lines)");
            } catch (FileProcessingException e) {
                // 실패한 파일 기록
                failedFiles.add(path);
                System.err.println("Failed to process: " + path + " - " + e.getMessage());
            }
        }

        return failedFiles;
    }

    /**
     * 예외를 감싸서 처리하는 유틸리티 메서드
     * @param filePath 처리할 파일 경로
     * @return 처리 결과 또는 빈 목록 (절대 null을 반환하지 않음)
     */
    public List<String> safeReadFile(String filePath) {
        try {
            return readAndProcessFile(filePath);
        } catch (Exception e) {
            // 모든 예외를 처리하고 기본값 반환 (null 안전성)
            System.err.println("Error processing file: " + filePath);
            e.printStackTrace();
            return Collections.emptyList();  // 절대 null을 반환하지 않음
        }
    }
}