package factory.예제3;

// 1. 문서 변환기 인터페이스
public interface DocumentConverter {
    byte[] convert(byte[] input);
    String getOutputFormat();
}
