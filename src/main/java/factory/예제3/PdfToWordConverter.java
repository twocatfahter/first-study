package factory.예제3;

public class PdfToWordConverter implements DocumentConverter{
    @Override
    public byte[] convert(byte[] input) {
        // PDF를 Word 형태로 변환하는 로직
        return input;
    }

    @Override
    public String getOutputFormat() {
        return "DOCX";
    }
}
